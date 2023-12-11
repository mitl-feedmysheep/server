package feedmysheep.feedmysheepapi.domain.text.app.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
public class TextResDto {

  @Getter
  @Setter
  @RequiredArgsConstructor
  public static class getTextByScreenKey {

    private String text;
  }

}
