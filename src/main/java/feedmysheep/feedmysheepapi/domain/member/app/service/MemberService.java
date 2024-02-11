package feedmysheep.feedmysheepapi.domain.member.app.service;

import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberReqDto;
import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberResDto;
import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberResDto.getChurchWithBody;
import feedmysheep.feedmysheepapi.global.utils.jwt.CustomUserDetails;
import feedmysheep.feedmysheepapi.models.CellEntity;
import java.util.List;

public interface MemberService {

  void sendVerificationCode(MemberReqDto.sendVerificationCode query);

  void checkVerificationCode(MemberReqDto.checkVerificationCode query);

  void checkEmailDuplication(MemberReqDto.checkEmailDuplication query);

  MemberResDto.signUp signUp(MemberReqDto.signUp body);

  MemberResDto.signIn signIn(MemberReqDto.signIn body);

  MemberResDto.checkChurchMember checkChurchMember(CustomUserDetails customUserDetails);

  List<getChurchWithBody> getMemberChurchesWithBodies(CustomUserDetails customUserDetails);

  MemberResDto.getMemberInfo getMemberInfo(CustomUserDetails customUserDetails);

  List<MemberResDto.getCellByBodyIdAndMemberId> getCellListByBodyIdAndMemberId(
      CustomUserDetails customUserDetails, Long bodyId);

  void askToJoinChurchAndBody(Long churchId, Long bodyId, CustomUserDetails customUserDetails);

  MemberResDto.findMemberEmail findMemberEmail(MemberReqDto.findMemberEmail query);

  void requestTemporaryPassword(MemberReqDto.requestTemporaryPassword body);

  void changePassword(MemberReqDto.changePassword body);

  void deactivate(CustomUserDetails customUserDetails);
}
