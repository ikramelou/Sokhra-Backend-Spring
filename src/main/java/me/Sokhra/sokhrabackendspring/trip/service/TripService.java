package me.Sokhra.sokhrabackendspring.trip.service;

import lombok.RequiredArgsConstructor;
import me.Sokhra.sokhrabackendspring.shipment.entity.Shipment;
import me.Sokhra.sokhrabackendspring.shipment.model.ShipmentStatus;
import me.Sokhra.sokhrabackendspring.trip.dto.TripDTO;
import me.Sokhra.sokhrabackendspring.trip.entity.Trip;
import me.Sokhra.sokhrabackendspring.trip.exception.TripNotFoundException;
import me.Sokhra.sokhrabackendspring.trip.model.TripStatus;
import me.Sokhra.sokhrabackendspring.trip.repository.TripRepository;
import me.Sokhra.sokhrabackendspring.trip.repository.projection.TripProjectionForMyTrips;
import me.Sokhra.sokhrabackendspring.trip.repository.projection.TripProjectionForTripsListing;
import me.Sokhra.sokhrabackendspring.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TripService {
  private final TripRepository tripRepository;
  private final WebInvocationPrivilegeEvaluator privilegeEvaluator;

  public Trip getTripById(UUID id) {
    return tripRepository.findById(id).orElse(null);
  }

  public void addTrip(Jwt token, TripDTO.AddTripDTO addTripDTO) {
    User traveller = new User(token.getClaim("user_id"));
    Trip trip = Trip.builder()
            .traveller(traveller)
            .origin(addTripDTO.getOrigin())
            .destination(addTripDTO.getDestination())
            .maxWeight(addTripDTO.getWeight())
            .price(addTripDTO.getPrice())
            .departureDate(addTripDTO.getDepartureDate())
            .status(TripStatus.ACTIVE)
            .build();
    tripRepository.save(trip);
  }

  public Integer getAvailableWeight(UUID id) throws Exception {
    Trip trip = getTripById(id);
    if (trip == null) throw new TripNotFoundException();
    Integer allocatedWeight = trip.getShipments().stream()
            .filter(r -> r.getStatus() == ShipmentStatus.ACCEPTED || r.getStatus() == ShipmentStatus.PENDING)
            .mapToInt(Shipment::getWeight).sum();
    return trip.getMaxWeight() - allocatedWeight;
  }

  public boolean canAcceptTrip(UUID id, Integer requestedWeight) throws Exception {
    Trip trip = getTripById(id);
    if (trip == null) throw new TripNotFoundException();
    return getAvailableWeight(id) >= requestedWeight;
  }

  public Page<TripProjectionForTripsListing> getTripsPaginated(int page, int size, TripDTO.GetTripsDTO getTripsDTO) {
    Pageable pageable;
    if (getTripsDTO == null || getTripsDTO.areAllFieldsNull()) {
      pageable = PageRequest.of(page, size, Sort.by("departureDate").ascending());
      return tripRepository.findAllByStatus(TripStatus.ACTIVE, pageable);
    }
    pageable = PageRequest.of(page, size, Sort.by(((getTripsDTO.getDepartureDate() == null) ? "departureDate" : "price")).ascending());
    return tripRepository.findAllBy((getTripsDTO.getOrigin().getCity() == null) ? null : getTripsDTO.getOrigin().getCity().toLowerCase(), (getTripsDTO.getOrigin().getCity() == null) ? null : getTripsDTO.getOrigin().getCountry().toLowerCase(),
            (getTripsDTO.getDestination().getCity() == null) ? null : getTripsDTO.getDestination().getCity().toLowerCase(), (getTripsDTO.getDestination().getCity() == null) ? null : getTripsDTO.getDestination().getCountry().toLowerCase(),
            (getTripsDTO.getDepartureDate() == null) ? null : getTripsDTO.getDepartureDate(),
            (getTripsDTO.getWeight() == null) ? null : getTripsDTO.getWeight(),
            TripStatus.ACTIVE,
            pageable);
  }

  public Integer getTripsCountByUserId(String id) {
    return tripRepository.countAllByTravellerIdAndStatus(id, TripStatus.COMPLETED);
  }

  public List<TripProjectionForMyTrips> getAllMyTrips(Jwt token, TripStatus status) {
    return tripRepository.findAllByTravellerIdAndStatusOrderByDepartureDateAsc(token.getClaim("user_id"), status);
  }

// Freely editing a trip is the ability to totally edit a trip weight and price tp any value
// This is only allowed when there is no accepted request to the trip
// Origin, destination and departure date are in no way editable
//  public boolean canFreelyEdit(UUID id) throws Exception {
//    Trip trip = getTripById(id);
//    if (trip == null) throw new TripNotFoundException();
//    return trip.getShipments().stream().anyMatch(r -> r.getStatus() == RequestStatus.ACCEPTED);
//  }

//@PreAuthorize("tripRepository.findById(#id).orElse(null)?.traveller?.getUid == principal.username")
//  public void editWeight(UUID id, Double weight) throws Exception {
//    Trip trip = getTripById(id);
//    if (trip == null) throw new TripNotFoundException();
//    if (!canFreelyEdit(id)) throw new CantEditTrip();
//    trip.setMaxWeight(weight);
//    tripRepository.save(trip);
//  }

//  //  @PreAuthorize("tripRepository.findById(#id).orElse(null)?.traveller?.getUid == principal.username")
//  public void editPrice(UUID id, Integer price) throws Exception {
//    Trip trip = getTripById(id);
//    // todo: change weight when not freely
//    if (trip == null) throw new TripNotFoundException();
//    if (!canFreelyEdit(id)) throw new CantEditTrip();
//    trip.setPrice(price);
//    tripRepository.save(trip);
//  }

}
