//package feedmysheep.feedmysheepapi.global.utils;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import java.security.Key;
//import java.util.Base64;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Component
//public class JwtUtil {
//  private final String secretKey;
//  private final Long refreshExpiry;
//  private final Long accessExpiry;
//  private final static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//  public JwtUtil(@Value("${jwt.secretKey}") String secretKey, @Value("${jwt.refreshExpiry}") Long refreshExpiry, @Value("${jwt.accessExpiry}") Long accessExpiry) {
//    this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
//    this.refreshExpiry = refreshExpiry;
//    this.accessExpiry = accessExpiry;
//  }
//
//  public String generateAccessToken(Long memberId, int level, String memberName) {
//    Date now = new Date();
//    Date expiryDate = new Date(now.getTime() + this.accessExpiry);
//
//    // Private Claims
//    Map<String, Object> privateClaims = new HashMap<>();
//    privateClaims.put("memberId", memberId);
//    privateClaims.put("level", level);
//    privateClaims.put("memberName", memberName);
//
//    return Jwts.builder()
//        .setIssuedAt(now)
//        .setExpiration(expiryDate)
//        .setClaims(privateClaims)
//        .signWith(SignatureAlgorithm.HS256, this.secretKey)
//        .compact();
//  }
//}
