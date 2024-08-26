package feedmysheep.feedmysheepapi.domain.auth.app.controller;

import feedmysheep.feedmysheepapi.domain.auth.app.dto.AuthReqDto;
import feedmysheep.feedmysheepapi.domain.auth.app.dto.AuthResDto;
import feedmysheep.feedmysheepapi.domain.auth.app.service.AuthService;
import feedmysheep.feedmysheepapi.global.utils.jwt.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/token")
  public AuthResDto.createToken createToken(@Valid @RequestBody AuthReqDto.createToken body) {
    return this.authService.createToken(body);
  }

  @GetMapping("/member")
  public AuthResDto.getMemberAuthByScreenKey getMemberAuthByScreenKey(
      @Valid AuthReqDto.getMemberAuthByScreenKey query,
      @AuthenticationPrincipal CustomUserDetails customUserDetails) {
    return this.authService.getMemberAuthByScreenKey(query, customUserDetails);
  }

  ;
}
