package feedmysheep.feedmysheepapi.domain.auth.app.service;

import feedmysheep.feedmysheepapi.domain.auth.app.dto.AuthReqDto;
import feedmysheep.feedmysheepapi.domain.auth.app.dto.AuthResDto;
import feedmysheep.feedmysheepapi.domain.auth.app.dto.AuthResDto.createAccessToken;
import feedmysheep.feedmysheepapi.global.utils.jwt.JwtDto;
import feedmysheep.feedmysheepapi.global.utils.jwt.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {
  private final JwtTokenProvider jwtTokenProvider;

  @Autowired
  public AuthService(JwtTokenProvider jwtTokenProvider) { this.jwtTokenProvider = jwtTokenProvider; }

  public AuthResDto.createAccessToken createAccessToken (@Valid @RequestBody AuthReqDto.createAccessToken body) {
    String refreshToken = body.getRefreshToken();
    JwtDto.memberInfo memberInfo = this.jwtTokenProvider.validateToken(refreshToken);
    String accessToken = this.jwtTokenProvider.createAccessToken(memberInfo);

    return new createAccessToken(accessToken);
  }
}
