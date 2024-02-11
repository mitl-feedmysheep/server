package feedmysheep.feedmysheepapi.domain.text.app.service;

import feedmysheep.feedmysheepapi.domain.text.app.dto.TextReqDto;
import feedmysheep.feedmysheepapi.domain.text.app.dto.TextResDto;

public interface TextService {

  TextResDto.getTextByScreenKey getTextByScreenKey(TextReqDto.getTextByScreenKey query);
}
