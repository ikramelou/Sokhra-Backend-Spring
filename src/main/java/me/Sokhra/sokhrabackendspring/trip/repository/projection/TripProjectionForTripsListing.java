package me.Sokhra.sokhrabackendspring.trip.repository.projection;

import me.Sokhra.sokhrabackendspring.trip.model.Place;
import me.Sokhra.sokhrabackendspring.trip.model.TripStatus;
import me.Sokhra.sokhrabackendspring.user.repository.projection.UserProjection;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.util.UUID;

public interface TripProjectionForTripsListing {
  UUID getId();

  UserProjection getTraveller();

  Place getOrigin();

  Place getDestination();

  LocalDate getDepartureDate();

  @Value("#{@tripService.getAvailableWeight(target.id)}")
  Integer getAvailableWeight();

  Integer getPrice();

  TripStatus getStatus();
}
