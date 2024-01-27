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
  public static class getCell {

    @NotEmpty
    private Long cellId;
  }
}
