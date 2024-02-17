package feedmysheep.feedmysheepapi.domain.media.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.domain.TestUtil;
import feedmysheep.feedmysheepapi.global.config.TestQueryDslConfig;
import feedmysheep.feedmysheepapi.models.MediaEntity;
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
@Import(TestQueryDslConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class MediaRepositoryTest {
  @Autowired
  private MediaRepository mediaRepository;

  static MediaEntity media1;

  @BeforeAll
  public static void setUp(@Autowired MediaRepository mediaRepository) {
    String screenKey = TestUtil.getRandomString();
    media1 = mediaRepository.save(DataFactory.createMediaByScreenKey(screenKey));
    mediaRepository.save(DataFactory.createMediaByScreenKey(screenKey));
  }

  @AfterAll
  public static void tearDown(@Autowired MediaRepository mediaRepository) {
    mediaRepository.deleteAll();
  }

  @Test
  @DisplayName("유효한 미디어 조회")
  public void test1() {
    // given

    // when
    List<MediaEntity> mediaList = this.mediaRepository.findAllByScreenKey(media1.getScreenKey());

    // then
    assertThat(mediaList.size()).isEqualTo(2);
  }
}