package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.domain.TestUtil;
import feedmysheep.feedmysheepapi.global.config.TestConfig;
import feedmysheep.feedmysheepapi.models.CellEntity;
import java.time.LocalDate;
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
class CellRepositoryTest {

  @Autowired
  private CellRepository cellRepository;

  static Long organId1 = TestUtil.getRandomLong();
  static Long organId2 = TestUtil.getRandomLong();

  @BeforeAll
  public static void setup(@Autowired CellRepository cellRepository) {
    cellRepository.save(DataFactory.createCellByOrganId(organId1));
    cellRepository.save(DataFactory.createCellByOrganId(organId2));
    cellRepository.save(DataFactory.createCellByOrganId(organId2));
    cellRepository.save(DataFactory.createCellByOrganId(organId2));
    CellEntity invalidCell = DataFactory.createCellByOrganId(organId2);
    invalidCell.setStartDate(LocalDate.parse("2000-01-01"));
    invalidCell.setEndDate(LocalDate.parse("2000-12-31"));
    cellRepository.save(invalidCell);
    cellRepository.save(DataFactory.createCellByOrganId(TestUtil.getRandomLong()));
  }

  @AfterAll
  public static void cleanup(@Autowired CellRepository cellRepository) {
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