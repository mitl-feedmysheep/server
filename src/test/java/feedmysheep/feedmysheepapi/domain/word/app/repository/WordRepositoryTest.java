package feedmysheep.feedmysheepapi.domain.word.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.domain.TestUtil;
import feedmysheep.feedmysheepapi.global.config.TestConfig;
import feedmysheep.feedmysheepapi.models.WordEntity;
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
class WordRepositoryTest {

  @Autowired
  private WordRepository wordRepository;

  static WordEntity word1;

  @BeforeAll
  public static void setup(@Autowired WordRepository wordRepository) {
    String screenKey = TestUtil.getRandomString();
    word1 = wordRepository.save(DataFactory.createWordByScreenKey(screenKey));
    wordRepository.save(DataFactory.createWordByScreenKey(screenKey));
  }

  @AfterAll
  public static void cleanup(@Autowired WordRepository wordRepository) {
    wordRepository.deleteAll();
  }

  @Test
  @DisplayName("유효한 메인스크린과 서브스크린에 맞는 말씀리스트 조회")
  public void test1() {
    // given

    // when
    List<WordEntity> wordList = this.wordRepository.findAllByScreenKey(word1.getScreenKey());

    // then
    assertThat(wordList.size()).isEqualTo(2);
  }

  @Test
  @DisplayName("서브스크린이 null인 말씀리스트 조회 -> 조회가능")
  public void test2() {
    // given
    WordEntity word = this.wordRepository.save(
        DataFactory.createWordByScreenKey(TestUtil.getRandomString()));

    // when
    List<WordEntity> emptyWordList = this.wordRepository.findAllByScreenKey(word.getScreenKey());

    // then
    assertThat(emptyWordList.size()).isEqualTo(1);
  }
}