package me.Sokhra.sokhrabackendspring.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Rating {
  private Double ratingValue;
  private Integer numberOfRatings;
}
