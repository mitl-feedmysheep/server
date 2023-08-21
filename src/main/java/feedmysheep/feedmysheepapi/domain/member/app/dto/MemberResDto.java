package feedmysheep.feedmysheepapi.domain.member.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class MemberResDto {
  @AllArgsConstructor
  @Getter
  public static class checkPhoneDuplication {
    private Boolean isDuplicated;
  }
}
