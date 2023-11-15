package feedmysheep.feedmysheepapi.domain.member.app.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
public class MemberReqDto {

  @AllArgsConstructor
  @Getter
  @Setter
  public static class sendVerificationCode {

    @NotEmpty(message = "휴대폰 번호가 존재하지 않아요.")
    private String phone;
  }

  @AllArgsConstructor
  @Getter
  @Setter
  public static class checkVerificationCode {

    @NotEmpty(message = "휴대폰 번호가 존재하지 않아요.")
    private String phone;
    @NotEmpty(message = "인증번호가 존재하지 않아요.")
    private String code;
  }

  @AllArgsConstructor
  @Getter
  @Setter
  public static class checkEmailDuplication {

    @NotEmpty(message = "이메일 주소가 존재하지 않아요.")
    private String email;
  }

  @AllArgsConstructor
  @NoArgsConstructor // 직렬화하는데 필요함
  @Getter
  @Setter
  public static class signUp {

    @NotEmpty(message = "이름이 존재하지 않아요.")
    @Size(max = 10, message = "이름은 최대 10자까지 입력 가능합니다.")
    private String memberName;
    @NotEmpty(message = "성별이 존재하지 않아요")
    @Pattern(regexp = "^[MF]$", message = "성별은 'M' 또는 'F'로만 설정할 수 있습니다.")
    private String sex;
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate birthday;
    @NotEmpty(message = "휴대폰번호가 존재하지 않아요")
    @Size(max = 20, message = "휴대폰번호는 최대 20자리까지 입력 가능합니다.")
    private String phone;
    @NotEmpty(message = "이메일이 존재하지 않아요")
    private String email;
    @NotEmpty(message = "비밀번호가 존재하지 않아요.")
    private String password;
    @NotEmpty(message = "주소가 존재하지 않아요.")
    private String address;
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  @Setter
  public static class signIn {

    @NotEmpty(message = "이메일이 존재하지 않아요")
    private String email;
    @NotEmpty(message = "비밀번호가 존재하지 않아요.")
    private String password;
  }
}
