package feedmysheep.feedmysheepapi.domain.media.app.dto;

import feedmysheep.feedmysheepapi.models.MediaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaMapper {

  MediaResDto.getMediaByScreenKey getMediasByScreenKey(MediaEntity mediaUrl);
}