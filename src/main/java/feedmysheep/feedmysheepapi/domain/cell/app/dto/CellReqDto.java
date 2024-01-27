package feedmysheep.feedmysheepapi.domain.cell.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class CellReqDto {

  @AllArgsConstructor
  @Getter
  public static class getCellGatheringListByCellId {
      private int month;
  }
}
