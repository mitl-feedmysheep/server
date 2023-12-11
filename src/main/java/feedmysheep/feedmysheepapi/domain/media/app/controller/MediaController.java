package feedmysheep.feedmysheepapi.domain.media.app.controller;

import feedmysheep.feedmysheepapi.domain.media.app.dto.MediaReqDto;
import feedmysheep.feedmysheepapi.domain.media.app.dto.MediaResDto;
import feedmysheep.feedmysheepapi.domain.media.app.service.MediaService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/medias")
public class MediaController {

  private final MediaService mediaService;

  @Autowired
  public MediaController(MediaService mediaService) {
    this.mediaService = mediaService;
  }

  @GetMapping
  public List<MediaResDto.getMediaListByScreenKey> getMediasByScreenKey(
      @Valid MediaReqDto.getMediasByScreenKey query) {
    return this.mediaService.getMediasByScreenKey(query);
  }
};

