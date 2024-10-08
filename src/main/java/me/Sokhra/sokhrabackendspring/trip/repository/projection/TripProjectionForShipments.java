package me.Sokhra.sokhrabackendspring.trip.repository.projection;

import me.Sokhra.sokhrabackendspring.shipment.entity.Shipment;

import java.util.List;

public interface TripProjectionForShipments {
  List<Shipment> getShipments();

  Integer getPrice();
}
