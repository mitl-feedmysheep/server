package feedmysheep.feedmysheepapi.domain.word.app.controller;

import feedmysheep.feedmysheepapi.domain.word.app.dto.WordDto;
import feedmysheep.feedmysheepapi.domain.word.app.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/word")
public class WordController {
  private final WordService wordService;

  @Autowired
  public WordController(WordService wordService) {
    this.wordService = wordService;
  }

  public WordDto.getWord getWordByMainAndSubScreen(@RequestParam() String mainScreen, @RequestParam() String subScreen) {
    wordService.getWordByMainAndSubScreen()
  }
}
