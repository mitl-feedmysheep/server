package feedmysheep.feedmysheepapi.domain.auth.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.domain.TestUtil;
import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import java.util.Optional;
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
class AuthorizationRepositoryTest {
  @Autowired
  private AuthorizationRepository authorizationRepository;

  static AuthorizationEntity authorization1;

  @BeforeAll
  public static void setup(@Autowired AuthorizationRepository authorizationRepository) {
    authorization1 = authorizationRepository.save(DataFactory.createAuthorization());
  }

  @AfterAll
  public static void cleanup(@Autowired AuthorizationRepository authorizationRepository) {
    authorizationRepository.deleteAll();
  }

  @Test
  @DisplayName("권한아이디로 권한 엔티티 찾기 '성공'")
  void test1() {
    // given

    // when
    Optional<AuthorizationEntity> authorization = this.authorizationRepository.getAuthorizationByAuthorizationId(
        authorization1.getAuthorizationId());

    // then
    assertThat(authorization).isPresent();
  }

  @Test
  @DisplayName("권한아이디로 권한 엔티티 찾기 '실패'")
  void test2() {
    // given
    Long randomLong = (long) TestUtil.getRandomNum(5);

    // when
    Optional<AuthorizationEntity> authorization = this.authorizationRepository.getAuthorizationByAuthorizationId(randomLong);

    // then
    assertThat(authorization).isNotPresent();
  }
}