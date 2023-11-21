package feedmysheep.feedmysheepapi.domain.church.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.domain.TestUtil;
import feedmysheep.feedmysheepapi.models.BodyEntity;
import feedmysheep.feedmysheepapi.models.ChurchEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class BodyRepositoryTest {

  @Autowired
  private BodyRepository bodyRepository;

  static DataFactory dataFactory;

  static ChurchEntity church1;
  static BodyEntity body1;

  @BeforeAll
  public static void setup(@Autowired BodyRepository bodyRepository,
      @Autowired ChurchRepository churchRepository) {
    // 기본 한개씩
    dataFactory = new DataFactory(churchRepository, bodyRepository);
    church1 = dataFactory.createTestChurch(true);
    body1 = dataFactory.createTestBodyByChurchId(church1.getChurchId(), true);
  }

  @Test
  @DisplayName("교회아이디가 있을 때 -> 유효한 바디리스트 2개 가져오기")
  void test1() {
    // given
    dataFactory.createTestBodyByChurchId(church1.getChurchId(), true);

    // when
    List<BodyEntity> bodyList = this.bodyRepository.getBodyListByChurchId(church1.getChurchId());

    // then
    assertThat(bodyList.size()).isEqualTo(2);
  }

  @Test
  @DisplayName("교회아이디가 있을 때 -> 유효한 바디리스트 1개 가져오기")
  void test2() {
    // given
    dataFactory.createTestBodyByChurchId(church1.getChurchId(), false);

    // when
    List<BodyEntity> bodyList = this.bodyRepository.getBodyListByChurchId(church1.getChurchId());

    // then
    assertThat(bodyList.size()).isEqualTo(1);
  }

  @Test
  @DisplayName("교회아이디가 없을 때 -> 바디리스트 0개")
  void test3() {
    // given

    // when
    List<BodyEntity> bodyList = this.bodyRepository.getBodyListByChurchId(
        (long) TestUtil.getRandomNum(5));

    // then
    assertThat(bodyList.size()).isEqualTo(0);
  }

}