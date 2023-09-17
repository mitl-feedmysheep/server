package feedmysheep.feedmysheepapi.domain.member.app.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class MemberResDto {

  @AllArgsConstructor
  @Getter
  public  static class signUp {
    private String refreshToken;
    private String accessToken;
  }

  @AllArgsConstructor
  @Getter
  public static class checkChurchMember {
    private boolean isChurchMember;
  }
}
