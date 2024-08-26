package feedmysheep.feedmysheepapi.domain.cell.app.dto;

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
public class CellResDto {

  @Getter
  @Setter
  @RequiredArgsConstructor
  public static class getCellMemberByCellId {

    private UUID memberId;
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

    private UUID cellGatheringId;
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

  @Getter
  @Setter
  @RequiredArgsConstructor
  public static class getCellGatheringAndMemberListAndPrayerList {

    private UUID cellGatheringId;
    private String gatheringTitle;
    private LocalDate gatheringDate;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private String gatheringPlace;
    private String gatheringPhotoUrl;
    private String description;
    private List<CellServiceDto.cellGatheringMember> cellGatheringMemberList;
  }

  @Getter
  @Setter
  public static class cellGatheringMemberPrayer {

    private UUID cellGatheringMemberId;
    private String prayerRequest;
    private boolean isAnswered;
  }

  @Getter
  @Setter
  @RequiredArgsConstructor
  public static class getCellByCellId {

    private UUID cellId;
    private String cellName;
    private String cellLogoUrl;
    private String cellPlace;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
  }

  @Getter
  @Setter
  @AllArgsConstructor
  public static class createCellGatheringByCellId {

    private UUID cellGatheringId;
  }
}
