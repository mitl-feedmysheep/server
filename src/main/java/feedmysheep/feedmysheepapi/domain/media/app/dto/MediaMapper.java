package feedmysheep.feedmysheepapi.domain.media.app.dto;

import feedmysheep.feedmysheepapi.models.MediaEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaMapper {

  List<MediaResDto.getMediaByScreenKey> getMediaByScreenKey(List<MediaEntity> mediaList);
}