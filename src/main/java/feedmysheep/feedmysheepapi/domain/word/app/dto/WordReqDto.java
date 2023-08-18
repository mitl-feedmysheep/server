package feedmysheep.feedmysheepapi.domain.word.app.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Data
public class WordReqDto {

  @AllArgsConstructor
  @Getter
  public static class mainAndSubScreen {
    @NotEmpty(message = "메인 스크린 값은 존재해야해요.")
    private String mainScreen;
    @Nullable
    private String subScreen;
  }
}