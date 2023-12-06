package feedmysheep.feedmysheepapi.domain.member.app.dto;

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
}
