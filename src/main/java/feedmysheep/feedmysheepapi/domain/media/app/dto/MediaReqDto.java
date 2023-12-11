package feedmysheep.feedmysheepapi.domain.media.app.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class MediaReqDto {


  @AllArgsConstructor
  @Getter
  public static class getMediasByScreenKey {

    @NotEmpty(message = "스크린 키 값이 존재하지 않아요.")
    private String sreenKey;

  }
}




