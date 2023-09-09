package feedmysheep.feedmysheepapi.domain.word.app.repository;


import static org.assertj.core.api.Assertions.*;

import feedmysheep.feedmysheepapi.domain.word.app.dto.WordResDto;
import feedmysheep.feedmysheepapi.models.WordEntity;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class WordRepositoryTest {
  @Autowired
  private WordRepository wordRepository;

  @BeforeEach
  public void setup() {
    WordEntity testWord1 = WordEntity.builder()
        .mainScreen("testMain1")
        .subScreen("testSub1")
        .displayStartDate(LocalDate.parse("2000-01-01"))
        .displayEndDate(LocalDate.parse("2000-01-02"))
        .book("testBook1")
        .chapter(1)
        .verse(1)
        .words("testWords1")
        .build();
    wordRepository.save(testWord1);

    WordEntity testWord2 = WordEntity.builder()
        .mainScreen("testMain2")
        .displayStartDate(LocalDate.parse("2000-01-01"))
        .displayEndDate(LocalDate.parse("2000-01-02"))
        .book("testBook2")
        .chapter(2)
        .verse(2)
        .words("testWords2")
        .build();
    wordRepository.save(testWord2);
  }

  @Test
  @DisplayName("메인 스크린과 서브스크린으로 말씀 가져인오기")
  public void getWordByMainScreenAndSubScreen() {
    WordResDto.getWordByMainAndSubScreen word1 = wordRepository.findByMainScreenAndSubScreen("testMain1", "testSub1");
    assertThat(word1.getBook()).isEqualTo("testBook1");
    assertThat(word1.getChapter()).isEqualTo(1);
    assertThat(word1.getVerse()).isEqualTo(1);
    assertThat(word1.getWords()).isEqualTo("testWords1");
  }

  @Test
  @DisplayName("메인 스크린으로 말씀 가져오기 (서브스크린이 null인 경우)")
  public void getWordByMainScreen() {
    WordResDto.getWordByMainAndSubScreen word2 = wordRepository.findByMainScreenAndSubScreen("testMain2", null);
    assertThat(word2.getBook()).isEqualTo("testBook2");
    assertThat(word2.getChapter()).isEqualTo(2);
    assertThat(word2.getVerse()).isEqualTo(2);
    assertThat(word2.getWords()).isEqualTo("testWords2");
  }
}