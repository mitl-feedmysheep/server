package feedmysheep.feedmysheepapi.domain.word.app.service;

import feedmysheep.feedmysheepapi.domain.word.app.repository.WordRepository;
import feedmysheep.feedmysheepapi.models.Word;
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

  public Word getWordByMainAndSubScreen(String mainScreen, String subScreen) {
    return wordRepository.findBy();
  }

}
