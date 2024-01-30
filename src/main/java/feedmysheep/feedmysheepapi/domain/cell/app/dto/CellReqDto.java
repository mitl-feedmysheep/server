package feedmysheep.feedmysheepapi.domain.cell.app.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
}
