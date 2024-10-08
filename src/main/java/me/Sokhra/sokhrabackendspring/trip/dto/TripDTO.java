package me.Sokhra.sokhrabackendspring.trip.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import me.Sokhra.sokhrabackendspring.trip.model.Place;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class TripDTO {
  @Data
  @Builder
  public static class AddTripDTO {
    @NotNull
    private Place origin;

    @NotNull
    private Place destination;

    @NotNull
    @DateTimeFormat
    private LocalDate departureDate;

    @NotNull
    @Min(0)
    private Integer weight;

    @NotNull
    @Min(0)
    private Integer price;

  }

  @Data
  @Builder
  public static class GetTripsDTO {
    private Place origin;

    private Place destination;


    private LocalDate departureDate;

    @Min(1)
    private Integer weight;

    public Boolean areAllFieldsNull() {
      return origin.getCity() == null && origin.getCountry() == null && destination.getCity() == null && destination.getCountry() == null && departureDate == null && weight == null;
    }
  }


}
