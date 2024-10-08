package me.Sokhra.sokhrabackendspring.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.Sokhra.sokhrabackendspring.requestresponse.util.ResponseUtil;
import me.Sokhra.sokhrabackendspring.user.dto.ReviewDTO;
import me.Sokhra.sokhrabackendspring.user.repository.projection.ReviewProjection;
import me.Sokhra.sokhrabackendspring.user.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReviewController {
  private final ReviewService reviewService;

  @PostMapping("/user/review/cast")
  public ResponseEntity<?> castVote(@AuthenticationPrincipal Jwt token, @RequestBody @Valid ReviewDTO.CastVoteDTO castVoteDTO) {
    reviewService.castVote(token, castVoteDTO);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                    ResponseUtil.successResponse(
                            "Vote casted successfully",
                            Collections.singletonMap("created", true)
                    )
            );
  }

  @GetMapping("/reviewtest")
  public List<ReviewProjection> tt() {
    return reviewService.getAllUserReviews("u2");
  }
}
