package feedmysheep.feedmysheepapi.domain.auth.app.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AuthResDto {
  @AllArgsConstructor
  @Getter
  @Setter
  public static class createAccessToken {
   private String accessToken;
  }
}
