package feedmysheep.feedmysheepapi.domain.auth.app.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AuthReqDto {
  @AllArgsConstructor
  @Getter
  @Setter
  public static class createToken {
    @NotEmpty(message = "리프레시 토큰이 존재하지 않아요.")
    private String refreshToken;
  }
}
