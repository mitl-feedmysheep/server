package feedmysheep.feedmysheepapi.domain.member.app.service;

import static org.assertj.core.api.Assertions.*;

import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberReqDto;
import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
import feedmysheep.feedmysheepapi.domain.verification.app.repository.VerificationRepository;
import feedmysheep.feedmysheepapi.global.thirdparty.twilio.TwilioService;
import feedmysheep.feedmysheepapi.models.Member;
import feedmysheep.feedmysheepapi.models.Member.Sex;
import feedmysheep.feedmysheepapi.models.Verification;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@EnableConfigurationProperties
@AutoConfigureTestDatabase(replace = Replace.NONE)
class MemberServiceTest {
  @Autowired
  private VerificationRepository verificationRepository;

  @Autowired
  private MemberRepository memberRepository;

  private MemberService memberService;

  @Value("${verification.maxCodeGenNum}")
  private int maxCodeGenNum;

  private final String phone;
  private final MemberReqDto.sendVerificationCode query;

  MemberServiceTest () {
    this.phone = "01088831954";
    this.query = new MemberReqDto.sendVerificationCode(phone);
  }


  @BeforeEach
  public void setup() {
    this.query.setPhone(this.phone);
    TwilioService twilioServiceMock = Mockito.mock(TwilioService.class);
    this.memberService = new MemberService(this.memberRepository, this.verificationRepository,
        twilioServiceMock, maxCodeGenNum);
  }

  @Test
  @DisplayName("이미 사용중인 휴대폰 번호일 때 에러를 뱉는다.")
  public void 이미사용중인휴대폰번호() {
    // given
    Member testMember1 = Member.builder()
        .memberName("테스트멤버1")
        .sex(Sex.M)
        .birthday(LocalDate.parse("1991-09-16"))
        .phone(this.phone)
        .address("테스트 주소")
        .email("test@test.com")
        .build();
    this.memberRepository.save(testMember1);
    // when
    this.memberService.sendVerificationCode(this.query);

    // then

  }

  @Test
  @DisplayName("인증코드가 정상적으로 발송되고, 인증코드가 저장되었는지 확인")
  public void 인증코드정상발송() {
    // given

    // when
    memberService.sendVerificationCode(query);

    //then
    List<Verification> verificationList = verificationRepository.findAll();
    Boolean isMatchedPhone = verificationList.stream().anyMatch(verification -> verification.getPhone().equals(phone));
    assertThat(isMatchedPhone).isEqualTo(true);
  }

  @Test
  @DisplayName("해당 번호로 인증코드가 5회 이상 생성된 경우, 에러를 뱉는다.")
  public void 인증코드5회이상실패() {
    // given



    // when
    // then
  }
}