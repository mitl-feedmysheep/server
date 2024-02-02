package feedmysheep.feedmysheepapi.domain.cell.app.service;

import feedmysheep.feedmysheepapi.domain.auth.app.repository.AuthorizationRepository;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellMapper;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellReqDto;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellResDto;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellResDto.getCell;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellServiceDto;
import feedmysheep.feedmysheepapi.domain.cell.app.repository.CellGatheringMemberPrayerRepository;
import feedmysheep.feedmysheepapi.domain.cell.app.repository.CellGatheringMemberRepository;
import feedmysheep.feedmysheepapi.domain.cell.app.repository.CellGatheringRepository;
import feedmysheep.feedmysheepapi.domain.cell.app.repository.CellMemberMapRepository;
import feedmysheep.feedmysheepapi.domain.cell.app.repository.CellRepository;
import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
import feedmysheep.feedmysheepapi.global.utils.jwt.CustomUserDetails;
import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;
import feedmysheep.feedmysheepapi.models.CellEntity;
import feedmysheep.feedmysheepapi.models.CellGatheringEntity;
import feedmysheep.feedmysheepapi.models.CellGatheringMemberEntity;
import feedmysheep.feedmysheepapi.models.CellGatheringMemberPrayerEntity;
import feedmysheep.feedmysheepapi.models.CellMemberMapEntity;
import feedmysheep.feedmysheepapi.models.ChurchEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
    // TODO 이거 나중에 메서드화해서, 어디서든 불러서 쓸 수 있게 하기 (abusing 방지용)
//    boolean isCellMember = cellMemberMapList.stream()
//        .anyMatch(cellMemberMapEntity -> cellMemberMapEntity.getMemberId().equals(memberId));
//    // 1.1 본인이 속한 셀이 아닌데, 권한이 organLeader 이상인 경우에만 pass
//    if (!isCellMember) {
//      MemberEntity member = this.memberRepository.getMemberByMemberId(memberId)
//          .orElseThrow(() -> new CustomException(ErrorMessage.MEMBER_NOT_FOUND));
//      AuthorizationEntity authorization = this.authorizationRepository.getAuthorizationByAuthorizationId(
//              member.getAuthorizationId())
//          .orElseThrow(() -> new CustomException(ErrorMessage.NO_AUTHORIZATION));
//      if (authorization.getLevel() < MemberAuth.ORGAN_LEADER.getValue()) {
//        throw new CustomException(ErrorMessage.NOT_AUTHORIZED);
//      }
//    }

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

  public CellResDto.getCellGatheringAndMemberListAndPrayerList getCellGatheringAndMemberListAndPrayerList(
      Long cellGatheringId) {
    // 1. 셀모임 조회
    CellGatheringEntity cellGathering = this.cellGatheringRepository.getCellGatheringByCellGatheringId(
        cellGatheringId);
    CellResDto.getCellGatheringAndMemberListAndPrayerList cellGatheringDto = this.cellMapper.setCellGathering(
        cellGathering);
    // 2. 셀모임멤버 조회
    List<CellGatheringMemberEntity> cellGatheringMemberList = this.cellGatheringMemberRepository.getCellGatheringMemberListByCellGatheringId(
        cellGatheringId);
    // 3. 셀모임멤버당 리더여부 조회 및 셀모임멤버당 기도제목 조회 & 매퍼처리
    List<CellServiceDto.cellGatheringMember> cellGatheringMemberListDto = new ArrayList<>();
    cellGatheringMemberList.forEach(cellGatheringMember -> {
      CellServiceDto.cellGatheringMember cellGatheringMemberDto = this.cellMapper.setCellGatheringMember(
          cellGatheringMember);
      // 3-1. 셀모임멤버당 리더여부
      CellMemberMapEntity cellMemberMap = this.cellMemberMapRepository.getCellMemberMapByCellMemberMapId(
              cellGatheringMember.getCellMemberMapId())
          .orElseThrow(() -> new CustomException(ErrorMessage.NOT_CELL_MEMBER));
      cellGatheringMemberDto.setLeader(cellMemberMap.isLeader());
      // 3-2. 셀모임멤버당 멤버정보
      MemberEntity member = this.memberRepository.getMemberByMemberId(cellMemberMap.getMemberId())
          .orElseThrow(() -> new CustomException(ErrorMessage.MEMBER_NOT_FOUND));
      cellGatheringMemberDto.setMemberName(member.getMemberName());
      cellGatheringMemberDto.setBirthday(member.getBirthday());
      cellGatheringMemberDto.setProfileImageUrl(member.getProfileImageUrl());
      // 3-3. 셀모임멤버당 기도제목
      List<CellGatheringMemberPrayerEntity> cellGatheringMemberPrayerList = this.cellGatheringMemberPrayerRepository.getCellGatheringMemberPrayerListByCellGatheringMemberId(
          cellGatheringMember.getCellGatheringMemberId());
      List<CellServiceDto.cellGatheringMemberPrayer> cellGatheringMemberPrayerListDto = this.cellMapper.setCellGatheringMemberPrayerList(
          cellGatheringMemberPrayerList);
      cellGatheringMemberDto.setCellGatheringMemberPrayerList(cellGatheringMemberPrayerListDto);
      // 3-4. 셀모임멤버리스트에 add
      cellGatheringMemberListDto.add(cellGatheringMemberDto);
    });
    cellGatheringDto.setCellGatheringMemberList(cellGatheringMemberListDto);

    return cellGatheringDto;
  }

  public getCell getCellByCellId(Long cellId) {

// 1. Repository에서 cellId 검색
    CellEntity getCell = cellRepository.getCellId(cellId);
    if (getCell == null) {
      throw new CustomException(ErrorMessage.NO_CELL_FOUND);
    } else {

      return this.cellMapper.getCellByCellId(getCell);

    }
  }
};