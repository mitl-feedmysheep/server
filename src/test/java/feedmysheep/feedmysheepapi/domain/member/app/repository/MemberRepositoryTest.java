package feedmysheep.feedmysheepapi.domain.member.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.TESTDATA;
import feedmysheep.feedmysheepapi.domain.auth.app.repository.AuthorizationRepository;
import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
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
  private MemberRepository memberRepository;

  @Autowired
  private AuthorizationRepository authorizationRepository;

  private Long testMemberId;

  @BeforeEach
  public void setup() {
    AuthorizationEntity authorization = this.authorizationRepository.findById(1L).orElseThrow();
    MemberEntity testMember1 = MemberEntity.builder()
        .memberName(TESTDATA.memberName)
        .sex(TESTDATA.sex)
        .birthday(TESTDATA.birthday)
        .phone(TESTDATA.phone)
        .address(TESTDATA.address)
        .email(TESTDATA.email)
        .password(TESTDATA.password)
        .authorization(authorization)
        .build();
    MemberEntity testMember = this.memberRepository.save(testMember1);
    testMemberId = testMember.getMemberId();
  }

  @Test
  @DisplayName("휴대폰 중복 멤버 여부 확인 - 중복O")
  public void checkPhoneDuplication1() {
    boolean isExistingPhone = this.memberRepository.existsMemberByPhone(TESTDATA.phone);
    assertThat(isExistingPhone).isEqualTo(true);
  }

  @Test
  @DisplayName("휴대폰 중복 멤버 여부 확인 - 중복X")
  public void checkPhoneDuplication2() {
    boolean isExistingPhone = this.memberRepository.existsMemberByPhone("01022223333");
    assertThat(isExistingPhone).isEqualTo(false);
  }

  @Test
  @DisplayName("이메일 중복 멤버 여부 확인 - 중복O")
  public void existsMemberByEmail1() {
    boolean isExistingEmail = this.memberRepository.existsMemberByEmail(TESTDATA.email);
    assertThat(isExistingEmail).isEqualTo(true);
  }

  @Test
  @DisplayName("이메일 중복 멤버 여부 확인 - 중복X")
  public void existsMemberByEmail2() {
    boolean isExistingEmail = this.memberRepository.existsMemberByEmail("r@random.com");
    assertThat(isExistingEmail).isEqualTo(false);
  }

  @Test
  @DisplayName("멤버 아이디로 멤버 찾기")
  public void getMemberByMemberId() {
    List<MemberEntity> memberList = this.memberRepository.findAll();
    MemberEntity validMember1 = memberList.stream()
        .filter(memberEntity -> memberEntity.getPhone().equals(
            TESTDATA.phone)).findFirst().orElseThrow();
    MemberEntity validMember2 = this.memberRepository.getMemberByMemberId(
        validMember1.getMemberId()).orElseThrow();
    assertThat(validMember1).isEqualTo(validMember2);
  }

  @Test
  @DisplayName("멤버 아이디로 멤버 유효 여부 O")
  public void 멤버아이디로멤버유효여부O() {
    // given

    // when
    boolean isValidMember = this.memberRepository.existsMemberByMemberId(testMemberId);

    // then
    assertThat(isValidMember).isEqualTo(true);
  }

  @Test
  @DisplayName("멤버 아이디로 멤버 유효 여부 X")
  public void 멤버아이디로멤버유효여부X() {
    // given

    // when
    boolean isValidMember = this.memberRepository.existsMemberByMemberId(0L);

    // then
    assertThat(isValidMember).isEqualTo(false);
  }

}