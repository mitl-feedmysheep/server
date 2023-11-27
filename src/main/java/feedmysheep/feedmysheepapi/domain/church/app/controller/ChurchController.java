package feedmysheep.feedmysheepapi.domain.church.app.controller;

import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchReqDto;
import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchResDto;
import feedmysheep.feedmysheepapi.domain.church.app.service.ChurchService;
import feedmysheep.feedmysheepapi.global.utils.jwt.CustomUserDetails;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/church")
public class ChurchController {

  private final ChurchService churchService;

  @Autowired
  public ChurchController(ChurchService churchService) {
    this.churchService = churchService;
  };

  @GetMapping
  public List<ChurchResDto.getChurch> getChurchList(
      @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @RequestParam(required = false) String churchName) {
    return this.churchService.getChurchList(customUserDetails, churchName);
  }

  @GetMapping("/{churchId}/body")
  public List<ChurchResDto.getBodyListByChurchId> getBodyListByChurchId(
      @AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long churchId) {
    return this.churchService.getBodyListByChurchId(customUserDetails, churchId);
  }

  @PostMapping("/register")
  public String registerChurch(@Valid @RequestBody ChurchReqDto.Church body){
    return this.churchService.registerChurch(body);
  }

}
