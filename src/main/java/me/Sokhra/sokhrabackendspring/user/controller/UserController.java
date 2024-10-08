package me.Sokhra.sokhrabackendspring.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.Sokhra.sokhrabackendspring.requestresponse.util.ResponseUtil;
import me.Sokhra.sokhrabackendspring.user.dto.UserDTO;
import me.Sokhra.sokhrabackendspring.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
@RestController
public class UserController {
  private final UserService userService;

  @GetMapping("/user/register/shouldRegister")
  public ResponseEntity<?> shouldRegister(@AuthenticationPrincipal Jwt token) {
    Boolean shouldRegister = userService.shouldRegister(token);
    return ResponseEntity.ok(
            ResponseUtil.successResponse(
                    (shouldRegister) ? "User should register" : "User should not register",
                    Collections.singletonMap("shouldRegister", shouldRegister)
            )
    );
  }

  @PostMapping("/user/register")
  public ResponseEntity<?> register(@AuthenticationPrincipal Jwt token,
                                    @ModelAttribute @Valid UserDTO.RegistrationDTO registrationDTO)
          throws IOException {
    userService.registerUser(token, registrationDTO);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                    ResponseUtil.successResponse(
                            "User created successfully",
                            Collections.singletonMap("created", true)
                    )
            );
  }

  @GetMapping(value = "user/profile/image/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
  public ResponseEntity<?> getProfilePicture(@PathVariable String id) throws IOException {
    return ResponseEntity.ok(
            userService.getProfilePicture(id)
    );
  }

  @GetMapping(value = "/user/profile")
  public ResponseEntity<?> getProfile(@AuthenticationPrincipal Jwt token, @RequestParam(required = false) String id) {
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                    ResponseUtil.successResponse(
                            "User profile fetched successfully",
                            Collections.singletonMap("profile", userService.getUserProfile((id == null || id.isEmpty()) ? token.getClaim("user_id") : id))
                    )
            );
  }

  @GetMapping(value = "/user/profile/home")
  public ResponseEntity<?> getProfileForHome(@AuthenticationPrincipal Jwt token) {
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                    ResponseUtil.successResponse(
                            "User profile fetched successfully",
                            Collections.singletonMap("profile", userService.getProfileForHome(token))
                    )
            );
  }
}
