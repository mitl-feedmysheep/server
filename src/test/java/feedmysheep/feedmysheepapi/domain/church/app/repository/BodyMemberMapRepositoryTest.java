package feedmysheep.feedmysheepapi.domain.church.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.domain.TestUtil;
import feedmysheep.feedmysheepapi.global.config.TestQueryDslConfig;
import feedmysheep.feedmysheepapi.models.BodyMemberMapEntity;
import java.time.LocalDateTime;
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
class BodyMemberMapRepositoryTest {

  @Autowired
  private BodyMemberMapRepository bodyMemberMapRepository;

  static UUID memberId = TestUtil.getRandomUUID();
  static UUID invalidMemberId = TestUtil.getRandomUUID();
  static UUID bodyId = TestUtil.getRandomUUID();

  @BeforeAll
  public static void setup(@Autowired BodyMemberMapRepository bodyMemberMapRepository) {
    bodyMemberMapRepository.save(
        DataFactory.createBodyMemberMapByBodyIdAndMemberId(bodyId, memberId));
    BodyMemberMapEntity invalidBodyMemberMap = DataFactory.createBodyMemberMapByBodyIdAndMemberId(
        bodyId, invalidMemberId);
    invalidBodyMemberMap.setDeletedAt(LocalDateTime.now());
    bodyMemberMapRepository.save(invalidBodyMemberMap);
    bodyMemberMapRepository.save(
        DataFactory.createBodyMemberMapByBodyIdAndMemberId(TestUtil.getRandomUUID(), memberId));
    bodyMemberMapRepository.save(
        DataFactory.createBodyMemberMapByBodyIdAndMemberId(bodyId, TestUtil.getRandomUUID()));
  }


  @AfterAll
  public static void cleanup(@Autowired BodyMemberMapRepository bodyMemberMapRepository) {
    bodyMemberMapRepository.deleteAll();
  }

  @Test
  @DisplayName("유효한 바디멤버맵 조회")
  void test1() {
    // given

    // when
    Optional<BodyMemberMapEntity> bodyMemberMap = this.bodyMemberMapRepository.findByBodyIdAndMemberId(
        bodyId, memberId);

    // then
    assertThat(bodyMemberMap).isPresent();
  }

  @Test
  @DisplayName("유효하지 않은 바디멤버맵 조회")
  void test2() {
    // given

    // when
    Optional<BodyMemberMapEntity> bodyMemberMap = this.bodyMemberMapRepository.findByBodyIdAndMemberId(
        bodyId, invalidMemberId);

    // then
    assertThat(bodyMemberMap).isNotPresent();
  }
}