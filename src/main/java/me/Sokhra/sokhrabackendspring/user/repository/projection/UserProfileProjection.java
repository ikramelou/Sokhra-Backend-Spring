package me.Sokhra.sokhrabackendspring.user.repository.projection;

import me.Sokhra.sokhrabackendspring.user.model.Rating;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface UserProfileProjection {
  String getId();

  String getFirstName();

  @Value("#{@reviewService.getRating(target.id)}")
  Rating getRating();

  @Value("#{@reviewService.getAllUserReviews(target.id)}")
  List<ReviewProjection> getRatings();

  @Value("#{@tripService.getTripsCountByUserId(target.id)}")
  Integer getTripsCount();

  @Value("#{@shipmentService.getShipmentsCountByUserId(target.id)}")
  Integer getShipmentsCount();
}
