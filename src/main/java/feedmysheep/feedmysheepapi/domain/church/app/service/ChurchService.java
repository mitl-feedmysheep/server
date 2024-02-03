package feedmysheep.feedmysheepapi.domain.church.app.service;

import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchMapper;
import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchReqDto;
import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchReqDto.getMemberEventsByBodyId;
import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchResDto;
import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchServiceDto;
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
import java.lang.reflect.Member;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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

  public ChurchResDto.getMemberEventListByMemberId getMemberEventsByBodyId(
      getMemberEventsByBodyId query, Long bodyId) {
    // 1. Data-destructuring
    Integer month = query.getMonth();
    int page = query.getPage();
    int limit = query.getLimit();
    int offset = page - 1;

    // 2. body(부서)에 해당하는 멤버들 bodyId 통해 가져오기
    List<BodyMemberMapEntity> bodyMemberListByBodyId = this.bodyMemberMapRepository.getBodyMemberListByBodyId(
        bodyId);

    // 3. BodyMemberMap에 있는 모든 정보들 중에서, memberId에 대한 정보들만 가져와서 List형식으로 만듦.
    List<Long> memberIdListByBodyId = bodyMemberListByBodyId.stream()
        .map(BodyMemberMapEntity::getMemberId).toList();

    // 4. memberRepository에 설정된 값과 매칭하기
    Pageable pageRequest = PageRequest.of(offset, limit);
    Page<MemberEntity> eventMemberPage = this.memberRepository.getMemberListByMemberIdListAndMonth(
        memberIdListByBodyId, month, pageRequest);

    // 5. eventMemberPage에서 찾은 멤버들의 수 찾기 & 이벤트 멤버 조회 및 DTO 매핑
    int totalMemberEventCount = Long.valueOf(eventMemberPage.getTotalElements()).intValue();
    List<MemberEntity> memberList = eventMemberPage.getContent();
    List<ChurchServiceDto.memberEventList> eventMemberList = this.churchMapper.setMemberEventList(
        memberList);

    //6. 멤버 리스트의 이벤트 이름(setEventName)과 한 주의 일(setDayOfWeek) 설정
    int currentYear = LocalDate.now().getYear();
    eventMemberList.forEach((member) -> {
      // 태어난 연도의 요일 -> 현재 연도의 요일
      member.setBirthday(member.getBirthday().withYear(currentYear));
      member.setDayOfWeek(
          member.getBirthday().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));
    });

    return this.churchMapper.getMemberEventsByBodyId(totalMemberEventCount, eventMemberList);
  }
};

