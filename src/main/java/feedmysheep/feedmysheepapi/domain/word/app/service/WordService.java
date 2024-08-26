package feedmysheep.feedmysheepapi.domain.word.app.service;

import feedmysheep.feedmysheepapi.domain.word.app.dto.WordReqDto;
import feedmysheep.feedmysheepapi.domain.word.app.dto.WordResDto;

public interface WordService {

  WordResDto.getWordByScreenKey getWordByScreenKey(WordReqDto.getWordByScreenKey query);
}
