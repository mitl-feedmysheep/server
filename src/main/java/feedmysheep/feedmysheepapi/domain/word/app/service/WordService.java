package feedmysheep.feedmysheepapi.domain.word.app.service;

import feedmysheep.feedmysheepapi.domain.word.app.dto.WordReqDto;
import feedmysheep.feedmysheepapi.domain.word.app.dto.WordResDto;
import feedmysheep.feedmysheepapi.domain.word.app.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordService {
  private final WordRepository wordRepository;

  @Autowired
  public WordService(WordRepository wordRepository) {
    this.wordRepository = wordRepository;
  }

  public WordResDto.getWordByMainAndSubScreen getWordByMainAndSubScreen(WordReqDto.mainAndSubScreen queries) {
//    if (true) throw new CustomException("이게 되네?");
    String mainScreen = queries.getMainScreen();
    String subScreen = queries.getSubScreen();

    return wordRepository.findByMainScreenAndSubScreen(mainScreen, subScreen);
  }
}
