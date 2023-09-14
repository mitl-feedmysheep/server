package feedmysheep.feedmysheepapi.domain.member.app.service;

import static org.assertj.core.api.Assertions.*;

import feedmysheep.feedmysheepapi.TestData;
import feedmysheep.feedmysheepapi.domain.auth.app.repository.AuthorizationRepository;
import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberReqDto;
import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberReqDto.sendVerificationCode;
import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberReqDto.signUp;
import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
import feedmysheep.feedmysheepapi.domain.verification.app.repository.VerificationRepository;
import feedmysheep.feedmysheepapi.domain.verification.app.repository.VerificationFailLogRepository;
import feedmysheep.feedmysheepapi.global.utils.jwt.JwtTokenProvider;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;
import feedmysheep.feedmysheepapi.global.thirdparty.twilio.TwilioService;
import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import feedmysheep.feedmysheepapi.models.VerificationEntity;
import feedmysheep.feedmysheepapi.models.VerificationFailLogEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@DataJpaTest
@EnableConfigurationProperties
@AutoConfigureTestDatabase(replace = Replace.NONE)
class MemberServiceTest {
  @Autowired
  private VerificationRepository verificationRepository;
  @Autowired
  private VerificationFailLogRepository verificationFailLogRepository;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private AuthorizationRepository authorizationRepository;
  private MemberService memberService;
  @Value("${verification.maxCodeGenNum}")
  private int maxCodeGenNum;
  @Value("${verification.maxCodeTryNum}")
  private int maxCodeTryNum;
  private AuthorizationEntity authorization;

  MemberServiceTest() {}

  @BeforeEach
  public void setup() {
    TwilioService twilioServiceMock = Mockito.mock(TwilioService.class);
    this.memberService = new MemberService(
        this.memberRepository,
        this.verificationRepository,
        this.verificationFailLogRepository,
        this.authorizationRepository,
        twilioServiceMock,
        maxCodeGenNum,
        maxCodeTryNum,
        new BCryptPasswordEncoder(),
        new JwtTokenProvider()
    );
    this.authorization = this.authorizationRepository.findById(1L).orElseThrow();
  }

  @Test
  @DisplayName("[인증번호전송] 이미 사용중인 휴대폰 번호일 때 에러를 뱉는다.")
  public void 이미사용중인휴대폰번호() {
    // given
    MemberReqDto.sendVerificationCode query = new MemberReqDto.sendVerificationCode(TestData.phone);
    MemberEntity testMember1 = MemberEntity.builder()
        .memberName(TestData.memberName)
        .sex(TestData.sex)
        .birthday(TestData.birthday)
        .phone(TestData.phone)
        .address(TestData.address)
        .email(TestData.email)
        .password(TestData.password)
        .authorization(this.authorization)
        .build();
    this.memberRepository.save(testMember1);

    // when
    // then
    assertThatThrownBy(() -> this.memberService.sendVerificationCode(query))
        .hasMessageContaining(ErrorMessage.PHONE_IN_USE);
  }

  @Test
  @DisplayName("[인증번호전송] 인증코드가 정상적으로 발송되고, 인증코드가 저장되었는지 확인")
  public void 인증코드정상발송() {
    // given
    MemberReqDto.sendVerificationCode query = new MemberReqDto.sendVerificationCode(TestData.phone);

    // when
    memberService.sendVerificationCode(query);

    //then
    List<VerificationEntity> verificationList = verificationRepository.findAll();
    Boolean isMatchedPhone = verificationList.stream()
        .anyMatch(verification -> verification.getPhone().equals(TestData.phone));
    assertThat(isMatchedPhone).isEqualTo(true);
  }

  @Test
  @DisplayName("[인증번호전송] 인증오류횟수가 5회를 넘는 경우, 에러를 뱉는다.")
  public void 인증오류횟수5회초과() {
    // given
    MemberReqDto.sendVerificationCode query = new sendVerificationCode(TestData.phone);
    VerificationFailLogEntity testData1 = VerificationFailLogEntity.builder()
        .phone(TestData.phone)
        .verificationCode("111111")
        .build();
    VerificationFailLogEntity testData2 = VerificationFailLogEntity.builder()
        .phone(TestData.phone)
        .verificationCode("111111")
        .build();
    VerificationFailLogEntity testData3 = VerificationFailLogEntity.builder()
        .phone(TestData.phone)
        .verificationCode("111111")
        .build();
    VerificationFailLogEntity testData4 = VerificationFailLogEntity.builder()
        .phone(TestData.phone)
        .verificationCode("111111")
        .build();
    VerificationFailLogEntity testData5 = VerificationFailLogEntity.builder()
        .phone(TestData.phone)
        .verificationCode("111111")
        .build();
    this.verificationFailLogRepository.saveAll(
        List.of(testData1, testData2, testData3, testData4, testData5));

    // when
    // then
    assertThatThrownBy(() -> this.memberService.sendVerificationCode(query))
        .hasMessageContaining(ErrorMessage.FAIL_LOG_OVER_5_TRIES);

  }

