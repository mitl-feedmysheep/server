package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.domain.TestUtil;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellServiceDto;
import feedmysheep.feedmysheepapi.global.config.TestQueryDslConfig;
import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.models.CellGatheringMemberEntity;
import java.util.List;
import java.util.UUID;
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
@Import(TestQueryDslConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CellGatheringMemberRepositoryTest {

  @Autowired
  private CellGatheringMemberRepository cellGatheringMemberRepository;

  // 모임
  static UUID cellGatheringId1 = TestUtil.getRandomUUID();
  static UUID cellGatheringId2 = TestUtil.getRandomUUID();
  static UUID cellMemberMapId3 = TestUtil.getRandomUUID();

  // 멤버
  static UUID cellMemberMapId1 = TestUtil.getRandomUUID();
  static UUID cellMemberMapId2 = TestUtil.getRandomUUID();
  static UUID cellGatheringId3 = TestUtil.getRandomUUID();

  static CellGatheringMemberEntity cellGatheringMember1;

  @BeforeAll
  public static void setUp(@Autowired CellGatheringMemberRepository cellGatheringMemberRepository) {
    cellGatheringMember1 = cellGatheringMemberRepository.save(
        DataFactory.createCellGatheringMember(cellGatheringId1, cellMemberMapId1));
    cellGatheringMemberRepository.save(
        DataFactory.createCellGatheringMember(cellGatheringId1, cellMemberMapId2));
    cellGatheringMemberRepository.save(
        DataFactory.createCellGatheringMember(cellGatheringId2, cellMemberMapId1));
    cellGatheringMemberRepository.save(
        DataFactory.createCellGatheringMember(cellGatheringId2, cellMemberMapId2));
    cellGatheringMemberRepository.save(
        DataFactory.createCellGatheringMember(cellGatheringId2, cellMemberMapId3));
    cellGatheringMemberRepository.save(
        DataFactory.createCellGatheringMember(cellGatheringId3, cellMemberMapId3));
  }

  @AfterAll
  public static void tearDown(
      @Autowired CellGatheringMemberRepository cellGatheringMemberRepository) {
    cellGatheringMemberRepository.deleteAll();
  }

  @Test
  @DisplayName("셀모임아이디리스트로 셀모임멤버리스트 조회 --> 성공")
  void test1() {
    // given
    List<UUID> cellGatheringIdList = List.of(cellGatheringId1, cellGatheringId2);

    // when
    List<CellGatheringMemberEntity> cellGatheringMemberList = this.cellGatheringMemberRepository.findAllByCellGatheringIdList(
        cellGatheringIdList);

    // then
    assertThat(cellGatheringMemberList.size()).isEqualTo(5);
    assertThat(cellGatheringMemberList.stream().filter(
            cellGatheringMember -> cellGatheringMember.getCellGatheringId().equals(cellGatheringId1))
        .toList().size()).isEqualTo(2);
    assertThat(cellGatheringMemberList.stream().filter(
            cellGatheringMember -> cellGatheringMember.getCellGatheringId().equals(cellGatheringId2))
        .toList().size()).isEqualTo(3);
  }

  @Test
  @DisplayName("저장되지 않은 셀모임멤버아이디리스트로 셀모임멤버기도제목리스트 조회 --> 조회안됨")
  void test2() {
    // given
    List<UUID> cellGatheringIdList = List.of(TestUtil.getRandomUUID(), TestUtil.getRandomUUID());

    // when
    List<CellGatheringMemberEntity> cellGatheringMemberList = this.cellGatheringMemberRepository.findAllByCellGatheringIdList(
        cellGatheringIdList);

    // then
    assertThat(cellGatheringMemberList.size()).isEqualTo(0);
  }

  @Test
  @DisplayName("셀모임멤버 업데이트")
  void test3() {
    // given
    CellServiceDto.updateAttendancesAndStoryWhenExisting updateDto = new CellServiceDto.updateAttendancesAndStoryWhenExisting(
        cellGatheringMember1.getCellGatheringMemberId(), null, true, null);

    // when
    this.cellGatheringMemberRepository.updateByCellGatheringMemberId(updateDto);
    CellGatheringMemberEntity updatedCellGatheringMember = this.cellGatheringMemberRepository.findById(
            updateDto.getCellGatheringMemberId())
        .orElseThrow(() -> new CustomException("테스트 데이터가 준비되지 않았어요."));

    // then
    assertThat(updatedCellGatheringMember.isWorshipAttendance()).isEqualTo(false);
    assertThat(updatedCellGatheringMember.isCellGatheringAttendance()).isEqualTo(true);
    assertThat(updatedCellGatheringMember.getStory()).isEqualTo(null);

  }
}