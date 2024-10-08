package me.Sokhra.sokhrabackendspring.shipment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.Sokhra.sokhrabackendspring.requestresponse.util.ResponseUtil;
import me.Sokhra.sokhrabackendspring.shipment.dto.ShipmentDTO;
import me.Sokhra.sokhrabackendspring.shipment.model.ShipmentStatus;
import me.Sokhra.sokhrabackendspring.shipment.repository.projection.ShipmentProjectionForUser;
import me.Sokhra.sokhrabackendspring.shipment.service.ShipmentService;
import me.Sokhra.sokhrabackendspring.trip.repository.projection.TripProjectionForShipments;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class ShipmentController {
  private final ShipmentService shipmentService;

  @PostMapping("/shipment/add")
  public ResponseEntity<?> addShipment(@AuthenticationPrincipal Jwt token,
                                       @ModelAttribute @Valid ShipmentDTO.AddShipmentDTO addShipmentDTO) {
    shipmentService.addShipment(token, addShipmentDTO);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                    ResponseUtil.successResponse(
                            "Shipment added successfully",
                            Collections.singletonMap("created", true)
                    )
            );
  }

  @PutMapping("/shipment/accept")
  public ResponseEntity<?> acceptShipment(@RequestBody @Valid ShipmentDTO.EditShipmentDTO editShipmentDTO) {
    shipmentService.editShipmentStatus(editShipmentDTO.getShipmentId(), ShipmentStatus.ACCEPTED);
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                    ResponseUtil.successResponse(
                            "Shipment accepted successfully",
                            Collections.singletonMap("accepted", true)
                    )
            );
  }

  @PutMapping("/shipment/reject")
  public ResponseEntity<?> rejectShipment(@RequestBody @Valid ShipmentDTO.EditShipmentDTO editShipmentDTO) {
    shipmentService.editShipmentStatus(editShipmentDTO.getShipmentId(), ShipmentStatus.REJECTED);
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                    ResponseUtil.successResponse(
                            "Shipment rejected successfully",
                            Collections.singletonMap("rejected", true)
                    )
            );
  }

  @PutMapping("/shipment/deliver")
  public ResponseEntity<?> deliverShipment(@RequestBody @Valid ShipmentDTO.EditShipmentDTO editShipmentDTO) {
    shipmentService.editShipmentStatus(editShipmentDTO.getShipmentId(), ShipmentStatus.DELIVERED);
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                    ResponseUtil.successResponse(
                            "Shipment delivered successfully",
                            Collections.singletonMap("delivered", true)
                    )
            );
  }

  @DeleteMapping("/shipment/cancel")
  public ResponseEntity<?> cancelShipment(@RequestBody @Valid ShipmentDTO.EditShipmentDTO editShipmentDTO) {
    shipmentService.deleteShipmentById(editShipmentDTO.getShipmentId());
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                    ResponseUtil.successResponse(
                            "Shipment deleted successfully",
                            Collections.singletonMap("deleted", true)
                    )
            );
  }

  @GetMapping(value = "/shipment/image/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
  public ResponseEntity<?> getShipmentPicture(@PathVariable UUID id) throws IOException {
    return ResponseEntity.ok(
            shipmentService.getProfilePicture(id)
    );
  }

  @GetMapping("/shipments/my")
  public ResponseEntity<?> getAllMyShipments(@AuthenticationPrincipal Jwt token, @RequestParam ShipmentStatus status) {
    List<ShipmentProjectionForUser> shipmentList = shipmentService.getAllMyShipments(token, status);
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                    ResponseUtil.successResponse(
                            "My shipments fetched successfully",
                            Collections.singletonMap("shipmentList", shipmentList)
                    )
            );
  }

  @GetMapping("/trip/shipments")
  public ResponseEntity<?> getTripShipments(@RequestParam UUID id, @RequestParam ShipmentStatus status) {
    List<TripProjectionForShipments> shipmentList = shipmentService.getTripShipments(id, status);
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                    ResponseUtil.successResponse(
                            "My shipments fetched successfully",
                            Collections.singletonMap("shipmentList", shipmentList)
                    )
            );
  }

}
