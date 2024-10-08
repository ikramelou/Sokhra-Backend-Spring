package me.Sokhra.sokhrabackendspring.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public class UserDTO {
  @Data
  @Builder
  public static class RegistrationDTO {
    @NotEmpty(message = "firstname is required.")
    private String firstName;

    @NotEmpty(message = "lastname is required.")
    private String lastName;

    @NotNull
    private MultipartFile profilePicture;
  }


}
