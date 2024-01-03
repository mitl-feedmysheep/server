package feedmysheep.feedmysheepapi.domain.text.app.repository;

import static org.junit.jupiter.api.Assertions.*;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.domain.TestUtil;
import feedmysheep.feedmysheepapi.models.TextEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class TextRepositoryTest {
  @Autowired
  private TextRepository textRepository;

  static TextEntity text1;

  @BeforeAll
  public static void setup(@Autowired TextRepository textRepository) {
    String screenKey = TestUtil.getRandomString();
    text1 = textRepository.save(DataFactory.createTextByScreenKey(screenKey));
    textRepository.save(DataFactory.createTextByScreenKey(screenKey));
  }

  @AfterAll
  public static void cleanup(@Autowired TextRepository textRepository) {
    textRepository.deleteAll();
  }

  @Test
  @DisplayName("")
  void test1() {}
}