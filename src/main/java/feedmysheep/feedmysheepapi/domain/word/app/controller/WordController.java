package feedmysheep.feedmysheepapi.domain.word.app.controller;

import feedmysheep.feedmysheepapi.domain.word.app.dto.WordDto;
import feedmysheep.feedmysheepapi.domain.word.app.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping
  public WordDto.getWordByMainAndSubScreen getWordByMainAndSubScreen(
      @RequestParam(name = "mainScreen") String mainScreen,
      @RequestParam(name = "subScreen", required = false) String subScreen
  ) {
    return wordService.getWordByMainAndSubScreen(mainScreen, subScreen);
  }
}
