package feedmysheep.feedmysheepapi.domain.text.app.service;

import feedmysheep.feedmysheepapi.domain.text.app.dto.TextMapper;
import feedmysheep.feedmysheepapi.domain.text.app.dto.TextReqDto;
import feedmysheep.feedmysheepapi.domain.text.app.dto.TextResDto;
import feedmysheep.feedmysheepapi.domain.text.app.repository.TextRepository;
import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;
import feedmysheep.feedmysheepapi.models.TextEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TextServiceImpl implements TextService {

  private final TextRepository textRepository;
  private final TextMapper textMapper;

  public TextResDto.getTextByScreenKey getTextByScreenKey(TextReqDto.getTextByScreenKey query) {
    String screenKey = query.getScreenKey();

    // 1. 스크린들에 맞는 데이터 가져오기.
    TextEntity text = this.textRepository.getTextByScreenKey(screenKey)
        .orElseThrow(() -> new CustomException(ErrorMessage.NO_TEXT_FOR_SCREENS));

    // 2. 반환
    return this.textMapper.getTextByScreenKey(text);
  }
}
