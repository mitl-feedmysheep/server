package feedmysheep.feedmysheepapi.domain.auth.app.controller;

import feedmysheep.feedmysheepapi.domain.auth.app.dto.AuthReqDto;
import feedmysheep.feedmysheepapi.domain.auth.app.dto.AuthResDto;
import feedmysheep.feedmysheepapi.domain.auth.app.service.AuthService;
import feedmysheep.feedmysheepapi.global.utils.jwt.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/auth")
public class AuthController {

  private final AuthService authService;

  @Autowired
  public AuthController(AuthService authService) { this.authService = authService; };

  @PostMapping("/token")
  public AuthResDto.createAccessToken createAccessToken (@Valid @RequestBody AuthReqDto.createAccessToken body) {
    return this.authService.createAccessToken(body);
  }
}
