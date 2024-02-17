package feedmysheep.feedmysheepapi.domain.text.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.domain.TestUtil;
import feedmysheep.feedmysheepapi.global.config.TestQueryDslConfig;
import feedmysheep.feedmysheepapi.models.TextEntity;
import java.util.Optional;
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
class TextRepositoryTest {
  @Autowired
  private TextRepository textRepository;

  static TextEntity text1;

  @BeforeAll
  public static void setup(@Autowired TextRepository textRepository) {
    String screenKey = TestUtil.getRandomString();
    text1 = textRepository.save(DataFactory.createTextByScreenKey(screenKey));
  }

  @AfterAll
  public static void cleanup(@Autowired TextRepository textRepository) {
    textRepository.deleteAll();
  }

  @Test
  @DisplayName("스크린키 값으로 텍스트 조회 성공 O")
  void test1() {
    // given

    // when
    Optional<TextEntity> text = this.textRepository.findByScreenKey(text1.getScreenKey());

    // then
    assertThat(text).isPresent();
  }

  @Test
  @DisplayName("스크린키 값으로 텍스트 조회 성공 X")
  void test2() {
    // given

    // when
    Optional<TextEntity> text = this.textRepository.findByScreenKey(TestUtil.getRandomString());

    // then
    assertThat(text).isNotPresent();
  }
}