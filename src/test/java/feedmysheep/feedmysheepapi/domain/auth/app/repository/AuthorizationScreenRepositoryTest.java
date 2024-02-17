package feedmysheep.feedmysheepapi.domain.auth.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.domain.TestUtil;
import feedmysheep.feedmysheepapi.global.config.TestQueryDslConfig;
import feedmysheep.feedmysheepapi.models.AuthorizationScreenEntity;
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
class AuthorizationScreenRepositoryTest {

  @Autowired
  private AuthorizationScreenRepository authorizationScreenRepository;

  static AuthorizationScreenEntity authorizationScreen1;

  @BeforeAll
  public static void setUp(@Autowired AuthorizationScreenRepository authorizationScreenRepository) {
    authorizationScreen1 = authorizationScreenRepository.save(
        DataFactory.createAuthorizationScreen());
  }

  @AfterAll
  public static void tearDown(
      @Autowired AuthorizationScreenRepository authorizationScreenRepository) {
    authorizationScreenRepository.deleteAll();
  }

  @Test
  @DisplayName("스크린키로 권한스크린 엔티티 찾기 '성공'")
  void test1() {
    // given

    // when
    Optional<AuthorizationScreenEntity> authorizationScreen = this.authorizationScreenRepository.findByScreenKey(
        authorizationScreen1.getScreenKey());

    // then
    assertThat(authorizationScreen).isPresent();
  }

  @Test
  @DisplayName("스크린키로 권한스크린 엔티티 찾기 '실패'")
  void test2() {
    // given
    String randomString = TestUtil.getRandomString();

    // when
    Optional<AuthorizationScreenEntity> authorizationScreen = this.authorizationScreenRepository.findByScreenKey(
        randomString);

    // then
    assertThat(authorizationScreen).isNotPresent();
  }
}