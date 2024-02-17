package feedmysheep.feedmysheepapi.domain.church.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.domain.TestUtil;
import feedmysheep.feedmysheepapi.global.config.TestQueryDslConfig;
import feedmysheep.feedmysheepapi.models.OrganEntity;
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
public class OrganRepositoryTest {

  @Autowired
  private OrganRepository organRepository;

  private static UUID bodyId = TestUtil.getRandomUUID();

  @BeforeAll
  public static void setup(@Autowired OrganRepository organRepository) {
    organRepository.save(DataFactory.createOrganByBodyId(bodyId));
    organRepository.save(DataFactory.createOrganByBodyId(bodyId));
    organRepository.save(DataFactory.createOrganByBodyId(bodyId));
    organRepository.save(DataFactory.createOrganByBodyId(TestUtil.getRandomUUID()));
  }

  @AfterAll
  public static void cleanup(@Autowired OrganRepository organRepository) {
    organRepository.deleteAll();
  }

  @Test
  @DisplayName("바디아이디로 유효한 올건리스트 조회")
  void test1() {
    // given

    // when
    List<OrganEntity> organList = this.organRepository.findAllByBodyId(bodyId);

    // then
    assertThat(organList.size()).isEqualTo(3);
  }
}
