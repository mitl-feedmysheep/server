package feedmysheep.feedmysheepapi.domain.cell.app.service;

import feedmysheep.feedmysheepapi.domain.auth.app.repository.AuthorizationRepository;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellMapper;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellReqDto;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellResDto;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellResDto.createCell;
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
import java.util.List;
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
  public List<CellResDto.createCell> createCellGathering(Long cellId,
      CellReqDto.createCellGathering body) {

    //@질문 : cellId를 cellRepository에서 가져올 때, 그냥 아래에 "단일 셀 조회"처럼 찾으면 안되는 거 아닌가?
    // 왜냐면, 어떤 교회의 어떤 부서의 어떤 조직에 속하는 셀인지를 알아야 된다고 생각이 되는데?
    //개발 순서
    //** cell-gathering-member-id를 구하려면, cell-gathering-id랑 cell-member-map-id가 필요함
    //1) cell-gathering-repository에서 cellId를 넣어서 cell-gathering(셀모임)값들을 가져오기
    //1-1) 가져온 cell-gathering-id를 **에서 사용하기
    //2) cell-id 와 member-id를 가지고 cell-member-map-id를 구하기 -> **에서 사용하기
    //3) 1-1)와 2)에서 가져온 cell-gathering-id와 cell-member-map-id를 가지고 cell-gathering-member-id를 구하기
    //!!모임 생성 시, 해당 셀의 모든 멤버가 전부 추가가 되어야만, 조회가 가능하도록 설정하기
    //!!위와 같은 내용인데, 셀 모임 생성 시, 셀 모임 사람들도 같이 넣어준 후 값 가져오도록
    // -> 셀에 추가된 멤버의 숫자가, 셀의 전체 멤버의 수와 같을 때만 참이 되도록.
    //4)

    //단일 셀 조회
    List<CellEntity> cell = this.cellRepository.getCellByCellId(cellId);
    System.out.println("cell = " + cell);

    // 2-1 전체 셀 리스트 조회
    List<CellEntity> cellList = this.cellRepository.findAll();
    System.out.println("cellList = " + cellList);

    // 2-2 전체 셀 리스트에서 cellId값만 List로 만들기
    List<Long> cellIdList = cellList.stream().map(CellEntity::getCellId).toList();

    //cell_member_Map 통하여 MemberIdList구하기
    List<CellMemberMapEntity> cellMemberMapList = this.cellMemberMapRepository.getCellMemberMapListByCellId(
        cellId);
    System.out.println("cellMemberMapList = " + cellMemberMapList);

    Long memberIdList = cellMemberMapList.stream().map(CellMemberMapEntity::getMemberId);
    System.out.println("memberIdList = " + memberIdList);

    List<CellMemberMapEntity> cellMemberMapId = this.cellMemberMapRepository.getCellMemberMapListByCellIdListAndMemberId(
        cellIdList, memberIdList);
    System.out.println("cellMemberMapId = " + cellMemberMapId);

    return this.cellMapper.getCellListByCellId(cell);
  }
};

