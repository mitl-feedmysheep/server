package feedmysheep.feedmysheepapi.domain.word.app.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;

public class WordDto {

//  @AllArgsConstructor
//  public static class getWord {
//
//  }

  private String mainScreen;
  private String subScreen;
  private boolean isValid;
  private LocalDate displayStartDate;
  private LocalDate displayEndDate;
  private String words;
  private String book;
  private int chapter;
  private int verse;
}
