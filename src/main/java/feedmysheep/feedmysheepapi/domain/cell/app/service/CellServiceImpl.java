package feedmysheep.feedmysheepapi.domain.cell.app.service;

import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellMapper;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellReqDto;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellResDto;
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
import feedmysheep.feedmysheepapi.models.MemberEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CellServiceImpl implements CellService {

  private final CellRepository cellRepository;
  private final CellGatheringRepository cellGatheringRepository;
  private final CellGatheringMemberRepository cellGatheringMemberRepository;
  private final CellGatheringMemberPrayerRepository cellGatheringMemberPrayerRepository;
  private final CellMemberMapRepository cellMemberMapRepository;
  private final MemberRepository memberRepository;
  private final CellMapper cellMapper;
  private final CellProcessor cellProcessor;

  @Override
  public List<CellResDto.getCellMemberByCellId> getCellMemberListByCellId(UUID cellId) {

    // 1. 본인이 속한 셀인지 확인
    List<CellMemberMapEntity> cellMemberMapList = this.cellMemberMapRepository.findAllByCellIdAndCurDate(
        cellId);
    List<UUID> memberIdList = cellMemberMapList.stream().map(CellMemberMapEntity::getMemberId)
        .toList();

    // 2. 셀멤버맵으로 멤버리스트 조회
    List<MemberEntity> memberList = this.memberRepository.findAllByMemberIdList(memberIdList);

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

  @Override
  public CellResDto.getGatheringsAndPrayersCount getGatheringsAndPrayersCountByCellId(UUID cellId) {
    // 1. 셀 아이디로 셀모임 조회
    List<CellGatheringEntity> cellGatheringList = this.cellGatheringRepository.findAllByCellId(
        cellId);
    List<UUID> cellGatheringIdList = cellGatheringList.stream()
        .map(CellGatheringEntity::getCellGatheringId).toList();

    // 2. 셀모임으로 셀모임멤버 조회
    List<CellGatheringMemberEntity> cellGatheringMemberList = this.cellGatheringMemberRepository.findAllByCellGatheringIdList(
        cellGatheringIdList);
    List<UUID> cellGatheringMemberIdList = cellGatheringMemberList.stream()
        .map(CellGatheringMemberEntity::getCellGatheringMemberId).toList();

    // 3. 셀모임멤버로 멤버당 셀모임멤버기도제목 조회
    List<CellGatheringMemberPrayerEntity> cellGatheringMemberPrayerList = this.cellGatheringMemberPrayerRepository.findAllByCellGatheringMemberIdList(
        cellGatheringMemberIdList);

    // 4. 셀모임개수와 기도제목개수
    int totalGatheringCount = cellGatheringList.size();
    int totalPrayerRequestCount = cellGatheringMemberPrayerList.size();

    return new CellResDto.getGatheringsAndPrayersCount(totalGatheringCount,
        totalPrayerRequestCount);
  }

  @Override
  public List<CellResDto.getCellGathering> getCellGatheringListByCellId(UUID cellId,
      CellReqDto.getCellGatheringListByCellId query) {
    int month = query.getMonth();

    // 1. 셀 아이디로 셀모임 전체 횟수 조회 (over-fetching)
    List<CellGatheringEntity> cellGatheringList = this.cellGatheringRepository.findAllByCellId(
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
          List<CellGatheringMemberEntity> cellGatheringMemberList = this.cellGatheringMemberRepository.findAllByCellGatheringMemberId(
              cellGathering.getCellGatheringId());
          // 4.2 셀모임멤버로 출석자수 조회 & 출석자수 넣기
          return this.cellProcessor.addAttendanceCountToCellGathering(cellGathering,
              cellGatheringMemberList);
        }).toList();

    return this.cellMapper.getCellGatheringListByCellId(cellGatheringListToReturn);
  }

  @Override
  public CellResDto.getCellGatheringAndMemberListAndPrayerList getCellGatheringAndMemberListAndPrayerList(
      UUID cellGatheringId) {
    // 1. 셀모임 조회
    CellGatheringEntity cellGathering = this.cellGatheringRepository.findByCellGatheringId(
            cellGatheringId)
        .orElseThrow(() -> new CustomException(ErrorMessage.NO_CELL_GATHERING_FOUND));
    CellResDto.getCellGatheringAndMemberListAndPrayerList cellGatheringDto = this.cellMapper.setCellGathering(
        cellGathering);
    // 2. 셀모임멤버 조회
    List<CellGatheringMemberEntity> cellGatheringMemberList = this.cellGatheringMemberRepository.findAllByCellGatheringMemberId(
        cellGatheringId);
    // 3. 셀모임멤버당 리더여부 조회 및 셀모임멤버당 기도제목 조회 & 매퍼처리
    List<CellServiceDto.cellGatheringMember> cellGatheringMemberListDto = new ArrayList<>();
    cellGatheringMemberList.forEach(cellGatheringMember -> {
      CellServiceDto.cellGatheringMember cellGatheringMemberDto = this.cellMapper.setCellGatheringMember(
          cellGatheringMember);
      // 3-1. 셀모임멤버당 리더여부
      CellMemberMapEntity cellMemberMap = this.cellMemberMapRepository.findByCellMemberMapId(
              cellGatheringMember.getCellMemberMapId())
          .orElseThrow(() -> new CustomException(ErrorMessage.NOT_CELL_MEMBER));
      cellGatheringMemberDto.setLeader(cellMemberMap.isLeader());
      // 3-2. 셀모임멤버당 멤버정보
      MemberEntity member = this.memberRepository.findByMemberId(cellMemberMap.getMemberId())
          .orElseThrow(() -> new CustomException(ErrorMessage.MEMBER_NOT_FOUND));
      cellGatheringMemberDto.setMemberName(member.getMemberName());
      cellGatheringMemberDto.setBirthday(member.getBirthday());
      cellGatheringMemberDto.setProfileImageUrl(member.getProfileImageUrl());
      // 3-3. 셀모임멤버당 기도제목
      List<CellGatheringMemberPrayerEntity> cellGatheringMemberPrayerList = this.cellGatheringMemberPrayerRepository.findAllByCellGatheringMemberId(
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

  @Override
  public List<CellResDto.cellGatheringMemberPrayer> getCellGatheringMemberPrayerListByCellGatheringMemberId(
      UUID cellGatheringMemberId) {
    // 1. 셀모임 멤버별 기도제목 리스트 조회
    List<CellGatheringMemberPrayerEntity> cellGatheringMemberPrayerList = this.cellGatheringMemberPrayerRepository.findAllByCellGatheringMemberId(
        cellGatheringMemberId);

    // 2. 리턴
    return this.cellMapper.getCellGatheringMemberPrayerListByCellGatheringMemberId(
        cellGatheringMemberPrayerList);
  }

  @Override
  public void updateCellGatheringMemberByCellGatheringMemberId(UUID cellGatheringMemberId,
      CellReqDto.updateCellGatheringMemberByCellGatheringMemberId body) {
    // 1. Data-destructuring
    Boolean worshipAttendance = body.getWorshipAttendance();
    Boolean cellGatheringAttendance = body.getWorshipAttendance();
    String story = body.getStory();
    CellServiceDto.updateAttendancesAndStoryWhenExisting repoDto = new CellServiceDto.updateAttendancesAndStoryWhenExisting(
        cellGatheringMemberId, worshipAttendance, cellGatheringAttendance, story);

    // 2. 업데이트
    this.cellGatheringMemberRepository.updateByCellGatheringMemberId(repoDto);
  }

  @Override
  public void insertCellGatheringMemberPrayerListByCellGatheringMemberId(UUID cellGatheringMemberId,
      List<String> prayerRequestList) {
    List<CellGatheringMemberPrayerEntity> cellGatheringMemberPrayerList = new ArrayList<>();
    prayerRequestList.forEach(prayerRequest -> {
      CellGatheringMemberPrayerEntity cellGatheringMemberPrayer = CellGatheringMemberPrayerEntity.builder()
          .cellGatheringMemberId(cellGatheringMemberId).prayerRequest(prayerRequest).build();
      cellGatheringMemberPrayerList.add(cellGatheringMemberPrayer);
    });
    this.cellGatheringMemberPrayerRepository.saveAll(cellGatheringMemberPrayerList);
  }

  ;

  @Override
  public void updateCellGatheringMemberPrayerList(
      List<CellReqDto.updateCellGatheringMemberPrayer> cellGatheringMemberPrayerList,
      CustomUserDetails customUserDetails) {
    cellGatheringMemberPrayerList.forEach(cellGatheringMemberPrayer -> {
      CellServiceDto.updatePrayerById updateDto = new CellServiceDto.updatePrayerById(
          cellGatheringMemberPrayer.getCellGatheringMemberPrayerId(),
          cellGatheringMemberPrayer.getPrayerRequest());
      this.cellGatheringMemberPrayerRepository.updateByCellGatheringMemberPrayerId(updateDto);
    });
  }

  @Override
  public void deleteCellGatheringMemberPrayerList(CellReqDto.deleteCellGatheringMemberPrayer body,
      CustomUserDetails customUserDetails) {
    List<UUID> cellGatheringMemberPrayerIdList = body.getCellGatheringMemberPrayerIdList();
    cellGatheringMemberPrayerIdList.forEach(
        this.cellGatheringMemberPrayerRepository::deleteByCellGatheringMemberPrayerId);
  }

  @Override
  public CellResDto.getCellByCellId getCellByCellId(UUID cellId) {

    // 1. Repository에서 cell 검색
    CellEntity cell = this.cellRepository.findByCellId(cellId)
        .orElseThrow(() -> new CustomException(ErrorMessage.NO_CELL_FOUND));

    return this.cellMapper.getCellByCellId(cell);
  }

  @Override
  public void deleteCellGatheringByCellGatheringId(UUID cellGatheringId) {
    this.cellGatheringRepository.deleteByCellGatheringId(cellGatheringId);
  }

  @Override
  @Transactional
  public CellResDto.createCellGatheringByCellId createCellGatheringByCellId(UUID cellId,
      CellReqDto.createCellGatheringByCellId body, CustomUserDetails customUserDetails) {
    // 1. 셀모임 생성
    CellGatheringEntity cellGathering = CellGatheringEntity.builder().cellId(cellId)
        .gatheringTitle(body.getGatheringDate().toString() + " 모임")
        .gatheringDate(body.getGatheringDate()).startedAt(body.getStartedAt())
        .endedAt(body.getEndedAt()).gatheringPlace(body.getGatheringPlace())
        .description(body.getDescription()).build();
    CellGatheringEntity savedCellGathering = this.cellGatheringRepository.save(cellGathering);

    // 2. 셀모임 멤버 생성
    // 2-1. 셀멤버맵 조회
    List<CellMemberMapEntity> cellMemberMapList = this.cellMemberMapRepository.findAllByCellIdAndCurDate(
        cellId);
    List<UUID> cellMemberMapIdList = cellMemberMapList.stream()
        .map(CellMemberMapEntity::getCellMemberMapId).toList();
    // 2-2. 셀멤버맵으로 셀모임멤버 생성
    List<CellGatheringMemberEntity> cellGatheringMemberList = new ArrayList<>();
    cellMemberMapIdList.forEach(cellMemberMapId -> {
      CellGatheringMemberEntity cellGatheringMember = CellGatheringMemberEntity.builder()
          .cellGatheringId(savedCellGathering.getCellGatheringId()).cellMemberMapId(cellMemberMapId)
          .build();
      cellGatheringMemberList.add(cellGatheringMember);
    });
    // 2-3. 셀모임멤버 저장
    this.cellGatheringMemberRepository.saveAll(cellGatheringMemberList);

    // 3. 반환
    return new CellResDto.createCellGatheringByCellId(savedCellGathering.getCellGatheringId());
  }

  @Override
  @Transactional
  public void updateCellGatheringByCellGatheringId(UUID cellGatheringId,
      CellReqDto.updateCellGatheringByCellGatheringId body) {
    // 1. 셀모임 존재여부 확인
    CellGatheringEntity cellGathering = this.cellGatheringRepository.findByCellGatheringId(
            cellGatheringId)
        .orElseThrow(() -> new CustomException(ErrorMessage.NO_CELL_GATHERING_FOUND));

    // 2. 셀모임 업데이트
    Optional.ofNullable(body.getGatheringDate()).ifPresent(gatheringDate -> {
      cellGathering.setGatheringDate(gatheringDate);
      cellGathering.setGatheringTitle(gatheringDate.toString() + " 모임");
    });
    Optional.ofNullable(body.getStartedAt()).ifPresent(cellGathering::setStartedAt);
    Optional.ofNullable(body.getEndedAt()).ifPresent(cellGathering::setEndedAt);
    Optional.ofNullable(body.getGatheringPlace()).ifPresent(cellGathering::setGatheringPlace);
    Optional.ofNullable(body.getDescription()).ifPresent(cellGathering::setDescription);

    // 3. 셀모임 업데이트
    this.cellGatheringRepository.save(cellGathering);
  }
}