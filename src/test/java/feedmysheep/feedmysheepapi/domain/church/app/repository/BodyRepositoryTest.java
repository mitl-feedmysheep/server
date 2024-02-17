package feedmysheep.feedmysheepapi.domain.church.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.domain.TestUtil;
import feedmysheep.feedmysheepapi.global.config.TestQueryDslConfig;
import feedmysheep.feedmysheepapi.models.BodyEntity;
import feedmysheep.feedmysheepapi.models.ChurchEntity;
import java.time.LocalDateTime;
import java.util.List;
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
class BodyRepositoryTest {

  @Autowired
  private BodyRepository bodyRepository;

  static ChurchEntity church1;
  static BodyEntity body1;
  static BodyEntity invalidBody1;

  @BeforeAll
  public static void setup(@Autowired BodyRepository bodyRepository,
      @Autowired ChurchRepository churchRepository) {
    // 기본 한개씩
    ChurchEntity church = DataFactory.createChurch();
    church1 = churchRepository.save(church);
    body1 = bodyRepository.save(DataFactory.createBodyByChurchId(church1.getChurchId()));
    BodyEntity invalidBody = DataFactory.createBodyByChurchId(church1.getChurchId());
    invalidBody.setDeletedAt(LocalDateTime.now());
    invalidBody1 = bodyRepository.save(invalidBody);
  }

  @AfterAll
  public static void cleanup(@Autowired BodyRepository bodyRepository,
      @Autowired ChurchRepository churchRepository) {
    bodyRepository.deleteAll();
    churchRepository.deleteAll();
  }

  @Test
  @DisplayName("교회아이디가 있을 때 -> 유효한 바디리스트 2개 가져오기")
  void test1() {
    // given
    this.bodyRepository.save(DataFactory.createBodyByChurchId(church1.getChurchId()));

    // when
    List<BodyEntity> bodyList = this.bodyRepository.findAllByChurchId(church1.getChurchId());

    // then
    assertThat(bodyList.size()).isEqualTo(2);
  }

  @Test
  @DisplayName("교회아이디가 있을 때 -> 유효한 바디리스트 1개 가져오기")
  void test2() {
    // given
    BodyEntity body = DataFactory.createBodyByChurchId(church1.getChurchId());
    body.setDeletedAt(LocalDateTime.now());
    this.bodyRepository.save(body);

    // when
    List<BodyEntity> bodyList = this.bodyRepository.findAllByChurchId(church1.getChurchId());

    // then
    assertThat(bodyList.size()).isEqualTo(1);
    assertThat(bodyList.get(0).getChurchId()).isEqualTo(church1.getChurchId());
  }

  @Test
  @DisplayName("교회아이디가 없을 때 -> 바디리스트 0개")
  void test3() {
    // given

    // when
    List<BodyEntity> bodyList = this.bodyRepository.findAllByChurchId(TestUtil.getRandomUUID());

    // then
    assertThat(bodyList.size()).isEqualTo(0);
  }

  @Test
  @DisplayName("바디아이디가 있을 때 -> 바디 가져오기")
  void test4() {
    // given

    // when
    Optional<BodyEntity> body = this.bodyRepository.findByBodyId(body1.getBodyId());

    // then
    assertThat(body).isPresent();
    assertThat(body.get().getBodyId()).isEqualTo(body1.getBodyId());
  }

  @Test
  @DisplayName("바디아이디가 있어도 유효하지 않은 바디 가져오기 실패")
  void test5() {
    // given

    // when
    Optional<BodyEntity> body = this.bodyRepository.findByBodyId(invalidBody1.getBodyId());

    // then
    assertThat(body).isNotPresent();
  }
}