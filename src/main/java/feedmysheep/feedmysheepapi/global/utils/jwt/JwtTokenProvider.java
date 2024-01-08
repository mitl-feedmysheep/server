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
    Date expiryDate = new Date(now.getTime() + JWT.ACCESS_EXPIRY);

    // Private Claims
    Map<String, Object> privateClaims = new HashMap<>();
    privateClaims.put("memberId", memberInfo.getMemberId());
    privateClaims.put("memberName", memberInfo.getMemberName());

    return Jwts.builder().setClaims(privateClaims).setIssuedAt(now).setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS256, this.secretKey).compact();
  }

  // REFRESH 토큰 생성
  public String createRefreshToken(JwtDto.memberInfo memberInfo) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + JWT.REFRESH_EXPIRY);

    // Private Claims
    Map<String, Object> privateClaims = new HashMap<>();
    privateClaims.put("memberId", memberInfo.getMemberId());
    privateClaims.put("memberName", memberInfo.getMemberName());

    return Jwts.builder().setClaims(privateClaims).setIssuedAt(now).setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS256, this.secretKey).compact();
  }

  // 토큰 검증
  public JwtDto.memberInfo validateToken(String token) {
    try {
      Claims claims = Jwts.parserBuilder().setSigningKey(this.secretKey).build()
          .parseClaimsJws(token).getBody();

      JwtDto.memberInfo memberInfo = new memberInfo();
      memberInfo.setMemberId(claims.get("memberId", Long.class));
      memberInfo.setMemberName(claims.get("memberName", String.class));

      return memberInfo;
    } catch (UnsupportedJwtException | MalformedJwtException e) {
      throw new CustomException(ErrorMessage.INVALID_JWT);
    } catch (IllegalArgumentException e) {
      throw new CustomException(ErrorMessage.NO_TOKEN);
    }
    // ExpiredJwtException 은 AOP에서 잡기 위해서 처리를 하지 않음!!
  }
}