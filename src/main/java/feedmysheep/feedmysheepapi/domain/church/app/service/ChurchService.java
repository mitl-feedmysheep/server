package feedmysheep.feedmysheepapi.domain.church.app.service;

import feedmysheep.feedmysheepapi.domain.body.app.repository.BodyRepository;
import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchResDto;
import feedmysheep.feedmysheepapi.domain.church.app.repository.ChurchRepository;
import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
import feedmysheep.feedmysheepapi.global.utils.jwt.CustomUserDetails;
import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;
import feedmysheep.feedmysheepapi.models.BodyEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChurchService {

  private final ChurchRepository churchRepository;
  private final MemberRepository memberRepository;

  private final BodyRepository bodyRepository;

  @Autowired
  public ChurchService(ChurchRepository churchRepository, MemberRepository memberRepository,
      BodyRepository bodyRepository) {
    this.churchRepository = churchRepository;
    this.memberRepository = memberRepository;
    this.bodyRepository = bodyRepository;
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
    return this.churchRepository.getChurchList();
  }

  public List<ChurchResDto.getBodyListByChurchId> getBodyListByChurchId(
      CustomUserDetails customUserDetails, Long churchId) {
    // 1. 유효한 멤버인지 검사
    Boolean isValidMember = this.memberRepository.existsMemberByMemberId(
        customUserDetails.getMemberId());
    if (!isValidMember) {
      throw new CustomException(ErrorMessage.NO_AUTHORIZATION);
    }

    // 2. 바디 리스트 반환
    List<BodyEntity> bodyList = this.bodyRepository.getBodyListByChurchId(churchId);

    // 3.
    // TODO List<BodyEntity> -> List<ChurchResDto.getBodyListByChurchId>로 어떻게 converting 할건지 알아보고 해보기
    return
  }
}
