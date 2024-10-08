package me.Sokhra.sokhrabackendspring.user.entity;

import jakarta.persistence.*;
import lombok.*;
import me.Sokhra.sokhrabackendspring.common.entity.BaseEntity;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
@Builder
public class Review extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private User caster;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private User reviewed;

  private Integer rating;

  private String comment;

  public Review(UUID id) {
    super(id);
  }

  public Review(UUID id, User reviewer, User reviewed, Integer rating, String comment) {
    super(id);
    this.caster = reviewer;
    this.reviewed = reviewed;
    this.rating = rating;
    this.comment = comment;
  }
}
