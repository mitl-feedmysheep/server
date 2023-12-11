package feedmysheep.feedmysheepapi.domain.media.app.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class MediaResDto {

  @Getter
  @Setter
  @RequiredArgsConstructor
  public static class getMediaListByScreenKey {

    private String medias;
  }
}


