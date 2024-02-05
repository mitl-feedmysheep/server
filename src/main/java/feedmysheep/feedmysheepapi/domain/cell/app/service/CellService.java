package feedmysheep.feedmysheepapi.domain.cell.app.service;

import feedmysheep.feedmysheepapi.domain.auth.app.repository.AuthorizationRepository;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellMapper;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellReqDto;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellResDto;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellResDto.createCellGathering;
import feedmysheep.feedmysheepapi.domain.cell.app.repository.CellGatheringMemberPrayerRepository;
import feedmysheep.feedmysheepapi.domain.cell.app.repository.CellGatheringMemberRepository;
import feedmysheep.feedmysheepapi.domain.cell.app.repository.CellGatheringRepository;
import feedmysheep.feedmysheepapi.domain.cell.app.repository.CellMemberMapRepository;
import feedmysheep.feedmysheepapi.domain.cell.app.repository.CellRepository;
import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
import feedmysheep.feedmysheepapi.global.interceptor.auth.MemberAuth;
import feedmysheep.feedmysheepapi.global.utils.jwt.CustomUserDetails;
import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;
import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import feedmysheep.feedmysheepapi.models.CellEntity;
import feedmysheep.feedmysheepapi.models.CellGatheringEntity;
import feedmysheep.feedmysheepapi.models.CellGatheringMemberEntity;
import feedmysheep.feedmysheepapi.models.CellGatheringMemberPrayerEntity;
import feedmysheep.feedmysheepapi.models.CellMemberMapEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CellService {

  private final CellRepository cellRepository;
  private final CellGatheringRepository cellGatheringRepository;
  private final CellGatheringMemberRepository cellGatheringMemberRepository;
  private final CellGatheringMemberPrayerRepository cellGatheringMemberPrayerRepository;
  private final CellMemberMapRepository cellMemberMapRepository;
  private final MemberRepository memberRepository;
  private final AuthorizationRepository authorizationRepository;
  private final CellMapper cellMapper;
  private final CellProcessor cellProcessor;

  @Autowired
  public CellService(CellRepository cellRepository, CellGatheringRepository cellGatheringRepository,
      CellGatheringMemberRepository cellGatheringMemberRepository,
      CellGatheringMemberPrayerRepository cellGatheringMemberPrayerRepository,
      CellMemberMapRepository cellMemberMapRepository, MemberRepository memberRepository,
      AuthorizationRepository authorizationRepository, CellProcessor cellProcessor,
      CellMapper cellMapper) {
    this.cellRepository = cellRepository;
    this.cellGatheringRepository = cellGatheringRepository;
    this.cellGatheringMemberRepository = cellGatheringMemberRepository;
    this.cellGatheringMemberPrayerRepository = cellGatheringMemberPrayerRepository;
    this.cellMemberMapRepository = cellMemberMapRepository;
    this.memberRepository = memberRepository;
    this.authorizationRepository = authorizationRepository;
    this.cellMapper = cellMapper;
    this.cellProcessor = cellProcessor;
  }

  public List<CellResDto.getCellMemberByCellId> getCellMemberListByCellId(Long cellId,
      CustomUserDetails customUserDetails) {
    Long memberId = customUserDetails.getMemberId();

    // 1. 본인이 속한 셀인지 확인
    List<CellMemberMapEntity> cellMemberMapList = this.cellMemberMapRepository.getCellMemberMapListByCellId(
        cellId);
    List<Long> memberIdList = cellMemberMapList.stream().map(CellMemberMapEntity::getMemberId)
        .toList();
    boolean isCellMember = cellMemberMapList.stream()
        .anyMatch(cellMemberMapEntity -> cellMemberMapEntity.getMemberId().equals(memberId));
    // 1.1 본인이 속한 셀이 아닌데, 권한이 organLeader 이상인 경우에만 pass
    if (!isCellMember) {
      MemberEntity member = this.memberRepository.getMemberByMemberId(memberId)
          .orElseThrow(() -> new CustomException(ErrorMessage.MEMBER_NOT_FOUND));
      AuthorizationEntity authorization = this.authorizationRepository.getAuthorizationByAuthorizationId(
              member.getAuthorizationId())
          .orElseThrow(() -> new CustomException(ErrorMessage.NO_AUTHORIZATION));
      if (authorization.getLevel() < MemberAuth.ORGAN_LEADER.getValue()) {
        throw new CustomException(ErrorMessage.NOT_AUTHORIZED);
      }
    }

    // 2. 셀멤버맵으로 멤버리스트 조회
    List<MemberEntity> memberList = this.memberRepository.getMemberListByMemberIdList(memberIdList);

    // 3. 멤버에 isLeader 추가
    List<MemberEntity> memberListWithIsLeader = this.cellProcessor.addIsLeaderToMemberList(
        memberList, cellMemberMapList);

    // 4. 멤버에 isBirthdayThisMonth 추가
    List<MemberEntity> memberListWithIsBirthdayThisMonth = this.cellProcessor.addIsBirthdayThisMonthToMemberList(
        memberListWithIsLeader);

    // 4. 정렬 - 셀리더가 맨 앞으로 && 생일자가 그 뒤로 && 이름으로 정렬
    List<MemberEntity> sortedMemberList = this.cellProcessor.sortCellMemberList(
        memberListWithIsBirthdayThisMonth);

    return this.cellMapper.getCellMemberListByCellId(sortedMemberList);
  }

  public CellResDto.getGatheringsAndPrayersCount getGatheringsAndPrayersCountByCellId(Long cellId) {
    // 1. 셀 아이디로 셀모임 조회
    List<CellGatheringEntity> cellGatheringList = this.cellGatheringRepository.getCellGatheringListByCellId(
        cellId);
    List<Long> cellGatheringIdList = cellGatheringList.stream()
        .map(CellGatheringEntity::getCellGatheringId).toList();

    // 2. 셀모임으로 셀모임멤버 조회
    List<CellGatheringMemberEntity> cellGatheringMemberList = this.cellGatheringMemberRepository.getCellGatheringMemberListByCellGatheringIdList(
        cellGatheringIdList);
    List<Long> cellGatheringMemberIdList = cellGatheringMemberList.stream()
        .map(CellGatheringMemberEntity::getCellGatheringMemberId).toList();

    // 3. 셀모임멤버로 멤버당 셀모임멤버기도제목 조회
    List<CellGatheringMemberPrayerEntity> cellGatheringMemberPrayerList = this.cellGatheringMemberPrayerRepository.getCellGatheringMemberPrayerListByCellGatheringMemberIdList(
        cellGatheringMemberIdList);

    // 4. 셀모임개수와 기도제목개수
    int totalGatheringCount = cellGatheringList.size();
    int totalPrayerRequestCount = cellGatheringMemberPrayerList.size();

    return new CellResDto.getGatheringsAndPrayersCount(totalGatheringCount,
        totalPrayerRequestCount);
  }

  public List<CellResDto.getCellGathering> getCellGatheringListByCellId(Long cellId,
      CellReqDto.getCellGatheringListByCellId query) {
    int month = query.getMonth();

    // 1. 셀 아이디로 셀모임 전체 횟수 조회 (over-fetching)
    List<CellGatheringEntity> cellGatheringList = this.cellGatheringRepository.getCellGatheringListByCellId(
        cellId);

    // 2. N번째 모임 붙이기 & 요일 붙이기 & 내림차순 정렬
    List<CellGatheringEntity> cellGatheringListProcessed = this.cellProcessor.addNumberAndDayToCellGatheringList(
        cellGatheringList);

    // 3. month 여부에 따라 데이터 필터링
    List<CellGatheringEntity> cellGatheringListByMonth = this.cellProcessor.filterCellGatheringListByMonth(
        cellGatheringListProcessed, month);

    // 4 totalWorshipAttendanceCount & totalCellGatheringAttendanceCount 넣기
    List<CellGatheringEntity> cellGatheringListToReturn = cellGatheringListByMonth.stream()
        .map(cellGathering -> {
          // 4.1 셀모임 아이디로 셀모임멤버 조회
          List<CellGatheringMemberEntity> cellGatheringMemberList = this.cellGatheringMemberRepository.getCellGatheringMemberListByCellGatheringId(
              cellGathering.getCellGatheringId());
          // 4.2 셀모임멤버로 출석자수 조회 & 출석자수 넣기
          return this.cellProcessor.addAttendanceCountToCellGathering(cellGathering,
              cellGatheringMemberList);
        }).toList();

    return this.cellMapper.getCellGatheringListByCellId(cellGatheringListToReturn);
  }


  //소모임 생성
  //!!코드 작성 다 하고, 변수명 등 지켜야 할 조건들 확인하기(this붙이기 등)
  public createCellGathering createCellGathering(Long cellId,
      CellReqDto.createCellGathering body, CustomUserDetails customUserDetails) {

// 1-1. User가 보낸 정보 가져오기(body에 입력된 정보들 가져오기)
    LocalDateTime startedAt = body.getStartedAt();
    LocalDateTime endedAt = body.getEndedAt();
    LocalDate gatheringDate = body.getGatheringDate();
    String gatheringPlace = body.getGatheringPlace();
    String gatheringTitle = body.getGatheringTitle();

// 1-2. 소모임 생성) body에서 가져온 정보들을 가지고, cellGathering 생성하기 및 매핑.
    CellGatheringEntity buildCellGathering = CellGatheringEntity.builder().cellId(cellId)
        .startedAt(startedAt).endedAt(endedAt).gatheringDate(gatheringDate)
        .gatheringPlace(gatheringPlace).gatheringTitle(gatheringTitle)
        .build();
    this.cellGatheringRepository.save(buildCellGathering);

//초기화
    Long saveCellGathering = buildCellGathering.getCellGatheringId();

//  2. 소모임 멤버 구하기(cell-gathering-member)
//  2-1. 소모임 id(cell-gathering-id) 구하기
    CellEntity cellGatheringId = this.cellGatheringRepository.getCellGatheringIdByCellId(
        cellId);

// 2-2. 셀 멤버 아이디(cell-member-map-id) 구하기
    CellMemberMapEntity cellMemberMap = this.cellMemberMapRepository.getCellMemberMapByCellIdAndMemberId(
        cellId, customUserDetails.getMemberId());
    Long cellMemberMapId = cellMemberMap.getCellMemberMapId();

    // 2-2-1 cellIdList
    List<CellEntity> cellList = this.cellRepository.getCellByCellId(cellId).stream().toList();
    List<Long> cellIdList = cellList.stream().map(CellEntity::getCellId).toList();

    // 2-2-2. memberIdList
    Optional<MemberEntity> memberList = this.memberRepository.getMemberByMemberId(
        customUserDetails.getMemberId());
    List<Long> memberIdList = memberList.stream().map(MemberEntity::getMemberId).toList();

    // 2-3 cell-member-Map-Id-List
    List<CellMemberMapEntity> cellMemberMapIdList = this.cellMemberMapRepository.getCellMemberMapListByCellIdListAndMemberId(
        cellIdList,
        customUserDetails.getMemberId());

    //(2) cellGahteringIdLIst
    List<CellGatheringEntity> cellGatheringIdList = this.cellGatheringRepository.getCellGatheringListByCellId(
        cellId).stream().toList();

    //(2-1) cellGatheringMemberIdList
    List<CellGatheringMemberEntity> cellGatheringMemberList = this.cellGatheringMemberRepository.getCellGatheringMemberListByCellGatheringIdListAndCellMemberMapIdList(
        cellGatheringIdList, cellMemberMapIdList);

    if (cellGatheringMemberList.size() == cellMemberMapIdList.size()) {
      return this.cellMapper.getCellGatheringByCellId(buildCellGathering);
    }

    return null;
  }
};

