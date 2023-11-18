package feedmysheep.feedmysheepapi.domain.church.app.service;

import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchResDto;
import feedmysheep.feedmysheepapi.domain.church.app.repository.BodyRepository;
import feedmysheep.feedmysheepapi.domain.church.app.repository.ChurchRepository;
import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
import feedmysheep.feedmysheepapi.global.utils.jwt.CustomUserDetails;
import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;
import feedmysheep.feedmysheepapi.models.BodyEntity;
import feedmysheep.feedmysheepapi.models.ChurchEntity;
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

  public List<ChurchResDto.getChurch> getChurchList(
      CustomUserDetails customUserDetails, String churchName) {
    // 1. 유효한 멤버인지 검사
    this.memberRepository.getMemberByMemberId(customUserDetails.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorMessage.MEMBER_NOT_FOUND));

    // 2. 교회 리스트
    List<ChurchEntity> churchList;
    if (churchName != null && !churchName.isEmpty()) {
      churchList = this.churchRepository.getChurchListByChurchName(churchName);
    } else {
      churchList = this.churchRepository.getChurchList();
    }

    // 3. 반환
    return churchList.stream().map(church -> {
      ChurchResDto.getChurch churchDto = new ChurchResDto.getChurch();
      churchDto.setChurchId(church.getChurchId());
      churchDto.setChurchName(church.getChurchName());
      churchDto.setChurchLocation(church.getChurchLocation());

      return churchDto;
    }).toList();
//
  }

  public List<ChurchResDto.getBodyListByChurchId> getBodyListByChurchId(
      CustomUserDetails customUserDetails, Long churchId) {
    // 1. 유효한 멤버인지 검사
    this.memberRepository.getMemberByMemberId(customUserDetails.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorMessage.MEMBER_NOT_FOUND));

    // 2. 바디 리스트 반환
    List<BodyEntity> bodyList = this.bodyRepository.getBodyListByChurchId(churchId);

    // 3. DTO 매핑
    return bodyList.stream().map(body -> {
      ChurchResDto.getBodyListByChurchId bodyDto = new ChurchResDto.getBodyListByChurchId();
      bodyDto.setBodyId(body.getBodyId());
      bodyDto.setBodyName(body.getBodyName());

      return bodyDto;
    }).toList();
  }
}