  @Test
  @DisplayName("[인증번호전송] 해당 번호로 인증코드가 5회 이상 생성된 경우, 에러를 뱉는다.")
  public void 인증코드5회이상실패() {
    // given
    MemberReqDto.sendVerificationCode query = new MemberReqDto.sendVerificationCode(TestData.phone);
    LocalDate today = LocalDate.now();
    VerificationEntity verificationTest1 = VerificationEntity.builder()
        .phone(TestData.phone)
        .verificationCode("111111")
        .validDate(today)
        .build();
    VerificationEntity verificationTest2 = VerificationEntity.builder()
        .phone(TestData.phone)
        .verificationCode("222222")
        .validDate(today)
        .build();
    VerificationEntity verificationTest3 = VerificationEntity.builder()
        .phone(TestData.phone)
        .verificationCode("333333")
        .validDate(today)
        .build();
    VerificationEntity verificationTest4 = VerificationEntity.builder()
        .phone(TestData.phone)
        .verificationCode("444444")
        .validDate(today)
        .build();
    VerificationEntity verificationTest5 = VerificationEntity.builder()
        .phone(TestData.phone)
        .verificationCode("555555")
        .validDate(today)
        .build();
    List<VerificationEntity> verificationList = new ArrayList<>();
    verificationList.add(verificationTest1);
    verificationList.add(verificationTest2);
    verificationList.add(verificationTest3);
    verificationList.add(verificationTest4);
    verificationList.add(verificationTest5);
    this.verificationRepository.saveAll(verificationList);

    // when
    // then
    assertThatThrownBy(() -> this.memberService.sendVerificationCode(query))
        .hasMessageContaining(ErrorMessage.CODE_GEN_TODAY_EXCEEDED);
  }

  @Test
  @DisplayName("[인증번호검증] 이미 사용중인 휴대폰 번호일 때 에러를 뱉는다.")
  public void 이미사용중인휴대폰번호2() {
    // given
    MemberReqDto.checkVerificationCode query = new MemberReqDto.checkVerificationCode(TestData.phone,
        "111111");
    MemberEntity testMember1 = MemberEntity.builder()
        .memberName(TestData.memberName)
        .sex(TestData.sex)
        .birthday(TestData.birthday)
        .phone(TestData.phone)
        .address(TestData.address)
        .email(TestData.email)
        .password(TestData.password)
        .authorization(this.authorization)
        .build();
    this.memberRepository.save(testMember1);

    // when
    // then
    assertThatThrownBy(() -> this.memberService.checkVerificationCode(query))
        .hasMessageContaining(ErrorMessage.PHONE_IN_USE);
  }

  @Test
  @DisplayName("[인증번호검증] 휴대폰번호와 인증번호가 3분이내 존재할 경우")
  public void 인증번호3분이내성공() {
    // given
    LocalDate today = LocalDate.now();
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime twoMinAgo = now.minusMinutes(2);
    String verificationCode = "111111";
    VerificationEntity verificationTest1 = VerificationEntity.builder()
        .phone(TestData.phone)
        .verificationCode(verificationCode)
        .validDate(today)
        .build();
    verificationTest1.setCreatedAt(twoMinAgo);
    this.verificationRepository.save(verificationTest1);

    // when
    MemberReqDto.checkVerificationCode query = new MemberReqDto.checkVerificationCode(TestData.phone,
        verificationCode);
    this.memberService.checkVerificationCode(query);

    // then
    // nothing happens
  }

  @Test
  @DisplayName("[인증번호검증] 인증번호가 전송된 휴대폰 번호가 존재하지 않을 경우 / 휴대폰번호와 인증번호가 3분이내 존재하지 않을 경우")
  public void 휴대폰번호와인증번호가없는경우() {
    // given
    String verificationCode = "111111";

    // when
    MemberReqDto.checkVerificationCode query = new MemberReqDto.checkVerificationCode(TestData.phone,
        verificationCode);

    // then
    assertThatThrownBy(() -> this.memberService.checkVerificationCode(query))
        .hasMessageContaining(ErrorMessage.NO_VERIFICATION_CODE);
  }

