package feedmysheep.feedmysheepapi.domain.church.app.service;

import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchMapper;
import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchReqDto;
import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchResDto;
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
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

  public void register(ChurchReqDto.register body) {
    ChurchEntity church = ChurchEntity.builder().churchName(body.getChurchName())
        .churchLocation(body.getChurchLocation()).churchLogoUrl(body.getChurchLogoUrl())
        .churchNumber(body.getChurchNumber()).churchDescription(body.getChurchDescription())
        .churchNumber(body.getChurchNumber()).build();

    this.churchRepository.save(church);
  }

  //bodyMemberMapRepository사용하기
//  1. 유효한 멤버인지 검사하고(member에서 검사-> memberid 가져올 수 있음)
//  2. churchId를 가져와서 bodyId를 찾아서 body_member_map_id를 생성하기
  public List<ChurchResDto.getMemberEventByMemberId> getMemberEventsByBodyId(
      CustomUserDetails customUserDetails, ChurchReqDto.getMemberEventsByBodyId query,
      Long bodyId) {

    // 1. 유효한 멤버인지 검사
    // memberId는 customUserDetails를 통하여, 필수값임
    this.memberRepository.getMemberByMemberId(customUserDetails.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorMessage.MEMBER_NOT_FOUND));

    System.out.println("111");

    //2. bodyId로 멤버 id들을 조회하기
    List<BodyMemberMapEntity> memberListByBodyId = this.bodyMemberMapRepository.getMemberListByBodyId(
        bodyId);

    System.out.println(memberListByBodyId);

    //3. Body(부서)id로 검색한 존재하는 memberId들을 List로 가져오기
    List<Long> memberIdList = memberListByBodyId.stream()
        .map(BodyMemberMapEntity::getMemberId)
        .toList();

    System.out.println("memberIdList = " + memberIdList);

    int birthday = query.getBirthday().getMonthValue();


    List<MemberEntity> EventMemberByMemberIdList = this.memberRepository.getMemberListByMemberIdList(
        memberIdList, birthday);

    System.out.println("EventMemberByMemberIdList = " + EventMemberByMemberIdList);


    // 3. DTO 매핑
    return this.churchMapper.getMemberEventsByBodyId(EventMemberByMemberIdList);
  }
}






