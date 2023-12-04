package feedmysheep.feedmysheepapi.domain.word.app.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
public class WordResDto {

  @Getter
  @Setter
  @RequiredArgsConstructor
  public static class getWordByScreenKey {

    private String words;
  }
}
