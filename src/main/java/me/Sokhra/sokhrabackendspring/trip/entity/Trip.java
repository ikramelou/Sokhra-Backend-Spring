package me.Sokhra.sokhrabackendspring.trip.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import me.Sokhra.sokhrabackendspring.common.entity.BaseEntity;
import me.Sokhra.sokhrabackendspring.shipment.entity.Shipment;
import me.Sokhra.sokhrabackendspring.trip.model.Place;
import me.Sokhra.sokhrabackendspring.trip.model.TripStatus;
import me.Sokhra.sokhrabackendspring.user.entity.User;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trips")
@EntityListeners(AuditingEntityListener.class)
public class Trip extends BaseEntity {
  @ManyToOne
  @JoinColumn(nullable = false)
  private User traveller;

  @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<Shipment> shipments = new ArrayList<>();

  @Embedded
  @AttributeOverrides({
          @AttributeOverride(name = "city", column = @Column(name = "origin_city")),
          @AttributeOverride(name = "country", column = @Column(name = "origin_country"))
  })
  private Place origin;

  @Embedded
  @AttributeOverrides({
          @AttributeOverride(name = "city", column = @Column(name = "destination_city")),
          @AttributeOverride(name = "country", column = @Column(name = "destination_country"))
  })
  private Place destination;

  private LocalDate departureDate;

  private Integer maxWeight;

  private Integer price;

  @Enumerated(EnumType.STRING)
  private TripStatus status;

  public Trip(UUID id) {
    super(id);
  }

  public Trip(UUID id, User traveller, List<Shipment> shipments, Place origin, Place destination, LocalDate departureDate, Integer maxWeight, Integer price, TripStatus status) {
    super(id);
    this.traveller = traveller;
    this.shipments = shipments;
    this.origin = origin;
    this.destination = destination;
    this.departureDate = departureDate;
    this.maxWeight = maxWeight;
    this.price = price;
    this.status = status;
  }
}
