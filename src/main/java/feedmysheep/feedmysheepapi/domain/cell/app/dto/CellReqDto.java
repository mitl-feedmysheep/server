package feedmysheep.feedmysheepapi.domain.cell.app.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

public class CellReqDto {

  @AllArgsConstructor
  @Getter
  public static class getCellGatheringListByCellId {

    private int month;
  }

  @Getter
  @RequiredArgsConstructor
  public static class updateCellGatheringMemberByCellGatheringMemberId {

    private Boolean worshipAttendance;
    private Boolean cellGatheringAttendance;
    private String story;
  }

  @Getter
  public static class updateCellGatheringMemberPrayer {

    private Long cellGatheringMemberPrayerId;
    private String prayerRequest;
  }

  @Getter
  public static class deleteCellGatheringMemberPrayer {

    private List<Integer> cellGatheringMemberPrayerIdList;
  }

  @Getter
  @Setter
  @RequiredArgsConstructor
  public static class createCellGatheringByCellId {
    @NotNull
    private LocalDate gatheringDate;
    @NotNull
    private LocalDateTime startedAt;
    @NotNull
    private LocalDateTime endedAt;
    @NotNull
    private String gatheringPlace;
    @Nullable
    private String description;
  }
}



