package feedmysheep.feedmysheepapi.domain.member.app.controller;


import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberReqDto;
import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberResDto;
import feedmysheep.feedmysheepapi.domain.member.app.service.MemberService;
import feedmysheep.feedmysheepapi.global.utils.jwt.CustomUserDetails;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.QueryParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/app/member")
public class MemberController {

  private final MemberService memberService;

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
  public MemberResDto.checkChurchMember checkChurchMember(
      @AuthenticationPrincipal CustomUserDetails customUserDetails) {
    return this.memberService.checkChurchMember(customUserDetails);
  }

  @GetMapping("/churches-with-bodies")
  public List<MemberResDto.getChurchWithBody> getMemberChurchesWithBodies(
      @AuthenticationPrincipal CustomUserDetails customUserDetails) {
    return this.memberService.getMemberChurchesWithBodies(customUserDetails);
  }

  @GetMapping("/info")
  public MemberResDto.getMemberInfo getMemberInfo(
      @AuthenticationPrincipal CustomUserDetails customUserDetails) {
    return this.memberService.getMemberInfo(customUserDetails);
  }

  @GetMapping("/body/{bodyId}/cells")
  public List<MemberResDto.getCellByBodyIdAndMemberId> getCellListByBodyIdAndMemberId(
      @Valid @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @PathVariable UUID bodyId) {
    return this.memberService.getCellListByBodyIdAndMemberId(customUserDetails, bodyId);
  }

  /**
   * POLICY: 최초로 한번만 호출되어야 합니다. 한명의 유저가 여러개의 부서의 가입을 원할경우, 부서만 요청을 따로 해야합니다.
   */
  @PostMapping("/church/{churchId}/body/{bodyId}")
  public void askToJoinChurchAndBody(@PathVariable UUID churchId, @PathVariable UUID bodyId,
      @AuthenticationPrincipal CustomUserDetails customUserDetails) {
    this.memberService.askToJoinChurchAndBody(churchId, bodyId, customUserDetails);
  }

  @GetMapping("/find-email")
  public MemberResDto.findMemberEmail findMemberEmail(MemberReqDto.findMemberEmail query) {
    return this.memberService.findMemberEmail(query);
  }

  @PostMapping("/request-temporary-password")
  public void requestTemporaryPassword(
      @Valid @RequestBody MemberReqDto.requestTemporaryPassword body) {
    this.memberService.requestTemporaryPassword(body);
  }

  @PostMapping("/change-password")
  public void changePassword(@Valid @RequestBody MemberReqDto.changePassword body) {
    this.memberService.changePassword(body);
  }

  /**
   * 회원탈퇴
   */
  @DeleteMapping("/deactivate")
  public void deactivate(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
    this.memberService.deactivate(customUserDetails);
  }
}


