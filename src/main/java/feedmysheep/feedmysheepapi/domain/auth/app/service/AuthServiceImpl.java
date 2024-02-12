package feedmysheep.feedmysheepapi.domain.auth.app.service;

import feedmysheep.feedmysheepapi.domain.auth.app.dto.AuthReqDto;
import feedmysheep.feedmysheepapi.domain.auth.app.dto.AuthResDto;
import feedmysheep.feedmysheepapi.domain.auth.app.repository.AuthorizationRepository;
import feedmysheep.feedmysheepapi.domain.auth.app.repository.AuthorizationScreenRepository;
import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
import feedmysheep.feedmysheepapi.global.interceptor.auth.MemberAuth;
import feedmysheep.feedmysheepapi.global.utils.jwt.CustomUserDetails;
import feedmysheep.feedmysheepapi.global.utils.jwt.JwtDto;
import feedmysheep.feedmysheepapi.global.utils.jwt.JwtTokenProvider;
import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;
import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import feedmysheep.feedmysheepapi.models.AuthorizationScreenEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
  private final MemberRepository memberRepository;
  private final AuthorizationRepository authorizationRepository;
  private final AuthorizationScreenRepository authorizationScreenRepository;

  @Override
  public AuthResDto.createToken createToken(AuthReqDto.createToken body) {
    // 1. 유효한 리프레시 토큰인지 검사
    JwtDto.memberInfo memberInfo = this.jwtTokenProvider.validateToken(body.getRefreshToken());

    // 2. 토큰 재발급
    String refreshToken = this.jwtTokenProvider.createRefreshToken(memberInfo);
    String accessToken = this.jwtTokenProvider.createAccessToken(memberInfo);

    return new AuthResDto.createToken(refreshToken, accessToken);
  }

  @Override
  public AuthResDto.getMemberAuthByScreenKey getMemberAuthByScreenKey(
      AuthReqDto.getMemberAuthByScreenKey query, CustomUserDetails customUserDetails) {
    String screenKey = query.getScreenKey();

    // 1. 멤버의 아이디로 멤버의 권한을 조회합니다.
    MemberEntity member = this.memberRepository.getMemberByMemberId(customUserDetails.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorMessage.MEMBER_NOT_FOUND));
    AuthorizationEntity memberAuthorization = this.authorizationRepository.getByAuthorizationId(
            member.getAuthorizationId())
        .orElseThrow(() -> new CustomException(ErrorMessage.NO_AUTHORIZATION));

    // 2. 해당 스크린의 권한을 조회합니다.
    AuthorizationScreenEntity authorizationScreen = this.authorizationScreenRepository.getAuthorizationScreenByScreenKey(
        screenKey).orElseThrow(() -> new CustomException(ErrorMessage.NO_AUTHORIZATION_SCREEN));
    AuthorizationEntity screenAuthorization = this.authorizationRepository.getByAuthorizationId(
            authorizationScreen.getAuthorizationId())
        .orElseThrow(() -> new CustomException(ErrorMessage.NO_AUTHORIZATION));

    // 3. 멤버의 권한레벨과 스크린의 권한을 비교합니다.
    boolean isAccessible = screenAuthorization.getLevel() <= memberAuthorization.getLevel();

    // FIXME: TEST
    this.authorizationScreenRepository.save(AuthorizationScreenEntity.builder().authorizationId(1L).screenKey("test").build());

    return new AuthResDto.getMemberAuthByScreenKey(isAccessible);
  }
}
