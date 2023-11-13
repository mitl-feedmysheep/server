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

  //Restful API의 GET(GET=가져오는 것 / path -> /app/church 로 실행이 되었을 때,
  //getChurchList라는 메소드가 실행이 되고, customUserDeatils라는 파라미터를 받아와서 실행됨.
  //customUserDetails는 UserDetails(userPassword랑 userName을 사용 가능)라는
  // interface를 implements받고 있음을 참고하면 되고, CustomUserDetail(JwtDto.memberInfo  memberInfo)를 통해서,
  //memberId, level, memberName의 형식에 해당하는 타입만 받아옴

  //getMapping아래줄에 있는 메소드 getChurchList는 그냥 메소드 이름일뿐이고,
  //그 아래줄의 CustomUserDetails는 형식에 맞도록 인증하는 것이고,
  //

  //질문? getChurchList의 파라미터인 customUserDetail가 실행이 되는거면,
  // CustomUserDetails.java에 작성되어있는 CustomUserDeatils안의 (JwtDto.memberInf memberInfo)에 대한
  //내용들은 따로 작성을 안해주는건가? @Authen...다음에 CustomUserDetails(JwtDto.memberInfo...)이런식으로.
  @GetMapping
  public List<ChurchResDto.getChurchList> getChurchList(
      @AuthenticationPrincipal CustomUserDetails customUserDetails) {

    return this.churchService.getChurchList(customUserDetails);
  }

  @PostMapping("/register")
    public List<ChurchResDto.getChurchList> registerChurch(@RequestBody ChurchResDto.getChurchList body){
      return this.churchService.registerChurch(body);
    }
  };



