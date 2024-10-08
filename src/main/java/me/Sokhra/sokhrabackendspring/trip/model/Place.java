package me.Sokhra.sokhrabackendspring.trip.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Embeddable
public class Place {
  private String city;
  private String country;
}
