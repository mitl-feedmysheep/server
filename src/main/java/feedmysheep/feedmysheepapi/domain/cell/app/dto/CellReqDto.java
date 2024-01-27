package feedmysheep.feedmysheepapi.domain.cell.app.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class CellReqDto {

  @AllArgsConstructor
  @Getter
  public static class getCellGatheringListByCellId {

    private int month;
  }

  @AllArgsConstructor
  @Getter
  public static class createCellGathering {

    @NotEmpty
    private Long gatheringTitle;
    @NotEmpty
    private Long gatheringDate;
    @NotEmpty
    private Long startedAt;
    @NotEmpty
    private Long endedAt;
    @NotEmpty
    private Long gatheringPlace;
  }


}
