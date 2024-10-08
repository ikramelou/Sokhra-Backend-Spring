package me.Sokhra.sokhrabackendspring.trip.exception;

public class TripNotFoundException extends Exception {
  public TripNotFoundException() {
    super("Trip not found");
  }
}
