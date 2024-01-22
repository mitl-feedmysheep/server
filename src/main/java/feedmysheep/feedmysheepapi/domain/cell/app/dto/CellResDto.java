package feedmysheep.feedmysheepapi.domain.cell.app.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
public class CellResDto {

  @Getter
  @Setter
  @RequiredArgsConstructor
  public static class getCellMemberByCellId {

    private Long memberId;
    private boolean isLeader;
    private boolean isBirthdayThisMonth;
    private String birthday;
    private String profileImageUrl;
  }

  @Getter
  @Setter
  @AllArgsConstructor
  public static class getGatheringsAndPrayersCount {

    private int totalGatheringCount;
    private int totalPrayerRequestCount;
  }

  @Getter
  @Setter
  @RequiredArgsConstructor
  public static class getCellGathering {
    private Long cellGatheringId;
    private int numberOfGathering;
    private String gatheringTitle;
    private LocalDate gatheringDate;
    private String dayOfWeek;
    private int totalWorshipAttendanceCount;
    private int totalCellGatheringAttendanceCount;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private String gatheringPlace;
    private String gatheringPhotoUrl;
    private String description;
  }
}
