package feedmysheep.feedmysheepapi.domain.word.app.dto;

import feedmysheep.feedmysheepapi.models.WordEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WordMapper {

  WordResDto.getWordByScreenKey getWordByScreenKey(WordEntity word);
}
