package feedmysheep.feedmysheepapi.domain.word.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class WordResDto {

  @Getter
  @AllArgsConstructor
  public static class getWordByMainAndSubScreen {
    private String words;
    private String book;
    private int chapter;
    private int verse;
  }
}
