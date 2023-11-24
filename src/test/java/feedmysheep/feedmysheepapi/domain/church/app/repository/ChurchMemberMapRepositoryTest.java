package feedmysheep.feedmysheepapi.domain.church.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.domain.TestUtil;
import feedmysheep.feedmysheepapi.domain.auth.app.repository.AuthorizationRepository;
import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import feedmysheep.feedmysheepapi.models.ChurchEntity;
import feedmysheep.feedmysheepapi.models.ChurchMemberMapEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import java.util.List;
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
class ChurchMemberMapRepositoryTest {

  @Autowired
  private ChurchRepository churchRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private ChurchMemberMapRepository churchMemberMapRepository;

  static ChurchEntity church1;
  static AuthorizationEntity authorization1;
  static MemberEntity member1;
  static ChurchMemberMapEntity churchMemberMap1;

  @BeforeAll
  public static void setup(@Autowired ChurchRepository churchRepository,
      @Autowired AuthorizationRepository authorizationRepository,
      @Autowired MemberRepository memberRepository,
      @Autowired ChurchMemberMapRepository churchMemberMapRepository) {
    ChurchEntity church = DataFactory.createChurch();
    church.setValid(true);
    church1 = churchRepository.save(church);
    authorization1 = authorizationRepository.save(DataFactory.createAuthorization());
    member1 = memberRepository.save(DataFactory.createMember(authorization1.getAuthorizationId()));
    churchMemberMap1 = churchMemberMapRepository.save(
        DataFactory.createChurchMemberMap(church1.getChurchId(),
            member1.getMemberId()));
  }

  @AfterAll
  public static void cleanup(@Autowired ChurchRepository churchRepository,
      @Autowired AuthorizationRepository authorizationRepository,
      @Autowired MemberRepository memberRepository,
      @Autowired ChurchMemberMapRepository churchMemberMapRepository) {
    churchMemberMapRepository.deleteAll();
    authorizationRepository.deleteAll();
    memberRepository.deleteAll();
    churchRepository.deleteAll();
  }

  @Test
  @DisplayName("멤버가 존재한다 -> 교회에 등록되어 있다")
  void test1() {
    // given

    // when
    List<ChurchMemberMapEntity> churchMemberMapList = this.churchMemberMapRepository.getChurchMemberMapListByMemberId(
        member1.getMemberId());

    // then
    assertThat(churchMemberMapList.size()).isEqualTo(1);
    assertThat(churchMemberMapList.get(0).getChurchId()).isEqualTo(church1.getChurchId());
  }

  @Test
  @DisplayName("멤버가 존재한다 -> 교회에 등록되어 있지 않다")
  void test2() {
    // given
    MemberEntity member2 = this.memberRepository.save(
        DataFactory.createMember(authorization1.getAuthorizationId()));

    // when
    List<ChurchMemberMapEntity> churchMemberMapList = this.churchMemberMapRepository.getChurchMemberMapListByMemberId(
        member2.getMemberId());

    // then
    assertThat(churchMemberMapList.size()).isEqualTo(0);
  }

  @Test
  @DisplayName("멤버가 존재한다 -> 교회에 등록되어 있으나 현재는 다니지 않는다")
  void test3() {
    // given
    ChurchEntity church = DataFactory.createChurch();
    church.setValid(true);
    ChurchEntity church2 = this.churchRepository.save(church);
    ChurchMemberMapEntity churchMemberMap2 = DataFactory.createChurchMemberMap(
        church2.getChurchId(),
        member1.getMemberId());
    churchMemberMap2.setValid(false);
    churchMemberMap2.setInvalidReason(TestUtil.getRandomString());
    this.churchMemberMapRepository.save(churchMemberMap2);

    // when
    List<ChurchMemberMapEntity> churchMemberMapList = this.churchMemberMapRepository.getChurchMemberMapListByMemberId(
        member1.getMemberId());

    // then
    assertThat(churchMemberMapList.size()).isEqualTo(1);
    assertThat(churchMemberMapList.get(0).getChurchMemberMapId()).isEqualTo(
        churchMemberMap1.getChurchMemberMapId());
  }
}