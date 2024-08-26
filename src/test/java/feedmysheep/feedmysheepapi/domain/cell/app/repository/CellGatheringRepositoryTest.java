package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.domain.TestUtil;
import feedmysheep.feedmysheepapi.global.config.TestQueryDslConfig;
import feedmysheep.feedmysheepapi.models.CellGatheringEntity;
import java.util.Optional;
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
class CellGatheringRepositoryTest {

  @Autowired
  private CellGatheringRepository cellGatheringRepository;

  static UUID cellId1 = TestUtil.getRandomUUID();
  static CellGatheringEntity cellGathering1;

  @BeforeAll
  public static void setUp(@Autowired CellGatheringRepository cellGatheringRepository) {
    cellGathering1 = cellGatheringRepository.save(DataFactory.createCellGathering(cellId1));
    cellGatheringRepository.save(DataFactory.createCellGathering(cellId1));
    cellGatheringRepository.save(DataFactory.createCellGathering(cellId1));
    cellGatheringRepository.save(DataFactory.createCellGathering(cellId1));
  }

  @AfterAll
  public static void tearDown(@Autowired CellGatheringRepository cellGatheringRepository) {
    cellGatheringRepository.deleteAll();
  }

  @Test
  @DisplayName("셀모임아이디로 셀모임 조회 --> 성공")
  void test1() {
    // given
    // when
    Optional<CellGatheringEntity> cellGathering = this.cellGatheringRepository.findByCellGatheringId(
        cellGathering1.getCellGatheringId());

    // then
    assertThat(cellGathering.isPresent()).isTrue();
  }

  @Test
  @DisplayName("셀모임아이디로 셀모임 조회 --> 실패")
  void test2() {
    // given
    // when
    Optional<CellGatheringEntity> cellGathering = this.cellGatheringRepository.findByCellGatheringId(
        TestUtil.getRandomUUID());

    // then
    assertThat(cellGathering.isPresent()).isFalse();
  }

  @Test
  @DisplayName("셀모임아이디로 삭제 --> 성공")
  void test3() {
    // given

    // when
    this.cellGatheringRepository.deleteByCellGatheringId(cellGathering1.getCellGatheringId());
    Optional<CellGatheringEntity> cellGathering = this.cellGatheringRepository.findById(
        cellGathering1.getCellGatheringId());

    // then
    assertThat(cellGathering.isPresent()).isFalse();
  }
}