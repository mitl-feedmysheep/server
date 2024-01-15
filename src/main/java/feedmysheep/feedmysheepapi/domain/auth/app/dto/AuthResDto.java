package feedmysheep.feedmysheepapi.domain.auth.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AuthResDto {

  @Getter
  @Setter
  @AllArgsConstructor
  public static class createToken {

    private String refreshToken;
    private String accessToken;
  }

  @Getter
  @Setter
  @AllArgsConstructor
  public static class getMemberAuthByScreenKey {

    private boolean isAccessible;
  }
}
