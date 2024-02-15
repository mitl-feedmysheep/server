package feedmysheep.feedmysheepapi.domain.church.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.domain.TestUtil;
import feedmysheep.feedmysheepapi.global.config.TestConfig;
import feedmysheep.feedmysheepapi.models.OrganMemberMapEntity;
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
class OrganMemberMapRepositoryTest {

  @Autowired
  private OrganMemberMapRepository organMemberMapRepository;

  static List<Long> organIdList = List.of(TestUtil.getRandomLong(), TestUtil.getRandomLong(),
      TestUtil.getRandomLong(), TestUtil.getRandomLong());
  static Long memberId = TestUtil.getRandomLong();

  @BeforeAll
  public static void setup(@Autowired OrganMemberMapRepository organMemberMapRepository) {
    organMemberMapRepository.save(
        DataFactory.createOrganMemberMapByOrganIdAndMemberId(organIdList.get(0), memberId));
    organMemberMapRepository.save(
        DataFactory.createOrganMemberMapByOrganIdAndMemberId(organIdList.get(1), memberId));
    organMemberMapRepository.save(
        DataFactory.createOrganMemberMapByOrganIdAndMemberId(organIdList.get(2), memberId));
    OrganMemberMapEntity invalidOrganMemberMap = DataFactory.createOrganMemberMapByOrganIdAndMemberId(
        TestUtil.getRandomLong(), memberId);
    invalidOrganMemberMap.setValid(false);
    organMemberMapRepository.save(invalidOrganMemberMap);

  }

  @AfterAll
  public static void cleanup(@Autowired OrganMemberMapRepository organMemberMapRepository) {
    organMemberMapRepository.deleteAll();
  }

  @Test
  @DisplayName("유효한 + 유효하지 않은 올건아이디리스트 -> 유효한 올건아이디리스트로만 조회 가능")
  void test1() {
    // given

    // when
    List<OrganMemberMapEntity> organMemberMapList = this.organMemberMapRepository.findAllByOrganIdListAndMemberId(
        organIdList, memberId);

    // then
    assertThat(organMemberMapList.size()).isEqualTo(3);
  }
}