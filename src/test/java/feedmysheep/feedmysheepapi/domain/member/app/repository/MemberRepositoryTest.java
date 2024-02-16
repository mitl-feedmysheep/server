package feedmysheep.feedmysheepapi.domain.member.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.domain.auth.app.repository.AuthorizationRepository;
import feedmysheep.feedmysheepapi.global.config.TestConfig;
import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
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
class MemberRepositoryTest {

  @Autowired
  private AuthorizationRepository authorizationRepository;

  @Autowired
  private MemberRepository memberRepository;

  static AuthorizationEntity authorization1;
  static MemberEntity validMember1;
  static MemberEntity invalidMember1;

  @BeforeAll
  public static void setup(@Autowired AuthorizationRepository authorizationRepository,
      @Autowired MemberRepository memberRepository) {
    authorization1 = authorizationRepository.save(DataFactory.createAuthorization());
    validMember1 = memberRepository.save(DataFactory.createMember(
        authorization1.getAuthorizationId()));
    MemberEntity invalidMember = DataFactory.createMember(authorization1.getAuthorizationId());
    invalidMember.setActive(false);
    invalidMember1 = memberRepository.save(invalidMember);
  }

  @AfterAll
  public static void cleanup(@Autowired AuthorizationRepository authorizationRepository,
      @Autowired MemberRepository memberRepository) {
    authorizationRepository.deleteAll();
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
    Optional<MemberEntity> validMember = this.memberRepository.findByEmail(
        validMember1.getEmail());
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
    Optional<MemberEntity> validMember = this.memberRepository.findByPhone(
        validMember1.getPhone());
    Optional<MemberEntity> invalidMemberNotExists = this.memberRepository.findByPhone(
        invalidMember1.getPhone());

    // then
    assertThat(validMember).isPresent();
    assertThat(invalidMemberNotExists).isNotPresent();
  }
}