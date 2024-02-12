package feedmysheep.feedmysheepapi.domain.church.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.global.config.TestConfig;
import feedmysheep.feedmysheepapi.models.ChurchEntity;
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
@Import(TestConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ChurchRepositoryTest {

  @Autowired
  private ChurchRepository churchRepository;

  static ChurchEntity validChurch1;
  static ChurchEntity invalidChurch1;

  @BeforeAll
  public static void setup(@Autowired ChurchRepository churchRepository) {
    ChurchEntity validChurch = DataFactory.createChurch();
    validChurch.setValid(true);
    validChurch1 = churchRepository.save(validChurch);
    invalidChurch1 = DataFactory.createChurch();
    invalidChurch1.setValid(false);
    churchRepository.save(invalidChurch1);
  }

  @AfterAll
  public static void cleanup(@Autowired ChurchRepository churchRepository) {
    churchRepository.deleteAll();
  }

  @Test
  @DisplayName("유효한 모든 교회 조회")
  void test1() {
    // given
    ChurchEntity church = DataFactory.createChurch();
    church.setValid(true);
    this.churchRepository.save(church);

    // when
    List<ChurchEntity> churchList = this.churchRepository.getChurchList();

    // then
    assertThat(churchList.size()).isEqualTo(2);
  }

  @Test
  @DisplayName("유효한 교회만 이름으로 조회할 수 있다.")
  void test2() {
    // given

    // when
    List<ChurchEntity> validChurchList = this.churchRepository.getChurchListByChurchName(
        validChurch1.getChurchName());
    List<ChurchEntity> invalidChurchList = this.churchRepository.getChurchListByChurchName(
        invalidChurch1.getChurchName());

    // then
    assertThat(validChurchList.size()).isEqualTo(1);
    assertThat(validChurchList.get(0).getChurchName()).isEqualTo(validChurch1.getChurchName());
    assertThat(invalidChurchList.size()).isEqualTo(0);
  }

  @Test
  @DisplayName("교회아이디로 유효한 교회 조회 성공")
  void test3() {
    // given

    // when
    Optional<ChurchEntity> validChurch = this.churchRepository.getChurchByChurchId(
        validChurch1.getChurchId());

    // then
    assertThat(validChurch).isPresent();

  }

  @Test
  @DisplayName("교회아이디로 유효한 교회 조회 실패")
  void test4() {
    // given

    // when
    Optional<ChurchEntity> validChurch = this.churchRepository.getChurchByChurchId(
        invalidChurch1.getChurchId());

    // then
    assertThat(validChurch).isNotPresent();
  }
}