package feedmysheep.feedmysheepapi.domain.church.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class ChurchResDto {

  @Getter
  @AllArgsConstructor
  public static class getChurchList {
    private Long churchId;
    private String churchName;
    private String churchLocation;
  }
}
