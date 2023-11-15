package feedmysheep.feedmysheepapi.domain.church.app.controller;

import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchReqDto;
import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchResDto;
import feedmysheep.feedmysheepapi.domain.church.app.service.ChurchService;
import feedmysheep.feedmysheepapi.global.utils.jwt.CustomUserDetails;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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


  //getMapping아래줄에 있는 메소드 getChurchList는 그냥 메소드 이름일뿐이고,
  //그 아래줄의 CustomUserDetails는 형식에 맞도록 인증하는 것이고,

  @GetMapping
  public List<ChurchResDto.getChurchList> getChurchList(
      @AuthenticationPrincipal CustomUserDetails customUserDetails) {

    return this.churchService.getChurchList(customUserDetails);
  }

  @PostMapping(value = "/register", consumes = "application/json")
  public List<ChurchResDto.getChurchList> registerChurch(@RequestBody ChurchResDto.getChurchList body){
    return this.churchService.registerChurch(body);
  }
};


