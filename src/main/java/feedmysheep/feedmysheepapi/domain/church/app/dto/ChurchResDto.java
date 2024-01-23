package feedmysheep.feedmysheepapi.domain.church.app.dto;

import feedmysheep.feedmysheepapi.models.BodyEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
public class ChurchResDto {

  @Getter
  @Setter
  @RequiredArgsConstructor
  public static class getChurch {

    private Long churchId;
    private String churchName;
    private String churchLocation;
  }

  @Getter
  @Setter
  @RequiredArgsConstructor
  public static class getBodyByChurchId {

    private Long bodyId;
    private String bodyName;
  }

  @Getter
  @AllArgsConstructor
  public static class getMemberEventByMemberId {

    private List<Event> memberList;
  }
  @AllArgsConstructor
  @Getter
  public static class Event {
    private Long memberId;
    private String memberName;
    private String sex;
    private LocalDate birthday;
    private String dayOfWeek;
    private String phone;
    private String profileImageUrl;
    private String eventName;


  }
}
