package me.Sokhra.sokhrabackendspring.shipment.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.Sokhra.sokhrabackendspring.shipment.validator.weightwithinlimitvalidator.WeightWithinLimit;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class ShipmentDTO {
  @Data
  @Builder
  @WeightWithinLimit
  public static class AddShipmentDTO {
    @NotNull
    private UUID tripID;

    @NotNull
    @Min(1)
    private Integer weight;

    @NotEmpty
    private String title;

    private String note;

    @NotNull
    private MultipartFile shipmentPicture;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class EditShipmentDTO {
    @NotNull
    private UUID shipmentId;
  }


}
