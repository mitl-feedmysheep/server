package feedmysheep.feedmysheepapi.domain.auth.app.service;

import feedmysheep.feedmysheepapi.domain.auth.app.dto.AuthReqDto;
import feedmysheep.feedmysheepapi.domain.auth.app.dto.AuthResDto;
import feedmysheep.feedmysheepapi.domain.auth.app.repository.AuthorizationRepository;
import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
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

    // 2. 권한 업데이트
    MemberEntity member = this.memberRepository.getMemberByMemberId(memberInfo.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorMessage.MEMBER_NOT_FOUND));
    AuthorizationEntity authorization = this.authorizationRepository.getAuthorizationByAuthorizationId(
            member.getAuthorizationId())
        .orElseThrow(() -> new CustomException(ErrorMessage.NO_USER_AUTHORIZATION));
    memberInfo.setLevel(authorization.getLevel());

    // 3. 토큰 재발급
    String refreshToken = this.jwtTokenProvider.createRefreshToken(memberInfo);
    String accessToken = this.jwtTokenProvider.createAccessToken(memberInfo);

    return new AuthResDto.createToken(refreshToken, accessToken);
  }
}
