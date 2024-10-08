package me.Sokhra.sokhrabackendspring.user.repository;

import me.Sokhra.sokhrabackendspring.user.entity.Review;
import me.Sokhra.sokhrabackendspring.user.repository.projection.ReviewProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
  List<Review> findByReviewedId(String id);

  List<ReviewProjection> findAllProjectedByReviewedId(String id);
}
