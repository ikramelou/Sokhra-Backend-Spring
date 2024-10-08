package me.Sokhra.sokhrabackendspring.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Builder
@EntityListeners(AuditingEntityListener.class)
public class User {
  @Id
  private String id;

  private String phoneNumber;

  private String firstName;

  private String lastName;

  private String profilePicture;

  public User(String id) {
    this.id = id;
  }

  @CreatedDate
  @Column(name = "created_at")
  protected LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  protected LocalDateTime updatedAt;
}
