package feedmysheep.feedmysheepapi.domain.church.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.domain.TestUtil;
import feedmysheep.feedmysheepapi.domain.auth.app.repository.AuthorizationRepository;
import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
import feedmysheep.feedmysheepapi.global.config.TestQueryDslConfig;
import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import feedmysheep.feedmysheepapi.models.ChurchEntity;
import feedmysheep.feedmysheepapi.models.ChurchMemberMapEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
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
  static ChurchMemberMapEntity validChurchMemberMap1;
  static ChurchMemberMapEntity invalidChurchMemberMap1;

  @BeforeAll
  public static void setUp(@Autowired ChurchRepository churchRepository,
      @Autowired AuthorizationRepository authorizationRepository,
      @Autowired MemberRepository memberRepository,
      @Autowired ChurchMemberMapRepository churchMemberMapRepository) {
    ChurchEntity church = DataFactory.createChurch();
    church1 = churchRepository.save(church);
    authorization1 = authorizationRepository.save(DataFactory.createAuthorization());
    member1 = memberRepository.save(DataFactory.createMember(authorization1.getAuthorizationId()));
    // 유효한 교회멤버맵
    validChurchMemberMap1 = churchMemberMapRepository.save(
        DataFactory.createChurchMemberMap(church1.getChurchId(), member1.getMemberId()));
    // 유효하지 않은 교회멤버맵
    invalidChurchMemberMap1 = DataFactory.createChurchMemberMap(TestUtil.getRandomUUID(),
        TestUtil.getRandomUUID());
    invalidChurchMemberMap1.setDeletedAt(LocalDateTime.now());
    churchMemberMapRepository.save(invalidChurchMemberMap1);
  }

  @AfterAll
  public static void tearDown(@Autowired ChurchRepository churchRepository,
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
    List<ChurchMemberMapEntity> churchMemberMapList = this.churchMemberMapRepository.findAllByMemberId(
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
    List<ChurchMemberMapEntity> churchMemberMapList = this.churchMemberMapRepository.findAllByMemberId(
        member2.getMemberId());

    // then
    assertThat(churchMemberMapList.size()).isEqualTo(0);
  }

  @Test
  @DisplayName("멤버가 존재한다 -> 교회에 등록되어 있으나 현재는 다니지 않는다")
  void test3() {
    // given
    ChurchEntity church = DataFactory.createChurch();
    ChurchEntity church2 = this.churchRepository.save(church);
    ChurchMemberMapEntity churchMemberMap2 = DataFactory.createChurchMemberMap(
        church2.getChurchId(), member1.getMemberId());
    churchMemberMap2.setDeletedAt(LocalDateTime.now());
    churchMemberMap2.setDeleteReason(TestUtil.getRandomString());
    this.churchMemberMapRepository.save(churchMemberMap2);

    // when
    List<ChurchMemberMapEntity> churchMemberMapList = this.churchMemberMapRepository.findAllByMemberId(
        member1.getMemberId());

    // then
    assertThat(churchMemberMapList.size()).isEqualTo(1);
    assertThat(churchMemberMapList.get(0).getChurchMemberMapId()).isEqualTo(
        validChurchMemberMap1.getChurchMemberMapId());
  }

  @Test
  @DisplayName("교회아이디와 멤버아이디로 유효한 교회멤버맵을 가져온다 -> 성공")
  void test4() {
    // given

    // when
    Optional<ChurchMemberMapEntity> churchMemberMap = this.churchMemberMapRepository.findByChurchIdAndMemberId(
        church1.getChurchId(), member1.getMemberId());

    // then
    assertThat(churchMemberMap).isPresent();
    assertThat(churchMemberMap.get().getChurchId()).isEqualTo(validChurchMemberMap1.getChurchId());
    assertThat(churchMemberMap.get().getMemberId()).isEqualTo(validChurchMemberMap1.getMemberId());
  }

  @Test
  @DisplayName("교회아이디와 멤버아이디로 유효한 교회멤버맵을 가져온다 -> 실패")
  void test5() {
    // given

    // when
    Optional<ChurchMemberMapEntity> churchMemberMap = this.churchMemberMapRepository.findByChurchIdAndMemberId(
        invalidChurchMemberMap1.getChurchId(), invalidChurchMemberMap1.getMemberId());

    // then
    assertThat(churchMemberMap).isNotPresent();
  }

  @Test
  @DisplayName("교회아이디와 멤버아이디로 유효하지 않은 교회멤버맵을 가져온다 -> 성공")
  void test6() {
    // given

    // when
    Optional<ChurchMemberMapEntity> churchMemberMap = this.churchMemberMapRepository.getInvalidChurchMemberMapByChurchIdAndMemberId(
        invalidChurchMemberMap1.getChurchId(), invalidChurchMemberMap1.getMemberId());

    // then
    assertThat(churchMemberMap).isPresent();
    assertThat(churchMemberMap.get().getChurchId()).isEqualTo(
        invalidChurchMemberMap1.getChurchId());
    assertThat(churchMemberMap.get().getMemberId()).isEqualTo(
        invalidChurchMemberMap1.getMemberId());
  }

  @Test
  @DisplayName("교회아이디와 멤버아이디로 유효하지 않은 교회멤버맵을 가져온다 -> 실패")
  void test7() {
    // given

    // when
    Optional<ChurchMemberMapEntity> churchMemberMap = this.churchMemberMapRepository.getInvalidChurchMemberMapByChurchIdAndMemberId(
        validChurchMemberMap1.getChurchId(), validChurchMemberMap1.getMemberId());

    // then
    assertThat(churchMemberMap).isNotPresent();
  }
}