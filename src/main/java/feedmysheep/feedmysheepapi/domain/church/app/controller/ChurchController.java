package feedmysheep.feedmysheepapi.domain.church.app.controller;

import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchResDto;
import feedmysheep.feedmysheepapi.domain.church.app.service.ChurchService;
import feedmysheep.feedmysheepapi.global.utils.jwt.CustomUserDetails;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/church")
public class ChurchController {

  private final ChurchService churchService;

  @Autowired
  public ChurchController(ChurchService churchService) {
    this.churchService = churchService;
  }

  ;

  @GetMapping
  public List<ChurchResDto.getChurchList> getChurchList(
      @AuthenticationPrincipal CustomUserDetails customUserDetails) {
    return this.churchService.getChurchList(customUserDetails);
  }

  @GetMapping("/{churchId}/body")
  public List<ChurchResDto.getBodyListByChurchId> getBodyListByChurchId(
      @AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long churchId) {
    return this.churchService.getBodyListByChurchId(customUserDetails, churchId);
  }
}
