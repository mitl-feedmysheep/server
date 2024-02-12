package feedmysheep.feedmysheepapi.domain.cell.app.dto;

import feedmysheep.feedmysheepapi.models.CellGatheringEntity;
import feedmysheep.feedmysheepapi.models.CellGatheringMemberEntity;
import feedmysheep.feedmysheepapi.models.CellGatheringMemberPrayerEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
public class CellServiceDto {

  @Getter
  @Setter
  public static class cellGatheringMember {

    private Long cellGatheringMemberId;
    private Long cellGatheringId;
    private Long cellMemberMapId;
    private boolean isLeader;
    private String memberName;
    private LocalDate birthday;
    private String profileImageUrl;
    private boolean worshipAttendance;
    private boolean cellGatheringAttendance;
    private String story;
    private List<cellGatheringMemberPrayer> cellGatheringMemberPrayerList;
  }

  @Getter
  @Setter
  public static class cellGatheringMemberPrayer {

    private UUID cellGatheringMemberId;
    private String prayerRequest;
    private boolean isAnswered;
  }

  @Getter
  @AllArgsConstructor
  public static class updateAttendancesAndStoryWhenExisting {

    private UUID cellGatheringMemberId;
    private Boolean worshipAttendance;
    private Boolean cellGatheringAttendance;
    private String story;
    private UUID memberId;
  }

  @Getter
  @AllArgsConstructor
  public static class updatePrayerById {
    private UUID cellGatheringMemberPrayerId;
    private String prayerRequest;
    private UUID memberId;
  }
}
