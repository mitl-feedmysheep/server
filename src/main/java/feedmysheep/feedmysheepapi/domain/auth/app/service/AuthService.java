package feedmysheep.feedmysheepapi.domain.auth.app.service;

import feedmysheep.feedmysheepapi.domain.auth.app.dto.AuthReqDto;
import feedmysheep.feedmysheepapi.domain.auth.app.dto.AuthResDto;
import feedmysheep.feedmysheepapi.domain.auth.app.dto.AuthResDto.createToken;
import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
import feedmysheep.feedmysheepapi.global.utils.jwt.JwtDto;
import feedmysheep.feedmysheepapi.global.utils.jwt.JwtTokenProvider;
import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {
  private final JwtTokenProvider jwtTokenProvider;
  private final MemberRepository memberRepository;

  @Autowired
  public AuthService(JwtTokenProvider jwtTokenProvider, MemberRepository memberRepository) {
    this.jwtTokenProvider = jwtTokenProvider;
    this.memberRepository = memberRepository;
  }

  public AuthResDto.createToken createToken (AuthReqDto.createToken body) {
    // 1. 유효한 리프레시 토큰인지 검사
    JwtDto.memberInfo memberInfo = this.jwtTokenProvider.validateToken(body.getRefreshToken());

    // 2. 권한 업데이트
    MemberEntity member = this.memberRepository.findById(memberInfo.getMemberId()).orElseThrow(() -> new CustomException(ErrorMessage.MEMBER_NOT_FOUND));
    memberInfo.setLevel(member.getAuthorization().getLevel());

    // 3. 토큰 재발급
    String refreshToken = this.jwtTokenProvider.createRefreshToken(memberInfo);
    String accessToken = this.jwtTokenProvider.createAccessToken(memberInfo);

    return new createToken(refreshToken, accessToken);
  }
}