  @Test
  @DisplayName("[인증번호검증] 오늘 해당 번호로 실패여부가 5번 이상일 경우 에러")
  public void 인증실패여부5회이상() {
    // given
    VerificationFailLogEntity testData1 = VerificationFailLogEntity.builder()
        .phone(TestData.phone)
        .verificationCode("111111")
        .build();
    VerificationFailLogEntity testData2 = VerificationFailLogEntity.builder()
        .phone(TestData.phone)
        .verificationCode("111111")
        .build();
    VerificationFailLogEntity testData3 = VerificationFailLogEntity.builder()
        .phone(TestData.phone)
        .verificationCode("111111")
        .build();
    VerificationFailLogEntity testData4 = VerificationFailLogEntity.builder()
        .phone(TestData.phone)
        .verificationCode("111111")
        .build();
    VerificationFailLogEntity testData5 = VerificationFailLogEntity.builder()
        .phone(TestData.phone)
        .verificationCode("111111")
        .build();
    this.verificationFailLogRepository.saveAll(
        List.of(testData1, testData2, testData3, testData4, testData5));

    // when
    MemberReqDto.checkVerificationCode query = new MemberReqDto.checkVerificationCode(TestData.phone,
        "123123");

    // then
    assertThatThrownBy(() -> this.memberService.checkVerificationCode(query))
        .hasMessageContaining(ErrorMessage.FAIL_LOG_OVER_5_TRIES);
  }

  @Test
  @DisplayName("[이메일중복여부] 이메일 중복 여부 확인 - 중복O")
  public void 이메일중복O() {
    // given
    String testEmail = "test@test.com";
    MemberReqDto.checkEmailDuplication query = new MemberReqDto.checkEmailDuplication(testEmail);
    MemberEntity testMember1 = MemberEntity.builder()
        .memberName(TestData.memberName)
        .sex(TestData.sex)
        .birthday(TestData.birthday)
        .phone(TestData.phone)
        .address(TestData.address)
        .email(TestData.email)
        .password(TestData.password)
        .authorization(this.authorization)
        .build();
    this.memberRepository.save(testMember1);
    // when
    // then
    assertThatThrownBy(() -> this.memberService.checkEmailDuplication(query)).hasMessageContaining(ErrorMessage.EMAIL_DUPLICATED);
  }

  @Test
  @DisplayName("[이메일중복여부] 이메일 중복 여부 확인 - 중복X")
  public void 이메일중복X() {
    // given
    MemberReqDto.checkEmailDuplication query = new MemberReqDto.checkEmailDuplication("test@test.com");
    // when
    this.memberService.checkEmailDuplication(query);
    // then
    // nothing happens
  }

  @Test
  @DisplayName("[이메일중복여부] 이메일 중복 여부 확인 - 중복X (비활성화된 유저인 경우)")
  public void 이메일중복X2() {
    // given
    String testEmail = "test@test.com";
    MemberReqDto.checkEmailDuplication query = new MemberReqDto.checkEmailDuplication(testEmail);
    MemberEntity testMember1 = MemberEntity.builder()
        .memberName("테스트멤버1")
        .sex("M")
        .birthday(LocalDate.parse("1991-09-16"))
        .phone(TestData.phone)
        .address("테스트 주소")
        .email(testEmail)
        .password("testPassword")
        .authorization(this.authorization)
        .build();
    testMember1.setActive(false);
    this.memberRepository.save(testMember1);
    // when
    this.memberService.checkEmailDuplication(query);
    // then
    // nothing happens
  }
  
  @Test
  @DisplayName("[회원가입] 멤버 저장 확인")
  public void 회원가입() {
    // given
    MemberReqDto.signUp body = new MemberReqDto.signUp(TestData.memberName, TestData.sex, TestData.birthday, TestData.phone, TestData.email, TestData.password, TestData.address);
    // TODO 여기서부터 service 검증하면 됨!
    String phone = "01088831954";
    String email = "kcs19542001@gmail.com";
    MemberEntity memberToSave = MemberEntity.builder()
        .memberName(TestData.memberName)
        .sex(TestData.sex)
        .birthday(TestData.birthday)
        .phone(TestData.phone)
        .address(TestData.address)
        .email(TestData.email)
        .password(TestData.password)
        .authorization(this.authorization)
        .build();
    MemberEntity testMember = this.memberRepository.save(memberToSave);
    // when
    Optional<MemberEntity> member = this.memberRepository.findById(testMember.getMemberId());

    // then
    MemberEntity validMember = member.orElseThrow();
    assertThat(validMember.getPhone()).isEqualTo(phone);
    assertThat(validMember.getEmail()).isEqualTo(email);
  }
}