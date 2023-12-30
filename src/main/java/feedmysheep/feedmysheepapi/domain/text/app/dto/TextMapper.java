package feedmysheep.feedmysheepapi.domain.text.app.dto;

import feedmysheep.feedmysheepapi.models.TextEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TextMapper {

  TextResDto.getTextByScreenKey getTextByScreenKey(TextEntity text);
}
