package feedmysheep.feedmysheepapi.domain.word.app.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Data
public class WordReqDto {

  @AllArgsConstructor
  @Getter
  public static class getWordByScreenKey {
    @NotEmpty(message = "스크린 키 값이 존재하지 않아요.")
    private String screenKey;
  }
}