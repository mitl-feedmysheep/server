package feedmysheep.feedmysheepapi.domain.media.app.controller;

import feedmysheep.feedmysheepapi.domain.media.app.dto.MediaReqDto;
import feedmysheep.feedmysheepapi.domain.media.app.dto.MediaResDto;
import feedmysheep.feedmysheepapi.domain.media.app.service.MediaServiceImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app")
public class MediaController {

  private final MediaServiceImpl mediaService;

  @GetMapping("/medias")
  public List<MediaResDto.getMediaByScreenKey> getMediasByScreenKey(
      @Valid MediaReqDto.getMediasByScreenKey query) {
    return this.mediaService.getMediasByScreenKey(query);
  }
};

