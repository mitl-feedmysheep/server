package feedmysheep.feedmysheepapi.domain.auth.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.domain.TestUtil;
import feedmysheep.feedmysheepapi.global.config.TestQueryDslConfig;
import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import java.util.Optional;
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
class AuthorizationRepositoryTest {

  @Autowired
  private AuthorizationRepository authorizationRepository;

  static AuthorizationEntity authorization1;

  @BeforeAll
  public static void setUp(@Autowired AuthorizationRepository authorizationRepository) {
    authorization1 = DataFactory.createAuthorization();
    authorization1.setLevel(1);
    authorizationRepository.save(authorization1);
    authorizationRepository.save(authorization1);
  }

  @AfterAll
  public static void tearDown(@Autowired AuthorizationRepository authorizationRepository) {
    authorizationRepository.deleteAll();
  }

  @Test
  @DisplayName("권한아이디로 권한 엔티티 찾기 '성공'")
  void test1() {
    // given

    // when
    Optional<AuthorizationEntity> authorization = this.authorizationRepository.findByAuthorizationId(
        authorization1.getAuthorizationId());

    // then
    assertThat(authorization).isPresent();
  }

  @Test
  @DisplayName("권한아이디로 권한 엔티티 찾기 '실패'")
  void test2() {
    // given
    UUID randomUUID = TestUtil.getRandomUUID();

    // when
    Optional<AuthorizationEntity> authorization = this.authorizationRepository.findByAuthorizationId(
        randomUUID);

    // then
    assertThat(authorization).isNotPresent();
  }

  @Test
  @DisplayName("레벨로 권한 엔티티 찾기 '성공'")
  void test3() {
    // given
    int levelToFind = 1;

    // when
    Optional<AuthorizationEntity> authorization = this.authorizationRepository.findByLevel(
        levelToFind);

    // then
    assertThat(authorization).isPresent();
  }

  @Test
  @DisplayName("레벨로 권한 엔티티 찾기 '실패'")
  void test4() {
    // given
    int levelToFind = 9999;

    // when
    Optional<AuthorizationEntity> authorization = this.authorizationRepository.findByLevel(
        levelToFind);

    // then
    assertThat(authorization).isNotPresent();
  }
}