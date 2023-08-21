package feedmysheep.feedmysheepapi.domain.member.app.service;

import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberReqDto;
import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberResDto;
import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberResDto.checkPhoneDuplication;
import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
  private final MemberRepository memberRepository;

  @Autowired
  public MemberService(MemberRepository memberRepository) { this.memberRepository = memberRepository; };

  public MemberResDto.checkPhoneDuplication checkPhoneDuplication(MemberReqDto.checkPhoneDuplication body) {
    String phone = body.getPhone();

    boolean isDuplicated = memberRepository.existsMemberByPhone(phone);

    return new MemberResDto.checkPhoneDuplication(isDuplicated);
  }
}
