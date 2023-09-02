package feedmysheep.feedmysheepapi.domain.member.app.controller;

import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberReqDto;
import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberResDto;
import feedmysheep.feedmysheepapi.domain.member.app.service.MemberService;
import feedmysheep.feedmysheepapi.domain.word.app.dto.WordReqDto;
import feedmysheep.feedmysheepapi.domain.word.app.dto.WordResDto;
import feedmysheep.feedmysheepapi.domain.word.app.service.WordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/app/member")
public class MemberController {
  private final MemberService memberService;

  @Autowired
  public MemberController(MemberService memberService) { this.memberService = memberService; };

  @GetMapping("/phone/send-verification-code")
  public void sendVerificationCode(@Valid MemberReqDto.sendVerificationCode query) {
    memberService.sendVerificationCode(query);
  }

  @GetMapping("/phone/check-verification-code")
  public void checkVerificationCode(@Valid MemberReqDto.checkVerificationCode query) {
    memberService.checkVerificationCode(query);
  }
}


