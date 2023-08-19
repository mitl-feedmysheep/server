package feedmysheep.feedmysheepapi.global.utils;

import java.util.Date;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JwtUtil {
  @Value("${jwt.secretKey}")
  private String JWT_SECRET;

  @Value("${jwt.refreshExpiry}")
  private Long REFRESH_EXPIRY;

  @Value("${jwt.accessExpiry}")
  private Long ACCESS_EXPIRY;

  public static String generateAccessToken() {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + this.ACCESS_EXPIRY)
  }
}
