package me.Sokhra.sokhrabackendspring.user.service;

import me.Sokhra.sokhrabackendspring.imageutility.service.ImageService;
import me.Sokhra.sokhrabackendspring.user.dto.UserDTO;
import me.Sokhra.sokhrabackendspring.user.entity.User;
import me.Sokhra.sokhrabackendspring.user.repository.UserRepository;
import me.Sokhra.sokhrabackendspring.user.repository.projection.ProfileProjection;
import me.Sokhra.sokhrabackendspring.user.repository.projection.UserProfileProjection;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final ImageService imageService;

  public UserService(UserRepository userRepository, @Qualifier("profileImageService") ImageService imageService) {
    this.userRepository = userRepository;
    this.imageService = imageService;
  }

  public Boolean shouldRegister(Jwt token) {
    return !userRepository.existsById(token.getClaim("user_id"));
  }

  public void registerUser(Jwt token, UserDTO.RegistrationDTO registrationDTO)
          throws IOException {
    String uid = token.getClaim("user_id");
    String phoneNumber = token.getClaim("phone_number");
    User user = User
            .builder()
            .id(uid)
            .phoneNumber(phoneNumber)
            .firstName(registrationDTO.getFirstName())
            .lastName(registrationDTO.getLastName())
            .profilePicture(
                    uid + "." + Objects.requireNonNull(FilenameUtils.getExtension(registrationDTO.getProfilePicture().getOriginalFilename())).toLowerCase()
            )
            .build();
    saveProfilePicture(registrationDTO.getProfilePicture(), user.getProfilePicture());
    userRepository.save(user);
  }

  public byte[] getProfilePicture(String id) throws IOException {
    String profilePicture = userRepository.getProfilePictureById(id);
    return imageService.loadImage(profilePicture);
  }

  public void saveProfilePicture(MultipartFile profilePicture, String id) throws IOException {
    imageService.saveImage(profilePicture, id);
  }

  public UserProfileProjection getUserProfile(String id) {
    return userRepository.findUserProjectedById(id);
  }

  public ProfileProjection getProfileForHome(Jwt token) {
    return userRepository.findProfileProjectedById(token.getClaim("user_id"));
  }
}
