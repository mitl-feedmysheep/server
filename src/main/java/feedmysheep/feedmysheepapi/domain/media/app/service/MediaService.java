package feedmysheep.feedmysheepapi.domain.media.app.service;

import feedmysheep.feedmysheepapi.domain.media.app.dto.MediaMapper;
import feedmysheep.feedmysheepapi.domain.media.app.dto.MediaReqDto;
import feedmysheep.feedmysheepapi.domain.media.app.dto.MediaResDto;
import feedmysheep.feedmysheepapi.domain.media.app.repository.MediaRepository;
import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;
import feedmysheep.feedmysheepapi.models.MediaEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaService {

  private final MediaRepository mediaRepository;

  private final MediaMapper mediaMapper;

  @Autowired
  public MediaService(MediaRepository mediaRepository, MediaMapper mediaMapper) {
    this.mediaRepository = mediaRepository;
    this.mediaMapper = mediaMapper;
  }

  public List<MediaResDto.getMediaByScreenKey> getMediasByScreenKey(
      MediaReqDto.getMediasByScreenKey query) {
    String screenKey = query.getScreenKey();

    // 1. 스크린들에 맞는 데이터 가져오기
    List<MediaEntity> mediaList = this.mediaRepository.getMediasByScreenKey(screenKey);
    if (mediaList.isEmpty()) {
      throw new CustomException(ErrorMessage.NO_MEDIALIST_FOR_SCREENS);
    }

    return this.mediaMapper.getMediaByScreenKey(mediaList);
  }
}





