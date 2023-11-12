package feedmysheep.feedmysheepapi.domain.member.app.controller;

import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchResDto;
import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchResDto.getChurchList;
import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberReqDto;
import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberResDto;
import feedmysheep.feedmysheepapi.domain.member.app.service.MemberService;
import feedmysheep.feedmysheepapi.domain.word.app.dto.WordReqDto;
import feedmysheep.feedmysheepapi.domain.word.app.dto.WordResDto;
import feedmysheep.feedmysheepapi.domain.word.app.service.WordService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/app/member")
public class MemberController {

  private final MemberService memberService;

  @Autowired
  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  ;

  @GetMapping("/phone/send-verification-code")
  public void sendVerificationCode(@Valid MemberReqDto.sendVerificationCode query) {
    this.memberService.sendVerificationCode(query);
  }

  @GetMapping("/phone/check-verification-code")
  public void checkVerificationCode(@Valid MemberReqDto.checkVerificationCode query) {
    this.memberService.checkVerificationCode(query);
  }

  @GetMapping("/email/check-duplication")
  public void checkEmailDuplication(@Valid MemberReqDto.checkEmailDuplication query) {
    this.memberService.checkEmailDuplication(query);
  }

  //교회 등록여부
  //작성하려면 필요하다고 생각되는 것들
  //1. 현재 교회들의 전체 리스트 2. 전체 리스트에서 이미 등록되었는지 여부 3. 없다면 없다고 알려주기
//**MemoryMemberRepository같은 저장소에서 해당 리스트들을 찾아야 되는데,
  // 어디서 찾을 수 있는지?

  @GetMapping("/check-church-member") {
    public MemberResDto.checkChurchMember checkChurchMember() {
      return this.memberService.checkRegisteredMember();
    }
  }

  @PostMapping("/sign-up")
  public MemberResDto.signUp signUp(@Valid @RequestBody MemberReqDto.signUp body) {
    return this.memberService.signUp(body);
  }


}


//  @GetMapping("/check-church-member")
//  public MemberResDto.checkChurchMember checkChurchMember () {
//    return this.memberService.checkChurchMember();
//  }
//
//}
