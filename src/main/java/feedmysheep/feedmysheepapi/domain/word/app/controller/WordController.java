package feedmysheep.feedmysheepapi.domain.word.app.controller;

import feedmysheep.feedmysheepapi.domain.word.app.service.WordService;
import feedmysheep.feedmysheepapi.models.Word;
import java.util.List;
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
  public List<Word> getWordList() {
    return wordService.getWordList();
  }

//  @GetMapping
//  public Word getWordByMainAndSubScreen(
//      @RequestParam(name = "mainScreen") String mainScreen,
//      @RequestParam(name = "subScreen") String subScreen
//  ) {
//    return wordService.getWordByMainAndSubScreen(mainScreen, subScreen);
//  }


}
