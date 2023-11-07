package feedmysheep.feedmysheepapi.domain.church.app.controller;

import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchResDto;
import feedmysheep.feedmysheepapi.domain.church.app.service.ChurchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/church")
public class ChurchController {
  private final ChurchService churchService;

  @Autowired
  public ChurchController(ChurchService churchService) { this.churchService = churchService; };

  @GetMapping
  public ChurchResDto.getChurchList getChurchList(Authentication authentication) {
    // TODO 다른 도메인을 호출해야되는데, 서비스를 호출할 것인가? repo를 호출할 것인가?
    // 멤버리포지토리를 호출해서 가입한 사람인지를 알아내야함.. 우짜지???
  }
}
