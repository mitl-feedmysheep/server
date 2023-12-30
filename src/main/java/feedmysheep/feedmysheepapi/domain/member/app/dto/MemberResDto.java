package feedmysheep.feedmysheepapi.domain.member.app.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
public class MemberResDto {

  @AllArgsConstructor
  @Getter
  public static class signUp {

    private String refreshToken;
    private String accessToken;
  }

  @AllArgsConstructor
  @Getter
  public static class signIn {

    private String refreshToken;
    private String accessToken;
  }

  @AllArgsConstructor
  @Getter
  public static class checkChurchMember {

    private boolean isChurchMember;
  }

  @AllArgsConstructor
  @Getter
  public static class getChurchWithBody {
    private Long churchId;
    private String churchName;
    private List<Body> bodyList;
  }

  @AllArgsConstructor
  @Getter
  public static class Body {
    private Long bodyId;
    private String bodyName;
  }

  @AllArgsConstructor
  @Getter
  public static class getMemberInfo {
    private String memberName;
    private String sex;
    private LocalDate birthday;
    private String phone;
    private String profileImageUrl;
    private String address;
    private String memberDescription;
    private String email;
    private LocalDateTime registeredAt;
  }

  @AllArgsConstructor
  @Getter
  public static class getCellByBodyIdAndMemberId {
    private int cellMemberCount;
    private Long cellId;
    private String cellName;
    private String cellLogoUrl;
    private String cellPlace;
    private String description;
  }
}
