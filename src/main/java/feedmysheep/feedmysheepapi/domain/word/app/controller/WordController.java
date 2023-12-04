package feedmysheep.feedmysheepapi.domain.word.app.controller;

import feedmysheep.feedmysheepapi.domain.word.app.dto.WordReqDto;
import feedmysheep.feedmysheepapi.domain.word.app.dto.WordResDto;
import feedmysheep.feedmysheepapi.domain.word.app.service.WordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class WordController {
  private final WordService wordService;

  @Autowired
  public WordController(WordService wordService) {
    this.wordService = wordService;
  }

  @GetMapping("/words")
  public WordResDto.getWordByMainAndSubScreen getWordByMainAndSubScreen(@Valid WordReqDto.mainAndSubScreen queries) {
    return wordService.getWordByMainAndSubScreen(queries);
  }
}


