package feedmysheep.feedmysheepapi.domain.member.app.service;

import static org.assertj.core.api.Assertions.*;

import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberReqDto;
import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberReqDto.sendVerificationCode;
import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
import feedmysheep.feedmysheepapi.domain.verification.app.repository.VerificationRepository;
import feedmysheep.feedmysheepapi.global.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.response.error.ErrorMessage;
import feedmysheep.feedmysheepapi.global.thirdparty.twilio.TwilioService;
import feedmysheep.feedmysheepapi.models.Member;
import feedmysheep.feedmysheepapi.models.Member.Sex;
import feedmysheep.feedmysheepapi.models.Verification;
import java.time.LocalDate;
import java.util.ArrayList;
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

  MemberServiceTest () {
    this.phone = "01088831954";
  }


  @BeforeEach
  public void setup() {
    TwilioService twilioServiceMock = Mockito.mock(TwilioService.class);
    this.memberService = new MemberService(this.memberRepository, this.verificationRepository,
        twilioServiceMock, maxCodeGenNum);
  }

  @Test
  @DisplayName("[sendVerificationCode] 이미 사용중인 휴대폰 번호일 때 에러를 뱉는다.")
  public void 이미사용중인휴대폰번호() {
    // given
    MemberReqDto.sendVerificationCode query = new MemberReqDto.sendVerificationCode(this.phone);
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
    // then
    assertThatThrownBy(() -> this.memberService.sendVerificationCode(query))
        .isInstanceOf(CustomException.class)
        .hasMessageContaining(ErrorMessage.PHONE_IN_USE);
  }

  @Test
  @DisplayName("[sendVerificationCode] 인증코드가 정상적으로 발송되고, 인증코드가 저장되었는지 확인")
  public void 인증코드정상발송() {
    // given
    MemberReqDto.sendVerificationCode query = new MemberReqDto.sendVerificationCode(this.phone);

    // when
    memberService.sendVerificationCode(query);

    //then
    List<Verification> verificationList = verificationRepository.findAll();
    Boolean isMatchedPhone = verificationList.stream().anyMatch(verification -> verification.getPhone().equals(phone));
    assertThat(isMatchedPhone).isEqualTo(true);
  }

  @Test
  @DisplayName("[sendVerificationCode] 해당 번호로 인증코드가 5회 이상 생성된 경우, 에러를 뱉는다.")
  public void 인증코드5회이상실패() {
    // given
    MemberReqDto.sendVerificationCode query = new MemberReqDto.sendVerificationCode(this.phone);
    LocalDate today = LocalDate.now();
    Verification verificationTest1 = Verification.builder()
        .phone(this.phone)
        .verificationCode("111111")
        .validDate(today)
        .build();
    Verification verificationTest2 = Verification.builder()
        .phone(this.phone)
        .verificationCode("222222")
        .validDate(today)
        .build();
    Verification verificationTest3 = Verification.builder()
        .phone(this.phone)
        .verificationCode("333333")
        .validDate(today)
        .build();
    Verification verificationTest4 = Verification.builder()
        .phone(this.phone)
        .verificationCode("444444")
        .validDate(today)
        .build();
    Verification verificationTest5 = Verification.builder()
        .phone(this.phone)
        .verificationCode("555555")
        .validDate(today)
        .build();
    List<Verification> verificationList = new ArrayList<>();
    verificationList.add(verificationTest1);
    verificationList.add(verificationTest2);
    verificationList.add(verificationTest3);
    verificationList.add(verificationTest4);
    verificationList.add(verificationTest5);
    this.verificationRepository.saveAll(verificationList);

    // when
    // then
    assertThatThrownBy(() -> this.memberService.sendVerificationCode(query))
        .isInstanceOf(CustomException.class)
        .hasMessageContaining(ErrorMessage.CODE_GEN_TODAY_EXCEEDED);
  }

  @Test
  @DisplayName("[checkVerificationCode] 휴대폰번호와 인증번호가 3분이내 존재할 경우")
  public void 인증번호3분이내성공() {
    // given
    LocalDate today = LocalDate.now();
    Verification verificationTest1 = Verification.builder()
        .phone(this.phone)
        .verificationCode("111111")
        .validDate(today)
        .build();
    this.verificationRepository.save(verificationTest1);
    // when
    MemberReqDto.checkVerificationCode query = new MemberReqDto.checkVerificationCode(this.phone, "111111");

    // then
    this.memberService.checkVerificationCode(query);
    this.memberRepository.


  }

  @Test
  @DisplayName("[checkVerificationCode] 인증번호가 전송된 휴대폰 번호가 존재하지 않을 경우 / 휴대폰번호와 인증번호가 3분이내 존재하지 않을 경우")
  public void ㅇ2() {
    // when
    // given
    // then
  }

  @Test
  @DisplayName("[checkVerificationCode] 오늘 해당 번호로 실패여부가 5번 이상일 경우 에러")
  public void ㅇ3() {
    // when
    // given
    // then
  }
}