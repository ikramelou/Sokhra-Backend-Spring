package me.Sokhra.sokhrabackendspring.shipment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import me.Sokhra.sokhrabackendspring.common.entity.BaseEntity;
import me.Sokhra.sokhrabackendspring.shipment.model.ShipmentStatus;
import me.Sokhra.sokhrabackendspring.trip.entity.Trip;
import me.Sokhra.sokhrabackendspring.user.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shipments")
@Builder
public class Shipment extends BaseEntity {
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(nullable = false)
  private User sender;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "trip_id")
  @JsonBackReference
  private Trip trip;

  private String title;

  private String note;

  private Integer weight;

  private String shipmentPicture;

  @Enumerated(EnumType.STRING)
  private ShipmentStatus status;

  public Shipment(UUID id) {
    super(id);
  }

  public Shipment(UUID id, User sender, Trip trip, String title, String note, Integer weight, String shipmentPicture, ShipmentStatus status, LocalDateTime createdAt) {
    super(id);
    this.sender = sender;
    this.trip = trip;
    this.title = title;
    this.note = note;
    this.weight = weight;
    this.shipmentPicture = shipmentPicture;
    this.status = status;
    this.createdAt = createdAt;
  }
}
