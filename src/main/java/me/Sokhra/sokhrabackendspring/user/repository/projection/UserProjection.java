package me.Sokhra.sokhrabackendspring.user.repository.projection;

import me.Sokhra.sokhrabackendspring.user.model.Rating;
import org.springframework.beans.factory.annotation.Value;

public interface UserProjection {
  String getId();

  String getFirstName();

  @Value("#{@reviewService.getRating(target.id)}")
  Rating getRating();
}
