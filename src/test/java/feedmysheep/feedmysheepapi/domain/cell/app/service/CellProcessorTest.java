package feedmysheep.feedmysheepapi.domain.cell.app.service;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.models.CellGatheringEntity;
import feedmysheep.feedmysheepapi.models.CellGatheringMemberEntity;
import feedmysheep.feedmysheepapi.models.CellMemberMapEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CellProcessorTest {

  private final CellProcessor cellProcessor = new CellProcessor();

  @BeforeAll
  public static void setUp() {
  }

  @AfterAll
  public static void tearDown() {
  }

  @Test
  @DisplayName("addIsLeaderToMemberList 테스트")
  void test1() {
    // given
    MemberEntity member1 = DataFactory.createMember(UUID.randomUUID());
    MemberEntity member2 = DataFactory.createMember(UUID.randomUUID());
    MemberEntity member3 = DataFactory.createMember(UUID.randomUUID());
    List<MemberEntity> cellMemberList = List.of(member1, member2, member3);
    CellMemberMapEntity cellMemberMap1 = DataFactory.createCellMemberMapByCellIdAndMemberId(
        UUID.randomUUID(), member1.getMemberId());
    cellMemberMap1.setLeader(true);
    CellMemberMapEntity cellMemberMap2 = DataFactory.createCellMemberMapByCellIdAndMemberId(
        UUID.randomUUID(), member2.getMemberId());
    cellMemberMap2.setLeader(true);
    CellMemberMapEntity cellMemberMap3 = DataFactory.createCellMemberMapByCellIdAndMemberId(
        UUID.randomUUID(), member3.getMemberId());
    cellMemberMap3.setLeader(false);
    List<CellMemberMapEntity> cellMemberMapList = List.of(cellMemberMap1, cellMemberMap2,
        cellMemberMap3);

    // when
    List<MemberEntity> memberList = this.cellProcessor.addIsLeaderToMemberList(cellMemberList,
        cellMemberMapList);

    // then
    assertThat(memberList.get(0).isLeader()).isEqualTo(true);
    assertThat(memberList.get(1).isLeader()).isEqualTo(true);
    assertThat(memberList.get(2).isLeader()).isEqualTo(false);
  }

  @Test
  @DisplayName("addIsBirthdayThisMonthToMemberList 테스트")
  void test2() {
    // given
    int currentMonth = LocalDate.now().getMonthValue();
    MemberEntity member1 = DataFactory.createMember(UUID.randomUUID());
    member1.setBirthday(LocalDate.of(1990, currentMonth, 1));
    MemberEntity member2 = DataFactory.createMember(UUID.randomUUID());
    member2.setBirthday(LocalDate.of(1990, currentMonth, 1));
    MemberEntity member3 = DataFactory.createMember(UUID.randomUUID());
    member3.setBirthday(LocalDate.of(1990, currentMonth + 1, 1));
    List<MemberEntity> cellMemberList = List.of(member1, member2, member3);

    // when
    List<MemberEntity> memberList = this.cellProcessor.addIsBirthdayThisMonthToMemberList(
        cellMemberList);

    // then
    assertThat(memberList.get(0).isBirthdayThisMonth()).isEqualTo(true);
    assertThat(memberList.get(1).isBirthdayThisMonth()).isEqualTo(true);
    assertThat(memberList.get(2).isBirthdayThisMonth()).isEqualTo(false);
  }

  @Test
  @DisplayName("sortCellMemberList 테스트")
  void test3() {
    // given
    int currentMonth = LocalDate.now().getMonthValue();
    MemberEntity member1 = DataFactory.createMember(UUID.randomUUID());
    member1.setBirthday(LocalDate.of(1990, currentMonth, 1));
    member1.setMemberName("김창수");
    MemberEntity member2 = DataFactory.createMember(UUID.randomUUID());
    member2.setBirthday(LocalDate.of(1990, currentMonth + 2, 1));
    member2.setMemberName("김장수");
    MemberEntity member3 = DataFactory.createMember(UUID.randomUUID());
    member3.setBirthday(LocalDate.of(1990, currentMonth + 1, 1));
    member3.setLeader(true);
    List<MemberEntity> cellMemberList = List.of(member1, member2, member3);

    // when
    List<MemberEntity> memberList = this.cellProcessor.sortCellMemberList(cellMemberList);

    // then
    assertThat(memberList.get(0).getMemberId()).isEqualTo(member3.getMemberId());
    assertThat(memberList.get(1).getMemberId()).isEqualTo(member2.getMemberId());
    assertThat(memberList.get(2).getMemberId()).isEqualTo(member1.getMemberId());
  }

  @Test
  @DisplayName("addNumberAndDayToCellGatheringList 테스트")
  void test4() {
    // given
    CellGatheringEntity cellGathering1 = DataFactory.createCellGathering(UUID.randomUUID());
    cellGathering1.setGatheringDate(LocalDate.of(2021, 1, 13)); // 수
    CellGatheringEntity cellGathering2 = DataFactory.createCellGathering(UUID.randomUUID());
    cellGathering2.setGatheringDate(LocalDate.of(2021, 1, 1)); // 금
    CellGatheringEntity cellGathering3 = DataFactory.createCellGathering(UUID.randomUUID());
    cellGathering3.setGatheringDate(LocalDate.of(2020, 12, 31)); // 목
    List<CellGatheringEntity> cellGatheringList = List.of(cellGathering1, cellGathering2,
        cellGathering3);

    // when
    List<CellGatheringEntity> gatheringList = this.cellProcessor.addNumberAndDayToCellGatheringList(
        cellGatheringList);

    // then
    assertThat(gatheringList.get(0).getCellGatheringId()).isEqualTo(
        cellGathering1.getCellGatheringId());
    assertThat(gatheringList.get(0).getNumberOfGathering()).isEqualTo(1);
    assertThat(gatheringList.get(0).getDayOfWeek()).isEqualTo("수");
    assertThat(gatheringList.get(1).getCellGatheringId()).isEqualTo(
        cellGathering2.getCellGatheringId());
    assertThat(gatheringList.get(1).getNumberOfGathering()).isEqualTo(2);
    assertThat(gatheringList.get(1).getDayOfWeek()).isEqualTo("금");
    assertThat(gatheringList.get(2).getCellGatheringId()).isEqualTo(
        cellGathering3.getCellGatheringId());
    assertThat(gatheringList.get(2).getNumberOfGathering()).isEqualTo(3);
    assertThat(gatheringList.get(2).getDayOfWeek()).isEqualTo("목");
  }

  @Test
  @DisplayName("filterCellGatheringListByMonth 테스트")
  void test5() {
    // given
    CellGatheringEntity cellGathering1 = DataFactory.createCellGathering(UUID.randomUUID());
    cellGathering1.setGatheringDate(LocalDate.of(2021, 5, 13)); // 수
    CellGatheringEntity cellGathering2 = DataFactory.createCellGathering(UUID.randomUUID());
    cellGathering2.setGatheringDate(LocalDate.of(2021, 5, 1)); // 금
    CellGatheringEntity cellGathering3 = DataFactory.createCellGathering(UUID.randomUUID());
    cellGathering3.setGatheringDate(LocalDate.of(2020, 12, 31)); // 목
    List<CellGatheringEntity> cellGatheringList = List.of(cellGathering1, cellGathering2,
        cellGathering3);

    // when
    List<CellGatheringEntity> gatheringList = this.cellProcessor.filterCellGatheringListByMonth(
        cellGatheringList, 5);

    // then
    assertThat(gatheringList.size()).isEqualTo(2);
  }

  @Test
  @DisplayName("addAttendanceCountToCellGathering 테스트")
  void test6() {
    // given
    CellGatheringEntity cellGathering = DataFactory.createCellGathering(UUID.randomUUID());
    CellGatheringMemberEntity cellGatheringMember1 = DataFactory.createCellGatheringMember(
        UUID.randomUUID(), UUID.randomUUID());
    cellGatheringMember1.setWorshipAttendance(true);
    cellGatheringMember1.setCellGatheringAttendance(true);
    CellGatheringMemberEntity cellGatheringMember2 = DataFactory.createCellGatheringMember(
        UUID.randomUUID(), UUID.randomUUID());
    cellGatheringMember2.setWorshipAttendance(true);
    cellGatheringMember2.setCellGatheringAttendance(false);
    CellGatheringMemberEntity cellGatheringMember3 = DataFactory.createCellGatheringMember(
        UUID.randomUUID(), UUID.randomUUID());
    cellGatheringMember3.setWorshipAttendance(false);
    cellGatheringMember3.setCellGatheringAttendance(true);
    List<CellGatheringMemberEntity> cellGatheringMemberList = List.of(cellGatheringMember1,
        cellGatheringMember2, cellGatheringMember3);

    // when
    CellGatheringEntity gathering = this.cellProcessor.addAttendanceCountToCellGathering(
        cellGathering, cellGatheringMemberList);

    // then
    assertThat(gathering.getTotalWorshipAttendanceCount()).isEqualTo(2);
    assertThat(gathering.getTotalCellGatheringAttendanceCount()).isEqualTo(2);
  }
}