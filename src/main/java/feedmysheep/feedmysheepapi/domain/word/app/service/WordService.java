package feedmysheep.feedmysheepapi.domain.word.app.service;

import feedmysheep.feedmysheepapi.domain.word.app.dto.WordReqDto;
import feedmysheep.feedmysheepapi.domain.word.app.dto.WordResDto;
import feedmysheep.feedmysheepapi.domain.word.app.repository.WordRepository;
import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;
import feedmysheep.feedmysheepapi.models.WordEntity;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordService {

  private final WordRepository wordRepository;

  @Autowired
  public WordService(WordRepository wordRepository) {
    this.wordRepository = wordRepository;
  }

  public WordResDto.getWordByMainAndSubScreen getWordByMainAndSubScreen(
      WordReqDto.mainAndSubScreen queries) {
    String mainScreen = queries.getMainScreen();
    String subScreen = queries.getSubScreen();

    // 스크린들에 맞는 데이터 가져오기
    List<WordEntity> wordList = this.wordRepository.getWordByMainScreenAndSubScreen(mainScreen,
        subScreen);
    if (wordList.isEmpty()) {
      throw new CustomException(ErrorMessage.NO_WORD_FOR_SCREENS);
    }

    // 말씀 랜덤으로 가져오기
    Collections.shuffle(wordList);
    WordEntity word = wordList.get(0);

    // 데이터 변환
    WordResDto.getWordByMainAndSubScreen wordRes = new WordResDto.getWordByMainAndSubScreen();
    wordRes.setBook(word.getBook());
    wordRes.setChapter(word.getChapter());
    wordRes.setVerse(word.getVerse());
    wordRes.setWords(word.getWords());

    return wordRes;
  }
}
