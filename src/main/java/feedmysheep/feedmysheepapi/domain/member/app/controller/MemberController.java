package feedmysheep.feedmysheepapi.domain.member.app.controller;

import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberReqDto;
import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberResDto;
import feedmysheep.feedmysheepapi.domain.member.app.service.MemberService;
import feedmysheep.feedmysheepapi.global.utils.jwt.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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

  @PostMapping("/sign-up")
  public MemberResDto.signUp signUp(@Valid @RequestBody MemberReqDto.signUp body) {
    return this.memberService.signUp(body);
  }

  @PostMapping("/sign-in")
  public MemberResDto.signIn signIn(@Valid @RequestBody MemberReqDto.signIn body) {
    return this.memberService.signIn(body);
  }

  @GetMapping("/check-church-member")
  public MemberResDto.checkChurchMember checkChurchMember(@AuthenticationPrincipal
  CustomUserDetails customUserDetails) {
    return this.memberService.checkChurchMember(customUserDetails);
  }
}


