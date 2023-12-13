package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import static org.junit.jupiter.api.Assertions.*;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.domain.TestUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CellRepositoryTest {

  @Autowired
  private CellRepository cellRepository;


  @BeforeAll
  public static void setup(@Autowired CellRepository cellRepository) {
    Long organId = TestUtil.getRandomLong();
    cellRepository.save(DataFactory.createCellByOrganId(organId));
    cellRepository.save(DataFactory.createCellByOrganId(organId));
    cellRepository.save(DataFactory.createCellByOrganId(organId));
    cellRepository.save(DataFactory.createCellByOrganId(organId));
  }

  @AfterAll
  public static void cleanup(@Autowired CellRepository cellRepository) {
    cellRepository.deleteAll();
  }

  // TODO 테스트 작성

}