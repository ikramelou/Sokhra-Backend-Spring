package me.Sokhra.sokhrabackendspring.shipment.repository;

import me.Sokhra.sokhrabackendspring.shipment.entity.Shipment;
import me.Sokhra.sokhrabackendspring.shipment.model.ShipmentStatus;
import me.Sokhra.sokhrabackendspring.shipment.repository.projection.ShipmentProjectionForUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, UUID> {
  @Query("SELECT shipmentPicture FROM Shipment WHERE id = :id")
  String getShipmentPictureById(@Param("id") UUID id);

  Integer countAllBySenderIdAndStatus(String id, ShipmentStatus status);

  List<ShipmentProjectionForUser> findAllProjectedBySenderIdAndStatusOrderByTripDepartureDateAsc(String id, ShipmentStatus status);
}
