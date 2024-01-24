package feedmysheep.feedmysheepapi.domain.cell.app.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class CellReqDto {

  @AllArgsConstructor
  @Getter
  public static class getCell {

    @NotEmpty
    private Long cellId;
  }
};

