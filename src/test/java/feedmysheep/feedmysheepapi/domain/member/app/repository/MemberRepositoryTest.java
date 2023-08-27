package feedmysheep.feedmysheepapi.domain.member.app.repository;

import static org.assertj.core.api.Assertions.*;

import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberResDto;
import feedmysheep.feedmysheepapi.models.Member;
import feedmysheep.feedmysheepapi.models.Member.Sex;
import java.time.LocalDate;
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

  @BeforeEach
  public void setup() {
    Member testMember1 =  Member.builder()
        .memberName("testMember")
        .sex(Sex.M)
        .birthday(LocalDate.parse("2000-01-01"))
        .phone("01011112222")
        .address("Test address does not need much")
        .email("random@random.com")
        .build();
    memberRepository.save(testMember1);
  }

  @Test
  @DisplayName("휴대폰 중복 멤버 여부 확인 - 중복O")
  public void checkPhoneDuplication1() {
    Boolean isExistingPhone = memberRepository.existsMemberByPhone("01011112222");
    assertThat(isExistingPhone).isEqualTo(true);
  }

  @Test
  @DisplayName("휴대폰 중복 멤버 여부 확인 - 중복X")
  public void checkPhoneDuplication2() {
    Boolean isExistingPhone = memberRepository.existsMemberByPhone("01022223333");
    assertThat(isExistingPhone).isEqualTo(false);
  }

}