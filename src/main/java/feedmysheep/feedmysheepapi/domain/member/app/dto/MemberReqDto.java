package feedmysheep.feedmysheepapi.domain.member.app.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class MemberReqDto {

  @AllArgsConstructor
  @Getter
  public static class sendVerificationCode {
    @NotEmpty(message = "휴대폰 번호가 존재하지 않아요.")
    private String phone;
  }
}
