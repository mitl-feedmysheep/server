package feedmysheep.feedmysheepapi.global.utils.jwt;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

public class JwtDto {

  @Getter
  @Setter
  public static class memberInfo {

    private UUID memberId;
    private String memberName;
  }
}
