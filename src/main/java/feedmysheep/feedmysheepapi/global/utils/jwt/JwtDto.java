package feedmysheep.feedmysheepapi.global.utils.jwt;

import lombok.Getter;
import lombok.Setter;

public class JwtDto {
  @Getter
  @Setter
  public static class memberInfo {
    private Long memberId;
    private int level;
    private String memberName;
  }
}
