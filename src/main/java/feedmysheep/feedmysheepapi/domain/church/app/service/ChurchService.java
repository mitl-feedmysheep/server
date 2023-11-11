package feedmysheep.feedmysheepapi.domain.church.app.service;

import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchResDto;
import feedmysheep.feedmysheepapi.domain.church.app.repository.ChurchRepository;
import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
import feedmysheep.feedmysheepapi.global.utils.jwt.CustomUserDetails;
import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChurchService {

  private final ChurchRepository churchRepository;
  private final MemberRepository memberRepository;

  @Autowired
  public ChurchService(ChurchRepository churchRepository, MemberRepository memberRepository) {
    this.churchRepository = churchRepository;
    this.memberRepository = memberRepository;
  }

  public List<ChurchResDto.getChurchList> getChurchList(
      CustomUserDetails customUserDetails) {
    // 1. 유효한 멤버인지 검사
    Boolean isValidMember = this.memberRepository.existsMemberByMemberId(
        customUserDetails.getMemberId());

    if (!isValidMember) {
      throw new CustomException(ErrorMessage.NO_AUTHORIZATION);
    }

    // 2. 교회 리스트 반환
    return this.churchRepository.getAllValidChurchList();
  }
}
