package feedmysheep.feedmysheepapi.domain.auth.app.service;

import feedmysheep.feedmysheepapi.domain.auth.app.dto.AuthReqDto;
import feedmysheep.feedmysheepapi.domain.auth.app.dto.AuthResDto;
import feedmysheep.feedmysheepapi.global.utils.jwt.CustomUserDetails;

public interface AuthService {

  AuthResDto.createToken createToken(AuthReqDto.createToken body);

  AuthResDto.getMemberAuthByScreenKey getMemberAuthByScreenKey(
      AuthReqDto.getMemberAuthByScreenKey query, CustomUserDetails customUserDetails);
}
