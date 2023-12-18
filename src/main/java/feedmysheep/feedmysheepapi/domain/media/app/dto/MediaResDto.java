package feedmysheep.feedmysheepapi.domain.media.app.dto;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class MediaResDto {

  @Getter
  @Setter
  @RequiredArgsConstructor
  public static class getMediasByScreenKey {

    private List<String> mediaList;
  }
}


