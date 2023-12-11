package feedmysheep.feedmysheepapi.domain.text.app.service;

import feedmysheep.feedmysheepapi.domain.text.app.dto.TextMapper;
import feedmysheep.feedmysheepapi.domain.text.app.dto.TextReqDto;
import feedmysheep.feedmysheepapi.domain.text.app.dto.TextResDto;
import feedmysheep.feedmysheepapi.domain.text.app.repository.TextRepository;
import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;
import feedmysheep.feedmysheepapi.models.TextEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TextService {

  private final TextRepository textRepository;

  private final TextMapper textMapper;

  @Autowired
  public TextService(TextRepository textRepository, TextMapper textMapper) {
    this.textRepository = textRepository;
    this.textMapper = textMapper;
  }

  public TextResDto.getTextByScreenKey getTextByScreenKey(TextReqDto.getTextByScreenKey query) {
    String screenKey = query.getScreenKey();

    // 1. 스크린들에 맞는 데이터 가져오기.
    List<TextEntity> textList = this.textRepository.getTextListByScreenKey(screenKey);
    if (textList.isEmpty()) {
      throw new CustomException(ErrorMessage.NO_WORD_FOR_SCREENS);
    }

    // 2. 텍스트 가져오기
    TextEntity text = textList.get(0);

    // 3. 반환
    return this.textMapper.getTextByScreenKey(text);
  }
}
