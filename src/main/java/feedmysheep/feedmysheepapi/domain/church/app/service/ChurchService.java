package feedmysheep.feedmysheepapi.domain.church.app.service;

import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchMapper;
import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchReqDto;
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
  private final ChurchMapper churchMapper;

  @Autowired
  public ChurchService(ChurchRepository churchRepository, MemberRepository memberRepository,
      BodyRepository bodyRepository, ChurchMapper churchMapper) {
    this.churchRepository = churchRepository;
    this.memberRepository = memberRepository;
    this.bodyRepository = bodyRepository;
    this.churchMapper = churchMapper;
  }

  public List<ChurchResDto.getChurch> getChurchList(CustomUserDetails customUserDetails,
      String churchName) {
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
    return this.churchMapper.getChurchList(churchList);
  }

  public List<ChurchResDto.getBodyByChurchId> getBodyListByChurchId(
      CustomUserDetails customUserDetails, Long churchId) {
    // 1. 유효한 멤버인지 검사
    this.memberRepository.getMemberByMemberId(customUserDetails.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorMessage.MEMBER_NOT_FOUND));

    // 2. 바디 리스트 반환
    List<BodyEntity> bodyList = this.bodyRepository.getBodyListByChurchId(churchId);

    // 3. DTO 매핑
    return this.churchMapper.getBodyListByChurchId(bodyList);
  }

  public void register(ChurchReqDto.register body, CustomUserDetails customUserDetails) {
    ChurchEntity church = ChurchEntity.builder().churchName(body.getChurchName())
        .churchLocation(body.getChurchLocation()).churchLogoUrl(body.getChurchLogoUrl())
        .churchNumber(body.getChurchNumber()).churchDescription(body.getChurchDescription())
        .churchNumber(body.getChurchNumber()).build();
    church.setCreatedBy(customUserDetails.getMemberId());

    this.churchRepository.save(church);
  }


};