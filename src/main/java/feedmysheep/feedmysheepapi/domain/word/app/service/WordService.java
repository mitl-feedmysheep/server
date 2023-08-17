package feedmysheep.feedmysheepapi.domain.word.app.service;

import feedmysheep.feedmysheepapi.domain.word.app.dto.WordDto;
import feedmysheep.feedmysheepapi.domain.word.app.repository.WordRepository;
import feedmysheep.feedmysheepapi.global.dto.response.error.ErrorEntity;
import feedmysheep.feedmysheepapi.models.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordService {
  private final WordRepository wordRepository;

  @Autowired
  public WordService(WordRepository wordRepository) {
    this.wordRepository = wordRepository;
  }

  public WordDto.getWordByMainAndSubScreen getWordByMainAndSubScreen(String mainScreen, String subScreen) {
    if (true) return new ErrorEntity("??", "이렇게 하면?");
    return wordRepository.findByMainScreenAndSubScreen(mainScreen, subScreen);
  }
}
