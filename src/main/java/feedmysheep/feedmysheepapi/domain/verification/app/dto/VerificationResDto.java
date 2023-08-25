package feedmysheep.feedmysheepapi.domain.verification.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class VerificationResDto {
  @Getter
  @AllArgsConstructor
  public static class countByPhoneAndValidDate {
    private int count;
  }
}
