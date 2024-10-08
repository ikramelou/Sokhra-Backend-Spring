package me.Sokhra.sokhrabackendspring.shipment.validator.weightwithinlimitvalidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.Sokhra.sokhrabackendspring.shipment.dto.ShipmentDTO;
import me.Sokhra.sokhrabackendspring.trip.service.TripService;

@RequiredArgsConstructor
public class IsWeightWithinLimit implements ConstraintValidator<WeightWithinLimit, ShipmentDTO.AddShipmentDTO> {
  private final TripService tripService;

  @SneakyThrows
  @Override
  public boolean isValid(ShipmentDTO.AddShipmentDTO addShipmentDTO, ConstraintValidatorContext constraintValidatorContext) {
    return tripService.canAcceptTrip(addShipmentDTO.getTripID(), addShipmentDTO.getWeight());
  }
}
