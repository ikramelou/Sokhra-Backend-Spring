package me.Sokhra.sokhrabackendspring.user.repository;

import me.Sokhra.sokhrabackendspring.user.entity.User;
import me.Sokhra.sokhrabackendspring.user.repository.projection.ProfileProjection;
import me.Sokhra.sokhrabackendspring.user.repository.projection.UserProfileProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {
  @Query("SELECT profilePicture FROM User WHERE id = :id")
  String getProfilePictureById(@Param("id") String id);

  UserProfileProjection findUserProjectedById(String id);

  ProfileProjection findProfileProjectedById(String id);
}
