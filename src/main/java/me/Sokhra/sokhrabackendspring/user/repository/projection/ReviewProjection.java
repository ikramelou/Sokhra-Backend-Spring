package me.Sokhra.sokhrabackendspring.user.repository.projection;

import java.time.LocalDateTime;

public interface ReviewProjection {
  ProfileProjection getCaster();

  Integer getRating();

  String getComment();

  LocalDateTime getCreatedAt();
}
