package feedmysheep.feedmysheepapi.global.utils.jwt;

import feedmysheep.feedmysheepapi.global.policy.CONSTANT.JWT;
import feedmysheep.feedmysheepapi.global.utils.jwt.JwtDto.memberInfo;
import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtTokenProvider {
  private final String secretKey = JWT.SECRET_KEY;

  // ACCESS 토큰 생성
  public String createAccessToken(JwtDto.memberInfo memberInfo) {
    Date now = new Date();
    Long accessExpiry = JWT.ACCESS_EXPIRY;
    Date expiryDate = new Date(now.getTime() + accessExpiry);

    // Private Claims
    Map<String, Object> privateClaims = new HashMap<>();
    privateClaims.put("memberId", memberInfo.getMemberId());
    privateClaims.put("level", memberInfo.getLevel());
    privateClaims.put("memberName", memberInfo.getMemberName());

    return Jwts.builder()
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .setClaims(privateClaims)
        .signWith(SignatureAlgorithm.HS256, this.secretKey)
        .compact();
  }

  // REFRESH 토큰 생성
  public String createRefreshToken(JwtDto.memberInfo memberInfo) {
    Date now = new Date();
    Long refreshExpiry = JWT.REFRESH_EXPIRY;
    Date expiryDate = new Date(now.getTime() + refreshExpiry);

    // Private Claims
    Map<String, Object> privateClaims = new HashMap<>();
    privateClaims.put("memberId", memberInfo.getMemberId());
    privateClaims.put("level", memberInfo.getLevel());
    privateClaims.put("memberName", memberInfo.getMemberName());

    return Jwts.builder()
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .setClaims(privateClaims)
        .signWith(SignatureAlgorithm.HS256, this.secretKey)
        .compact();
  }

  // 토큰 검증
  public JwtDto.memberInfo validateToken(String token) {
    try {
      Claims claims = Jwts
          .parserBuilder()
          .setSigningKey(Keys.hmacShaKeyFor(this.secretKey.getBytes()))
          .build()
          .parseClaimsJws(token)
          .getBody();

      JwtDto.memberInfo memberInfo = new memberInfo();
      memberInfo.setMemberId(claims.get("memberId", Long.class));
      memberInfo.setLevel(claims.get("level", int.class));
      memberInfo.setMemberName(claims.get("memberName", String.class));

      return memberInfo;
    } catch (UnsupportedJwtException | MalformedJwtException e) {
      throw new CustomException(ErrorMessage.INVALID_JWT);
    }
    // ExpiredJwtException 은 AOP에서 잡기 위해서 처리를 하지 않음!!
  }
}