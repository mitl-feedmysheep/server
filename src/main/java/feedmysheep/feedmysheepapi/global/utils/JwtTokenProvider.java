package feedmysheep.feedmysheepapi.global.utils;

import feedmysheep.feedmysheepapi.domain.member.app.dto.JwtDto;
import feedmysheep.feedmysheepapi.domain.member.app.dto.JwtDto.memberInfo;
import feedmysheep.feedmysheepapi.global.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.response.error.ErrorMessage;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
  @Value("${jwt.secretKey}")
  private String secretKey;

  @Value("${jwt.refreshExpiry}")
  private Long refreshExpiry;

  @Value("${jwt.accessExpiry}")
  private Long accessExpiry;

  // ACCESS 토큰 생성
  public String createAccessToken(MemberEntity member) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + this.accessExpiry);

    // Private Claims
    Map<String, Object> privateClaims = new HashMap<>();
    privateClaims.put("memberId", member.getMemberId());
    privateClaims.put("level", member.getAuthorization().getLevel());
    privateClaims.put("memberName", member.getMemberName());

    return Jwts.builder()
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .setClaims(privateClaims)
        .signWith(SignatureAlgorithm.HS256, this.secretKey)
        .compact();
  }

  // REFRESH 토큰 생성
  public String createRefreshToken(MemberEntity member) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + this.refreshExpiry);

    // Private Claims
    Map<String, Object> privateClaims = new HashMap<>();
    privateClaims.put("memberId", member.getMemberId());
    privateClaims.put("level", member.getAuthorization().getLevel());
    privateClaims.put("memberName", member.getMemberName());

    return Jwts.builder()
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .setClaims(privateClaims)
        .signWith(SignatureAlgorithm.HS256, this.secretKey)
        .compact();
  }

  // ACCESS 토큰 검증
  public JwtDto.memberInfo validateAccessToken(String accessToken) {
    try {
      Claims claims = Jwts
          .parserBuilder()
          .setSigningKey(Keys.hmacShaKeyFor(this.secretKey.getBytes()))
          .build()
          .parseClaimsJws(accessToken)
          .getBody();

      JwtDto.memberInfo memberInfo = new memberInfo();
      memberInfo.setMemberId(claims.get("memberId", Long.class));
      memberInfo.setLevel(claims.get("level", String.class));
      memberInfo.setMemberName(claims.get("memberName", String.class));

      return memberInfo;
    } catch (ExpiredJwtException e) {
      // TODO 토큰이 만료되었을 경우 어떻게 처리할지 생각해보기
    } catch (UnsupportedJwtException | MalformedJwtException e) {
      throw new CustomException(ErrorMessage.INVALID_JWT);
    }
  }
}
