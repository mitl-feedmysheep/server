package feedmysheep.feedmysheepapi.domain.word.app.service;

import feedmysheep.feedmysheepapi.domain.word.app.dto.WordMapper;
import feedmysheep.feedmysheepapi.domain.word.app.dto.WordReqDto;
import feedmysheep.feedmysheepapi.domain.word.app.dto.WordResDto;
import feedmysheep.feedmysheepapi.domain.word.app.repository.WordRepository;
import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;
import feedmysheep.feedmysheepapi.models.WordEntity;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {

  private final WordRepository wordRepository;
  private final WordMapper wordMapper;


  public WordResDto.getWordByScreenKey getWordByScreenKey(WordReqDto.getWordByScreenKey query) {
    String screenKey = query.getScreenKey();

    // 1. 스크린들에 맞는 데이터 가져오기
    List<WordEntity> wordList = this.wordRepository.findAllByScreenKey(screenKey);
    if (wordList.isEmpty()) {
      throw new CustomException(ErrorMessage.NO_WORD_FOR_SCREENS);
    }

    // 2. 말씀 랜덤으로 가져오기
    Collections.shuffle(wordList);
    WordEntity word = wordList.get(0);

    // 3. 반환
    return this.wordMapper.getWordByScreenKey(word);
  }
}
