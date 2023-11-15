package feedmysheep.feedmysheepapi.domain.church.app.dto;

import lombok.AllArgsConstructor;
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
    // TODO mapStruct 한번 보기!!!
  }

  @Getter
  @AllArgsConstructor
  public static class getBodyListByChurchId {

    private Long bodyId;
    private String bodyName;
  }
}
