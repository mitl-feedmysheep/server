package feedmysheep.feedmysheepapi.domain.auth.app.service;

import feedmysheep.feedmysheepapi.domain.auth.app.dto.AuthReqDto;
import feedmysheep.feedmysheepapi.domain.auth.app.dto.AuthResDto;
import feedmysheep.feedmysheepapi.domain.auth.app.repository.AuthorizationRepository;
import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
import feedmysheep.feedmysheepapi.global.interceptor.auth.MemberAuth;
import feedmysheep.feedmysheepapi.global.utils.jwt.CustomUserDetails;
import feedmysheep.feedmysheepapi.global.utils.jwt.JwtDto;
import feedmysheep.feedmysheepapi.global.utils.jwt.JwtTokenProvider;
import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;
import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final JwtTokenProvider jwtTokenProvider;
  private final MemberRepository memberRepository;
  private final AuthorizationRepository authorizationRepository;
  ;

  @Autowired
  public AuthService(MemberRepository memberRepository,
      AuthorizationRepository authorizationRepository) {
    this.jwtTokenProvider = new JwtTokenProvider();
    this.memberRepository = memberRepository;
    this.authorizationRepository = authorizationRepository;
  }

  public AuthResDto.createToken createToken(AuthReqDto.createToken body) {
    // 1. 유효한 리프레시 토큰인지 검사
    JwtDto.memberInfo memberInfo = this.jwtTokenProvider.validateToken(body.getRefreshToken());

    // 2. 토큰 재발급
    String refreshToken = this.jwtTokenProvider.createRefreshToken(memberInfo);
    String accessToken = this.jwtTokenProvider.createAccessToken(memberInfo);

    return new AuthResDto.createToken(refreshToken, accessToken);
  }

  /**
   * 멤버의 아이디로 권한 레벨을 조회합니다.
   */
  public MemberAuth getMemberAuth(CustomUserDetails customUserDetails) {
    // 1. 멤버의 아이디로 멤버를 조회합니다.
    MemberEntity member = this.memberRepository.getMemberByMemberId(customUserDetails.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorMessage.MEMBER_NOT_FOUND));

    // 2. 멤버의 아이디로 권한을 조회합니다.
    AuthorizationEntity authorization = this.authorizationRepository.getAuthorizationByAuthorizationId(
            member.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorMessage.NO_USER_AUTHORIZATION));

    // 3. 권한 레벨을 반환합니다.
    return MemberAuth.valueOf(authorization.getLevel());
  }
}
