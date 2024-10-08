package me.Sokhra.sokhrabackendspring.trip.repository;

import me.Sokhra.sokhrabackendspring.shipment.model.ShipmentStatus;
import me.Sokhra.sokhrabackendspring.trip.entity.Trip;
import me.Sokhra.sokhrabackendspring.trip.model.TripStatus;
import me.Sokhra.sokhrabackendspring.trip.repository.projection.TripProjectionForMyTrips;
import me.Sokhra.sokhrabackendspring.trip.repository.projection.TripProjectionForShipments;
import me.Sokhra.sokhrabackendspring.trip.repository.projection.TripProjectionForTripsListing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface TripRepository extends JpaRepository<Trip, UUID>, JpaSpecificationExecutor<Trip> {
  Page<TripProjectionForTripsListing> findAllByStatus(TripStatus status, Pageable pageable);

  @Query("""
          SELECT trip FROM Trip trip WHERE
          ((:originCity IS NULL OR trip.origin.city = :originCity) AND (:originCountry IS NULL OR trip.origin.country = :originCountry))
          AND
          ((:destinationCity IS NULL OR trip.destination.city = :destinationCity) AND (:destinationCountry IS NULL OR trip.destination.country = :destinationCountry))
          AND
          (:departureDate IS NULL OR trip.departureDate = :departureDate)
          AND
          (:weight IS NULL OR (COALESCE((SELECT SUM(shipments.weight) FROM trip.shipments shipments), 0)) <= trip.maxWeight - :weight)
          AND
          (trip.status = :status)
          """)
  Page<TripProjectionForTripsListing> findAllBy(@Param("originCity") String originCity, @Param("originCountry") String originCountry,
                                                @Param("destinationCity") String destinationCity, @Param("destinationCountry") String destinationCountry,
                                                @Param("departureDate") LocalDate departureDate,
                                                @Param("weight") Integer weight,
                                                @Param("status") TripStatus status,
                                                Pageable pageable);

  Integer countAllByTravellerIdAndStatus(String id, TripStatus status);

  List<TripProjectionForMyTrips> findAllByTravellerIdAndStatusOrderByDepartureDateAsc(String id, TripStatus status);

  Trip getTripById(UUID id);

  List<TripProjectionForShipments> getShipmentsByIdAndShipmentsStatusOrderByCreatedAtAsc(UUID id, ShipmentStatus shipmentStatus);
}
