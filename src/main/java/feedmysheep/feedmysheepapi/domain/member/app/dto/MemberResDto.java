package feedmysheep.feedmysheepapi.domain.member.app.dto;

import feedmysheep.feedmysheepapi.models.MemberEntity.Sex;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class MemberResDto {

  @AllArgsConstructor
  @Getter
  public  static class signUp {
    private String accessToken;
  }

  @AllArgsConstructor
  @Getter
  public static class checkChurchMember {
    private boolean isChurchMember;
  }
}
