package me.Sokhra.sokhrabackendspring.shipment.service;

import lombok.SneakyThrows;
import me.Sokhra.sokhrabackendspring.imageutility.service.ImageService;
import me.Sokhra.sokhrabackendspring.shipment.dto.ShipmentDTO;
import me.Sokhra.sokhrabackendspring.shipment.entity.Shipment;
import me.Sokhra.sokhrabackendspring.shipment.model.ShipmentStatus;
import me.Sokhra.sokhrabackendspring.shipment.repository.ShipmentRepository;
import me.Sokhra.sokhrabackendspring.shipment.repository.projection.ShipmentProjectionForUser;
import me.Sokhra.sokhrabackendspring.trip.entity.Trip;
import me.Sokhra.sokhrabackendspring.trip.repository.TripRepository;
import me.Sokhra.sokhrabackendspring.trip.repository.projection.TripProjectionForShipments;
import me.Sokhra.sokhrabackendspring.user.entity.User;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ShipmentService {
  private final ShipmentRepository shipmentRepository;
  private final ImageService imageService;
  private final TripRepository tripRepository;

  public ShipmentService(ShipmentRepository shipmentRepository, @Qualifier("shipmentImageService") ImageService imageService, TripRepository tripRepository) {
    this.shipmentRepository = shipmentRepository;
    this.imageService = imageService;
    this.tripRepository = tripRepository;
  }

  public byte[] getShipmentPicture(UUID id) throws IOException {
    String shipmentPicture = shipmentRepository.getShipmentPictureById(id);
    return imageService.loadImage(shipmentPicture);
  }

  public void saveShipmentPicture(MultipartFile shipmentPicture, String id) throws IOException {
    imageService.saveImage(shipmentPicture, id);
  }

  public Shipment getShipmentById(UUID shipmentId) {
    return shipmentRepository.findById(shipmentId).orElse(null);
  }

  @SneakyThrows
  public void addShipment(Jwt token, ShipmentDTO.AddShipmentDTO addShipmentDTO) {
    User sender = new User(token.getClaim("user_id"));
    Trip trip = new Trip(addShipmentDTO.getTripID());

    Shipment shipment = Shipment.builder().sender(sender).trip(trip).title(addShipmentDTO.getTitle()).note(addShipmentDTO.getNote()).weight(addShipmentDTO.getWeight()).shipmentPicture(UUID.randomUUID().toString() + "." + Objects.requireNonNull(FilenameUtils.getExtension(addShipmentDTO.getShipmentPicture().getOriginalFilename())).toLowerCase()).status(ShipmentStatus.PENDING).build();
    saveShipmentPicture(addShipmentDTO.getShipmentPicture(), shipment.getShipmentPicture());
    shipmentRepository.save(shipment);
  }

  public Integer getShipmentsCountByUserId(String id) {
    return shipmentRepository.countAllBySenderIdAndStatus(id, ShipmentStatus.DELIVERED);
  }

  //todo: mark trip as completed when all its shipments are delivered
  public void editShipmentStatus(UUID id, ShipmentStatus shipmentStatus) {
    Shipment shipment = shipmentRepository.findById(id).orElse(null);
    if (shipment != null) {
      shipment.setStatus(shipmentStatus);
      shipmentRepository.save(shipment);
    }
  }

  public void deleteShipmentById(UUID id) {
    shipmentRepository.deleteById(id);
  }

  public byte[] getProfilePicture(UUID id) throws IOException {
    String profilePicture = shipmentRepository.getShipmentPictureById(id);
    return imageService.loadImage(profilePicture);
  }

  public List<ShipmentProjectionForUser> getAllMyShipments(Jwt token, @RequestParam ShipmentStatus shipmentStatus) {
    return shipmentRepository.findAllProjectedBySenderIdAndStatusOrderByTripDepartureDateAsc(token.getClaim("user_id"), shipmentStatus);
  }

  public List<TripProjectionForShipments> getTripShipments(UUID id, @RequestParam ShipmentStatus shipmentStatus) {
    return tripRepository.getShipmentsByIdAndShipmentsStatusOrderByCreatedAtAsc(id, shipmentStatus);
  }
}
