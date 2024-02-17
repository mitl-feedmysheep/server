package feedmysheep.feedmysheepapi.domain.member.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.domain.TestUtil;
import feedmysheep.feedmysheepapi.domain.auth.app.repository.AuthorizationRepository;
import feedmysheep.feedmysheepapi.global.config.TestQueryDslConfig;
import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
class MemberRepositoryTest {

  @Autowired
  private MemberRepository memberRepository;

  static AuthorizationEntity authorization1;
  static MemberEntity validMember1;
  static MemberEntity invalidMember1;

  @BeforeAll
  public static void setUp(@Autowired AuthorizationRepository authorizationRepository,
      @Autowired MemberRepository memberRepository) {
    authorization1 = authorizationRepository.save(DataFactory.createAuthorization());
    MemberEntity validMember1ToSave = DataFactory.createMember(authorization1.getAuthorizationId());
    validMember1ToSave.setBirthday(LocalDate.of(1991, 11, 11))    ;
    validMember1 = memberRepository.save(validMember1ToSave);
    MemberEntity invalidMember = DataFactory.createMember(authorization1.getAuthorizationId());
    invalidMember.setDeletedAt(LocalDateTime.now());
    invalidMember1 = memberRepository.save(invalidMember);
  }

  @AfterAll
  public static void tearDown(@Autowired AuthorizationRepository authorizationRepository,
      @Autowired MemberRepository memberRepository) {
    authorizationRepository.deleteAll();
    // TODO 엔티티 조회할 때, deleted_at이 null인거를 가져와서 삭제를 하다보니, 선택이 되지 않아서 삭제가 안됨..... ㄷㄷㄷ
    memberRepository.deleteAll();
  }


  @Test
  @DisplayName("멤버 아이디로 유효한 멤버 조회 가능")
  void test1() {
    // given

    // when
    Optional<MemberEntity> validMember = this.memberRepository.findByMemberId(
        validMember1.getMemberId());
    Optional<MemberEntity> invalidMemberNotExists = this.memberRepository.findByMemberId(
        invalidMember1.getMemberId());

    // then
    assertThat(validMember).isPresent();
    assertThat(invalidMemberNotExists).isNotPresent();
  }

  @Test
  @DisplayName("멤버 이메일로 유효한 멤버 조회 가능")
  void test2() {
    // given

    // when
    Optional<MemberEntity> validMember = this.memberRepository.findByEmail(validMember1.getEmail());
    Optional<MemberEntity> invalidMemberNotExists = this.memberRepository.findByEmail(
        invalidMember1.getEmail());

    // then
    assertThat(validMember).isPresent();
    assertThat(invalidMemberNotExists).isNotPresent();
  }

  @Test
  @DisplayName("멤버 휴대폰으로 유효한 멤버 조회 가능")
  void test3() {
    // given

    // when
    Optional<MemberEntity> validMember = this.memberRepository.findByPhone(validMember1.getPhone());
    Optional<MemberEntity> invalidMemberNotExists = this.memberRepository.findByPhone(
        invalidMember1.getPhone());

    // then
    assertThat(validMember).isPresent();
    assertThat(invalidMemberNotExists).isNotPresent();
  }

