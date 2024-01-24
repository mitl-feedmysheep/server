package feedmysheep.feedmysheepapi.domain.cell.app.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
public class CellResDto {

  @Getter
  @Setter
  @RequiredArgsConstructor
  public static class getCell {

    private Long cellId;
    private String cellName;
    private String cellLogoUrl;
    private String cellPlace;
    private String description;
    private String startDate;
    private String endDate;
  }
}
