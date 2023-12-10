package feedmysheep.feedmysheepapi.domain.text.app.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class TextReqDto {

  @SuppressWarnings("checkstyle:MissingJavadocType")
  @AllArgsConstructor
  @Getter
  public static class getTextByScreenKey {

    @NotEmpty(message = "스크린 키 값이 존재하지 않아요.")
    private String screenKey;
  }
}
