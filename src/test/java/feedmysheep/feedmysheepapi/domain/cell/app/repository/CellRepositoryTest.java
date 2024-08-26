package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.domain.TestUtil;
import feedmysheep.feedmysheepapi.global.config.TestQueryDslConfig;
import feedmysheep.feedmysheepapi.models.CellEntity;
import java.time.LocalDate;
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
class CellRepositoryTest {

  @Autowired
  private CellRepository cellRepository;

  static UUID organId1 = TestUtil.getRandomUUID();
  static UUID organId2 = TestUtil.getRandomUUID();

  @BeforeAll
  public static void setUp(@Autowired CellRepository cellRepository) {
    cellRepository.save(DataFactory.createCellByOrganId(organId1));
    cellRepository.save(DataFactory.createCellByOrganId(organId2));
    cellRepository.save(DataFactory.createCellByOrganId(organId2));
    cellRepository.save(DataFactory.createCellByOrganId(organId2));
    CellEntity invalidCell = DataFactory.createCellByOrganId(organId2);
    invalidCell.setStartDate(LocalDate.parse("2000-01-01"));
    invalidCell.setEndDate(LocalDate.parse("2000-12-31"));
    cellRepository.save(invalidCell);
    cellRepository.save(DataFactory.createCellByOrganId(TestUtil.getRandomUUID()));
  }

  @AfterAll
  public static void tearDown(@Autowired CellRepository cellRepository) {
    cellRepository.deleteAll();
  }

  @Test
  @DisplayName("올건내에 속한 모든 셀리스트 조회")
  void test1() {
    // given

    // when
    List<CellEntity> cellList = this.cellRepository.findAllByOrganIdListAndCurDate(List.of(organId1, organId2));

    // then
    assertThat(cellList.size()).isEqualTo(4);
  }
}