package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.domain.TestUtil;
import feedmysheep.feedmysheepapi.domain.auth.app.repository.AuthorizationRepository;
import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
import feedmysheep.feedmysheepapi.global.config.TestConfig;
import feedmysheep.feedmysheepapi.models.CellMemberMapEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CellMemberMapRepositoryTest {

  @Autowired
  private CellMemberMapRepository cellMemberMapRepository;

  static Long memberId = TestUtil.getRandomLong();
  static List<Long> cellIdList = new ArrayList<>();

  @BeforeAll
  public static void setup(@Autowired MemberRepository memberRepository,
      @Autowired CellMemberMapRepository cellMemberMapRepository) {
    // 셀 추가
    cellIdList.add(TestUtil.getRandomLong());
    cellIdList.add(TestUtil.getRandomLong());
    cellIdList.add(TestUtil.getRandomLong());
    cellIdList.add(TestUtil.getRandomLong());
    // 셀멤버맵 생성
    cellMemberMapRepository.save(
        DataFactory.createCellMemberMapByCellIdAndMemberId(cellIdList.get(0), memberId));
    cellMemberMapRepository.save(
        DataFactory.createCellMemberMapByCellIdAndMemberId(cellIdList.get(1), memberId));
    CellMemberMapEntity cellMemberMapTest3 = DataFactory.createCellMemberMapByCellIdAndMemberId(
        cellIdList.get(2), memberId);
    cellMemberMapTest3.setStartDate(LocalDate.parse("2020-01-01"));
    cellMemberMapTest3.setEndDate(LocalDate.parse("2020-12-31"));
    cellMemberMapRepository.save(cellMemberMapTest3);
    CellMemberMapEntity cellMemberMapTest4 = DataFactory.createCellMemberMapByCellIdAndMemberId(
        cellIdList.get(3), memberId);
    cellMemberMapTest4.setValid(false);
    cellMemberMapRepository.save(cellMemberMapTest4);
  }

  @AfterAll
  public static void cleanup(@Autowired AuthorizationRepository authorizationRepository,
      @Autowired MemberRepository memberRepository,
      @Autowired CellMemberMapRepository cellMemberMapRepository) {
    authorizationRepository.deleteAll();
    memberRepository.deleteAll();
    cellMemberMapRepository.deleteAll();
  }

  @Test
  @DisplayName("셀아이디리스트 + 멤버아이디 -> 유효한 셀멤버맴만 조회")
  void test1() {
    // given

    // when
    List<CellMemberMapEntity> cellMemberMapList = this.cellMemberMapRepository.getCellMemberMapListByCellIdListAndMemberId(
        cellIdList, memberId);

    // then
    assertThat(cellMemberMapList.size()).isEqualTo(2);
    assertThat(cellMemberMapList.get(0).getCellId()).isEqualTo(cellIdList.get(0));
    assertThat(cellMemberMapList.get(1).getCellId()).isEqualTo(cellIdList.get(1));
  }

  @Test
  @DisplayName("셀아이디 -> 유효한 셀멤버맵만 조회")
  void test2() {
    // given

    // when
    List<CellMemberMapEntity> cellMemberMapList = this.cellMemberMapRepository.getCellMemberMapListByCellId(
        cellIdList.get(0));

    // then
    assertThat(cellMemberMapList.size()).isEqualTo(1);
    assertThat(cellMemberMapList.get(0).getCellId()).isEqualTo(cellIdList.get(0));
  }

  @Test
  @DisplayName("셀아이디 -> 날짜에 유효한 셀멤버맵 없음")
  void test3() {
    // given

    // when
    List<CellMemberMapEntity> cellMemberMapList = this.cellMemberMapRepository.getCellMemberMapListByCellId(
        cellIdList.get(2));

    // then
    assertThat(cellMemberMapList.size()).isEqualTo(0);
  }

  @Test
  @DisplayName("셀아이디 -> 유효한 셀멤버맵 없음")
  void test4() {
    // given

    // when
    List<CellMemberMapEntity> cellMemberMapList = this.cellMemberMapRepository.getCellMemberMapListByCellId(
        cellIdList.get(3));

    // then
    assertThat(cellMemberMapList.size()).isEqualTo(0);
  }
}