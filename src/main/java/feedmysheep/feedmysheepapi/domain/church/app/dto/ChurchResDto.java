package feedmysheep.feedmysheepapi.domain.church.app.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
public class ChurchResDto {

  @Getter
  @Setter
  @RequiredArgsConstructor
  public static class getChurch {

    private Long churchId;
    private String churchName;
    private String churchLocation;
  }

  @Getter
  @Setter
  @RequiredArgsConstructor
  public static class getBodyByChurchId {

    private Long bodyId;
    private String bodyName;
  }
}
