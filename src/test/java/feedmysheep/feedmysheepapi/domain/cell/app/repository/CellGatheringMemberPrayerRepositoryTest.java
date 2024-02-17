package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.domain.TestUtil;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellServiceDto;
import feedmysheep.feedmysheepapi.global.config.TestQueryDslConfig;
import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.models.CellGatheringMemberPrayerEntity;
import java.util.List;
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
class CellGatheringMemberPrayerRepositoryTest {

  @Autowired
  private CellGatheringMemberPrayerRepository cellGatheringMemberPrayerRepository;

  static UUID cellGatheringMemberId1 = TestUtil.getRandomUUID();
  static UUID cellGatheringMemberId2 = TestUtil.getRandomUUID();
  static UUID cellGatheringMemberId3 = TestUtil.getRandomUUID();
  static CellGatheringMemberPrayerEntity cellGatheringMemberPrayer1;

  @BeforeAll
  public static void setUp(
      @Autowired CellGatheringMemberPrayerRepository cellGatheringMemberPrayerRepository) {
    cellGatheringMemberPrayer1 = cellGatheringMemberPrayerRepository.save(
        DataFactory.createCellGatheringMemberPrayer(cellGatheringMemberId1));
    cellGatheringMemberPrayerRepository.save(
        DataFactory.createCellGatheringMemberPrayer(cellGatheringMemberId1));
    cellGatheringMemberPrayerRepository.save(
        DataFactory.createCellGatheringMemberPrayer(cellGatheringMemberId2));
    cellGatheringMemberPrayerRepository.save(
        DataFactory.createCellGatheringMemberPrayer(cellGatheringMemberId3));
    cellGatheringMemberPrayerRepository.save(
        DataFactory.createCellGatheringMemberPrayer(cellGatheringMemberId3));
  }

  @AfterAll
  public static void tearDown(
      @Autowired CellGatheringMemberPrayerRepository cellGatheringMemberPrayerRepository) {
    cellGatheringMemberPrayerRepository.deleteAll();
  }

  @Test
  @DisplayName("셀모임멤버아이디리스트로 셀모임멤버기도제목리스트 조회 --> 성공")
  void test1() {
    // given
    List<UUID> cellGatheringMemberIdList = List.of(cellGatheringMemberId1, cellGatheringMemberId2);

    // when
    List<CellGatheringMemberPrayerEntity> cellGatheringMemberPrayerList = this.cellGatheringMemberPrayerRepository.findAllByCellGatheringMemberIdList(
        cellGatheringMemberIdList);

    // then
    assertThat(cellGatheringMemberPrayerList.size()).isEqualTo(3);
    assertThat(cellGatheringMemberPrayerList.stream().filter(
        cellGatheringMemberPrayer -> cellGatheringMemberPrayer.getCellGatheringMemberId()
            .equals(cellGatheringMemberId1)).toList().size()).isEqualTo(2);
    assertThat(cellGatheringMemberPrayerList.stream().filter(
        cellGatheringMemberPrayer -> cellGatheringMemberPrayer.getCellGatheringMemberId()
            .equals(cellGatheringMemberId2)).toList().size()).isEqualTo(1);
  }

  @Test
  @DisplayName("저장되지 않은 셀모임멤버아이디리스트로 셀모임멤버기도제목리스트 조회 --> 조회안됨")
  void test2() {
    // given
    List<UUID> cellGatheringMemberIdList = List.of(TestUtil.getRandomUUID(),
        TestUtil.getRandomUUID());

    // when
    List<CellGatheringMemberPrayerEntity> cellGatheringMemberPrayerList = this.cellGatheringMemberPrayerRepository.findAllByCellGatheringMemberIdList(
        cellGatheringMemberIdList);

    // then
    assertThat(cellGatheringMemberPrayerList.size()).isEqualTo(0);
  }

  @Test
  @DisplayName("기도제목 업데이트 --> 성공")
  void test3() {
    // given
    String newPrayerRequest = TestUtil.getRandomString();
    CellServiceDto.updatePrayerById updateDto = new CellServiceDto.updatePrayerById(
        cellGatheringMemberPrayer1.getCellGatheringMemberPrayerId(), newPrayerRequest);

    // when
    this.cellGatheringMemberPrayerRepository.updateByCellGatheringMemberPrayerId(updateDto);
    CellGatheringMemberPrayerEntity cellGatheringMemberPrayer = this.cellGatheringMemberPrayerRepository.findById(
            updateDto.getCellGatheringMemberPrayerId())
        .orElseThrow(() -> new CustomException("테스트 데이터가 저장되지 않았어요."));

    // then
    assertThat(cellGatheringMemberPrayer.getPrayerRequest()).isEqualTo(newPrayerRequest);
  }

  @Test
  @DisplayName("삭제한 후 --> 조회되지 않음")
  void test4() {
    // given

    // when
    this.cellGatheringMemberPrayerRepository.deleteByCellGatheringMemberPrayerId(
        cellGatheringMemberPrayer1.getCellGatheringMemberPrayerId());
    Optional<CellGatheringMemberPrayerEntity> cellGatheringMemberPrayer = this.cellGatheringMemberPrayerRepository.findById(
        cellGatheringMemberPrayer1.getCellGatheringMemberPrayerId());

    // then
    assertThat(cellGatheringMemberPrayer.isPresent()).isFalse();

  }
}