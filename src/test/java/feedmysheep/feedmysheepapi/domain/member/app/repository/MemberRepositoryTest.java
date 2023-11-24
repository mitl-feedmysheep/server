package feedmysheep.feedmysheepapi.domain.member.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.domain.auth.app.repository.AuthorizationRepository;
import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
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


  @Test
  @DisplayName("멤버 아이디로 유효한 멤버 조회 가능")
  void test1() {
    // given

    // when
    Optional<MemberEntity> validMember = this.memberRepository.getMemberByMemberId(
        validMember1.getMemberId());
    Optional<MemberEntity> invalidMemberNotExists = this.memberRepository.getMemberByMemberId(
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
    Optional<MemberEntity> validMember = this.memberRepository.getMemberByEmail(
        validMember1.getEmail());
    Optional<MemberEntity> invalidMemberNotExists = this.memberRepository.getMemberByEmail(
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
    Optional<MemberEntity> validMember = this.memberRepository.getMemberByPhone(
        validMember1.getPhone());
    Optional<MemberEntity> invalidMemberNotExists = this.memberRepository.getMemberByPhone(
        invalidMember1.getPhone());

    // then
    assertThat(validMember).isPresent();
    assertThat(invalidMemberNotExists).isNotPresent();
  }
}