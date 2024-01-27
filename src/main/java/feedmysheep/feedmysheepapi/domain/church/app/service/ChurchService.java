package feedmysheep.feedmysheepapi.domain.church.app.service;

import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchMapper;
import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchReqDto;
import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchReqDto.getMemberEventsByBodyId;
import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchResDto;
import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchResDto.getBodyByChurchId;
import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchResDto.getMemberEventByMemberId;
import feedmysheep.feedmysheepapi.domain.church.app.repository.BodyMemberMapRepository;
import feedmysheep.feedmysheepapi.domain.church.app.repository.BodyRepository;
import feedmysheep.feedmysheepapi.domain.church.app.repository.ChurchRepository;
import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
import feedmysheep.feedmysheepapi.global.utils.jwt.CustomUserDetails;
import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;
import feedmysheep.feedmysheepapi.models.BodyEntity;
import feedmysheep.feedmysheepapi.models.BodyMemberMapEntity;
import feedmysheep.feedmysheepapi.models.ChurchEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ChurchService {

  private final ChurchRepository churchRepository;
  private final MemberRepository memberRepository;

  private final BodyRepository bodyRepository;
  private final ChurchMapper churchMapper;
  private BodyMemberMapRepository bodyMemberMapRepository;


  @Autowired
  public ChurchService(ChurchRepository churchRepository, MemberRepository memberRepository,
      BodyRepository bodyRepository, ChurchMapper churchMapper,
      BodyMemberMapRepository bodyMemberMapRepository) {
    this.churchRepository = churchRepository;
    this.memberRepository = memberRepository;
    this.bodyRepository = bodyRepository;
    this.churchMapper = churchMapper;
    this.bodyMemberMapRepository = bodyMemberMapRepository;
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

  public List<getMemberEventByMemberId> getMemberEventsByBodyId(
      CustomUserDetails customUserDetails, getMemberEventsByBodyId query,
      Long bodyId) {

    // 1. 유효한 멤버인지 검사
    this.memberRepository.getMemberByMemberId(customUserDetails.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorMessage.MEMBER_NOT_FOUND));

    //2. body(부서)에 해당하는 멤버들 bodyId 통해 가져오기
    List<BodyMemberMapEntity> memberListByBodyId = this.bodyMemberMapRepository.getMemberListByBodyId(
        bodyId);
    //bodyId를 넣어서 BodyMemberMap에 있는 정보들을 전부 가져옴
    System.out.println("memberListByBodyId = " + memberListByBodyId);

    //BodyMemberMap에 있는 모든 정보들 중에서, memberId에 대한 정보들만 가져와서 List형식으로 만듦.
    List<Long> memberIdListByBodyId = memberListByBodyId.stream()
        .map(BodyMemberMapEntity::getMemberId).toList();
    System.out.println("memberIdListByBodyId = " + memberIdListByBodyId);

    //3. 이벤트 월에 해당하는 날짜(2000-01-01)에서 월(month)값만 가져오기
    int targetMonthOfBirthday = query.getBirthday().getMonthValue();

    //4. 이벤트 멤버 들을 1페이지에 5명씩만 보여주도록 만들기 = OFFSET 0, LIMIT 5 (=LIMIT 0,5)
    Pageable pageable = PageRequest.of(0, 5);
    Page<MemberEntity> eventMemberPage = this.memberRepository.getMemberListByMemberIdListAndBirthday(
        memberIdListByBodyId, targetMonthOfBirthday, pageable);

    //5. 이벤트 멤버 리스트 필터링 // eventMemberPage에서 찾은 멤버들의 수 찾기
    long totalMemberEventCount = eventMemberPage.getTotalElements();
    System.out.println("totalMemberEventCount = " + totalMemberEventCount);

    // 6. 이벤트 멤버 조회 및 DTO 매핑
    List<MemberEntity> eventMember = eventMemberPage.getContent();
    System.out.println("eventMember = " + eventMember);

    return this.churchMapper.getMemberEventsByBodyId(eventMember);
  }
};
