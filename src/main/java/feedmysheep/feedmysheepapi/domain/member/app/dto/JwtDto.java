package feedmysheep.feedmysheepapi.domain.member.app.dto;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class JwtDto {
  @Getter
  @Setter
  public static class memberInfo {
    private Long memberId;
    private String level;
    private String memberName;
  }
}