  @Test
  @DisplayName("멤버아이디리스트 & month & offset & limit으로 멤버 찾기")
  void test4() {
    // given
    // first 5
    MemberEntity member1 = DataFactory.createMember(TestUtil.getRandomUUID());
    member1.setBirthday(LocalDate.of(1991, 9, 1));
    MemberEntity member2 = DataFactory.createMember(TestUtil.getRandomUUID());
    member2.setBirthday(LocalDate.of(1991, 9, 2));
    MemberEntity member3 = DataFactory.createMember(TestUtil.getRandomUUID());
    member3.setBirthday(LocalDate.of(1991, 9, 3));
    MemberEntity member4 = DataFactory.createMember(TestUtil.getRandomUUID()); // false data
    member4.setBirthday(LocalDate.of(1991, 10, 4));
    MemberEntity member5 = DataFactory.createMember(TestUtil.getRandomUUID());
    member5.setBirthday(LocalDate.of(1991, 9, 5));
    MemberEntity member6 = DataFactory.createMember(TestUtil.getRandomUUID());
    member6.setBirthday(LocalDate.of(1991, 9, 6));
    // second 5
    MemberEntity member7 = DataFactory.createMember(TestUtil.getRandomUUID());
    member7.setBirthday(LocalDate.of(1991, 9, 7));
    MemberEntity member8 = DataFactory.createMember(TestUtil.getRandomUUID());
    member8.setBirthday(LocalDate.of(1991, 9, 8));
    MemberEntity member9 = DataFactory.createMember(TestUtil.getRandomUUID());
    member9.setBirthday(LocalDate.of(1991, 9, 9));
    MemberEntity member10 = DataFactory.createMember(TestUtil.getRandomUUID());  // false data
    member10.setBirthday(LocalDate.of(1991, 11, 10));
    MemberEntity member11 = DataFactory.createMember(TestUtil.getRandomUUID());
    member11.setBirthday(LocalDate.of(1991, 9, 11));
    MemberEntity member12 = DataFactory.createMember(TestUtil.getRandomUUID());
    member12.setBirthday(LocalDate.of(1991, 9, 12));
    // third 5
    MemberEntity member13 = DataFactory.createMember(TestUtil.getRandomUUID());  // false data
    member13.setBirthday(LocalDate.of(1991, 12, 13));
    MemberEntity member14 = DataFactory.createMember(TestUtil.getRandomUUID());
    member14.setBirthday(LocalDate.of(1991, 9, 14));
    List<MemberEntity> memberListToSave = List.of(member6, member7, member8, member9, member10,
        member1, member2, member3, member4, member5, member11, member12, member13,
        member14); // 뒤집어서 저장
    List<UUID> memberIdListToSelect = memberListToSave.stream().map(MemberEntity::getMemberId)
        .toList();
    this.memberRepository.saveAll(memberListToSave);

    // when
    List<MemberEntity> firstMemberList = this.memberRepository.findAllByMemberIdListAndMonth(
        memberIdListToSelect, 9, 0, 5);
    List<MemberEntity> secondMemberList = this.memberRepository.findAllByMemberIdListAndMonth(
        memberIdListToSelect, 9, 5, 5);
    List<MemberEntity> thirdMemberList = this.memberRepository.findAllByMemberIdListAndMonth(
        memberIdListToSelect, 9, 10, 5);

    // then
    assertThat(firstMemberList.size()).isEqualTo(5);
    assertThat(firstMemberList.stream()
        .anyMatch(member -> member.getMemberName().equals(member4.getMemberName()))).isFalse();
    assertThat(firstMemberList.stream()
        .anyMatch(member -> member.getMemberName().equals(member1.getMemberName()))).isTrue();
    assertThat(firstMemberList.stream()
        .anyMatch(member -> member.getMemberName().equals(member2.getMemberName()))).isTrue();
    assertThat(firstMemberList.stream()
        .anyMatch(member -> member.getMemberName().equals(member3.getMemberName()))).isTrue();
    assertThat(firstMemberList.stream()
        .anyMatch(member -> member.getMemberName().equals(member5.getMemberName()))).isTrue();
    assertThat(firstMemberList.stream()
        .anyMatch(member -> member.getMemberName().equals(member6.getMemberName()))).isTrue();
    assertThat(secondMemberList.size()).isEqualTo(5);
    assertThat(secondMemberList.stream()
        .anyMatch(member -> member.getMemberName().equals(member10.getMemberName()))).isFalse();
    assertThat(secondMemberList.stream()
        .anyMatch(member -> member.getMemberName().equals(member7.getMemberName()))).isTrue();
    assertThat(secondMemberList.stream()
        .anyMatch(member -> member.getMemberName().equals(member8.getMemberName()))).isTrue();
    assertThat(secondMemberList.stream()
        .anyMatch(member -> member.getMemberName().equals(member9.getMemberName()))).isTrue();
    assertThat(secondMemberList.stream()
        .anyMatch(member -> member.getMemberName().equals(member11.getMemberName()))).isTrue();
    assertThat(secondMemberList.stream()
        .anyMatch(member -> member.getMemberName().equals(member12.getMemberName()))).isTrue();
    assertThat(thirdMemberList.size()).isEqualTo(1);
    assertThat(thirdMemberList.get(0).getMemberName().equals(member14.getMemberName())).isTrue();
  }
}