package me.Sokhra.sokhrabackendspring.user.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

public class ReviewDTO {

  @Data
  @Builder
  public static class CastVoteDTO {
    @NotEmpty
    private String reviewedID;

    @NotNull
    @Min(0)
    @Max(5)
    private Integer rating;

    private String comment;
  }
}
