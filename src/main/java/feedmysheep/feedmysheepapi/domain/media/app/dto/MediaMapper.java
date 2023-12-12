package feedmysheep.feedmysheepapi.domain.media.app.dto;

import feedmysheep.feedmysheepapi.models.MediaEntity;
import jakarta.persistence.Column;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaMapper {

  MediaResDto.getMediaListByScreenKey getMediasByScreenKey(MediaEntity media);
}

