package feedmysheep.feedmysheepapi.domain.text.app.controller;

import feedmysheep.feedmysheepapi.domain.text.app.dto.TextReqDto;
import feedmysheep.feedmysheepapi.domain.text.app.dto.TextResDto;
import feedmysheep.feedmysheepapi.domain.text.app.service.TextService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app")
public class TextController {

  private final TextService textService;

  @GetMapping("/text")
  public TextResDto.getTextByScreenKey getTextByScreenKey(
      @Valid TextReqDto.getTextByScreenKey query) {
    return textService.getTextByScreenKey(query);
  }
}


