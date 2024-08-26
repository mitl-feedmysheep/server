package feedmysheep.feedmysheepapi.domain.media.app.service;

import feedmysheep.feedmysheepapi.domain.media.app.dto.MediaReqDto;
import feedmysheep.feedmysheepapi.domain.media.app.dto.MediaResDto;
import java.util.List;

public interface MediaService {

  List<MediaResDto.getMediaByScreenKey> getMediasByScreenKey(
      MediaReqDto.getMediasByScreenKey query);
}