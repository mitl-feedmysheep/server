//package feedmysheep.feedmysheepapi.domain.member.app.service;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//import feedmysheep.feedmysheepapi.TESTDATA;
//import feedmysheep.feedmysheepapi.domain.auth.app.repository.AuthorizationRepository;
//import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberReqDto;
//import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberReqDto.sendVerificationCode;
//import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
//import feedmysheep.feedmysheepapi.domain.verification.app.repository.VerificationFailLogRepository;
//import feedmysheep.feedmysheepapi.domain.verification.app.repository.VerificationRepository;
//import feedmysheep.feedmysheepapi.global.thirdparty.twilio.TwilioService;
//import feedmysheep.feedmysheepapi.global.utils.jwt.JwtTokenProvider;
//import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
//import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;
//import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
//import feedmysheep.feedmysheepapi.models.MemberEntity;
//import feedmysheep.feedmysheepapi.models.VerificationEntity;
//import feedmysheep.feedmysheepapi.models.VerificationFailLogEntity;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.transaction.annotation.Transactional;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//class MemberServiceTestDeprecated {
//
//  @Autowired
//  private VerificationRepository verificationRepository;
//  @Autowired
//  private VerificationFailLogRepository verificationFailLogRepository;
//  @Autowired
//  private MemberRepository memberRepository;
//  @Autowired
//  private AuthorizationRepository authorizationRepository;
//  private MemberService memberService;
//  private AuthorizationEntity authorization;
//
//  MemberServiceTestDeprecated() {
//  }
//
//  @BeforeAll
//  public static void setup() {
//    TwilioService twilioServiceMock = Mockito.mock(TwilioService.class);
//    memberService = new MemberService(
//        this.memberRepository,
//        this.verificationRepository,
//        this.verificationFailLogRepository,
//        this.authorizationRepository,
//        twilioServiceMock,
//        new BCryptPasswordEncoder(),
//        new JwtTokenProvider()
//    );
//    this.authorization = this.authorizationRepository.findById(1L)
//        .orElseThrow(() -> new CustomException(ErrorMessage.NO_AUTHORIZATION));
//  }
//
//  @Test
//  @Transactional
//  @DisplayName("[인증번호전송] 이미 사용중인 휴대폰 번호일 때 에러를 뱉는다.")
//  public void 이미사용중인휴대폰번호() {
//    // given
//    MemberReqDto.sendVerificationCode query = new MemberReqDto.sendVerificationCode(TESTDATA.phone);
//    MemberEntity testMember1 = MemberEntity.builder()
//        .memberName(TESTDATA.memberName)
//        .sex(TESTDATA.sex)
//        .birthday(TESTDATA.birthday)
//        .phone(TESTDATA.phone)
//        .address(TESTDATA.address)
//        .email(TESTDATA.email)
//        .password(TESTDATA.password)
//        .authorization(this.authorization)
//        .build();
//    this.memberRepository.save(testMember1);
//
//    // when
//    // then
//    assertThatThrownBy(() -> this.memberService.sendVerificationCode(query))
//        .hasMessageContaining(ErrorMessage.PHONE_IN_USE);
//  }
//
//  @Test
//  @Transactional
//  @DisplayName("[인증번호전송] 인증코드가 정상적으로 발송되고, 인증코드가 저장되었는지 확인")
//  public void 인증코드정상발송() {
//    // given
//    MemberReqDto.sendVerificationCode query = new MemberReqDto.sendVerificationCode(TESTDATA.phone);
//
//    // when
//    memberService.sendVerificationCode(query);
//
//    //then
//    List<VerificationEntity> verificationList = verificationRepository.findAll();
//    Boolean isMatchedPhone = verificationList.stream()
//        .anyMatch(verification -> verification.getPhone().equals(TESTDATA.phone));
//    assertThat(isMatchedPhone).isEqualTo(true);
//  }
//
//  @Test
//  @DisplayName("[인증번호전송] 인증오류횟수가 5회를 넘는 경우, 에러를 뱉는다.")
//  public void 인증오류횟수5회초과() {
//    // given
//    MemberReqDto.sendVerificationCode query = new sendVerificationCode(TESTDATA.phone);
//    VerificationFailLogEntity testData1 = VerificationFailLogEntity.builder()
//        .phone(TESTDATA.phone)
//        .verificationCode("111111")
//        .build();
//    VerificationFailLogEntity testData2 = VerificationFailLogEntity.builder()
//        .phone(TESTDATA.phone)
//        .verificationCode("111111")
//        .build();
//    VerificationFailLogEntity testData3 = VerificationFailLogEntity.builder()
//        .phone(TESTDATA.phone)
//        .verificationCode("111111")
//        .build();
//    VerificationFailLogEntity testData4 = VerificationFailLogEntity.builder()
//        .phone(TESTDATA.phone)
//        .verificationCode("111111")
//        .build();
//    VerificationFailLogEntity testData5 = VerificationFailLogEntity.builder()
//        .phone(TESTDATA.phone)
//        .verificationCode("111111")
//        .build();
//    this.verificationFailLogRepository.saveAll(
//        List.of(testData1, testData2, testData3, testData4, testData5));
//
//    // when
//    // then
//    assertThatThrownBy(() -> this.memberService.sendVerificationCode(query))
//        .hasMessageContaining(ErrorMessage.FAIL_LOG_OVER_5_TRIES);
//
//  }
//
//  @Test
//  @DisplayName("[인증번호전송] 해당 번호로 인증코드가 5회 이상 생성된 경우, 에러를 뱉는다.")
//  public void 인증코드5회이상실패() {
//    // given
//    MemberReqDto.sendVerificationCode query = new MemberReqDto.sendVerificationCode(TESTDATA.phone);
//    LocalDate today = LocalDate.now();
//    VerificationEntity verificationTest1 = VerificationEntity.builder()
//        .phone(TESTDATA.phone)
//        .verificationCode("111111")
//        .validDate(today)
//        .build();
//    VerificationEntity verificationTest2 = VerificationEntity.builder()
//        .phone(TESTDATA.phone)
//        .verificationCode("222222")
//        .validDate(today)
//        .build();
//    VerificationEntity verificationTest3 = VerificationEntity.builder()
//        .phone(TESTDATA.phone)
//        .verificationCode("333333")
//        .validDate(today)
//        .build();
//    VerificationEntity verificationTest4 = VerificationEntity.builder()
//        .phone(TESTDATA.phone)
//        .verificationCode("444444")
//        .validDate(today)
//        .build();
//    VerificationEntity verificationTest5 = VerificationEntity.builder()
//        .phone(TESTDATA.phone)
//        .verificationCode("555555")
//        .validDate(today)
//        .build();
//    List<VerificationEntity> verificationList = new ArrayList<>();
//    verificationList.add(verificationTest1);
//    verificationList.add(verificationTest2);
//    verificationList.add(verificationTest3);
//    verificationList.add(verificationTest4);
//    verificationList.add(verificationTest5);
//    this.verificationRepository.saveAll(verificationList);
//
//    // when
//    // then
//    assertThatThrownBy(() -> this.memberService.sendVerificationCode(query))
//        .hasMessageContaining(ErrorMessage.CODE_GEN_TODAY_EXCEEDED);
//  }
//
//  @Test
//  @DisplayName("[인증번호검증] 이미 사용중인 휴대폰 번호일 때 에러를 뱉는다.")
//  public void 이미사용중인휴대폰번호2() {
//    // given
//    MemberReqDto.checkVerificationCode query = new MemberReqDto.checkVerificationCode(
//        TESTDATA.phone,
//        "111111");
//    MemberEntity testMember1 = MemberEntity.builder()
//        .memberName(TESTDATA.memberName)
//        .sex(TESTDATA.sex)
//        .birthday(TESTDATA.birthday)
//        .phone(TESTDATA.phone)
//        .address(TESTDATA.address)
//        .email(TESTDATA.email)
//        .password(TESTDATA.password)
//        .authorization(this.authorization)
//        .build();
//    this.memberRepository.save(testMember1);
//
//    // when
//    // then
//    assertThatThrownBy(() -> this.memberService.checkVerificationCode(query))
//        .hasMessageContaining(ErrorMessage.PHONE_IN_USE);
//  }
//
//  @Test
//  @DisplayName("[인증번호검증] 휴대폰번호와 인증번호가 3분이내 존재할 경우")
//  public void 인증번호3분이내성공() {
//    // given
//    LocalDate today = LocalDate.now();
//    LocalDateTime now = LocalDateTime.now();
//    LocalDateTime twoMinAgo = now.minusMinutes(2);
//    String verificationCode = "111111";
//    VerificationEntity verificationTest1 = VerificationEntity.builder()
//        .phone(TESTDATA.phone)
//        .verificationCode(verificationCode)
//        .validDate(today)
//        .build();
//    verificationTest1.setCreatedAt(twoMinAgo);
//    this.verificationRepository.save(verificationTest1);
//
//    // when
//    MemberReqDto.checkVerificationCode query = new MemberReqDto.checkVerificationCode(
//        TESTDATA.phone,
//        verificationCode);
//    this.memberService.checkVerificationCode(query);
//
//    // then
//    // nothing happens
//  }
//
//  @Test
//  @DisplayName("[인증번호검증] 인증번호가 전송된 휴대폰 번호가 존재하지 않을 경우 / 휴대폰번호와 인증번호가 3분이내 존재하지 않을 경우")
//  public void 휴대폰번호와인증번호가없는경우() {
//    // given
//    String verificationCode = "111111";
//
//    // when
//    MemberReqDto.checkVerificationCode query = new MemberReqDto.checkVerificationCode(
//        TESTDATA.phone,
//        verificationCode);
//
//    // then
//    assertThatThrownBy(() -> this.memberService.checkVerificationCode(query))
//        .hasMessageContaining(ErrorMessage.NO_VERIFICATION_CODE);
//  }
//
//  @Test
//  @DisplayName("[인증번호검증] 오늘 해당 번호로 실패여부가 5번 이상일 경우 에러")
//  public void 인증실패여부5회이상() {
//    // given
//    VerificationFailLogEntity testData1 = VerificationFailLogEntity.builder()
//        .phone(TESTDATA.phone)
//        .verificationCode("111111")
//        .build();
//    VerificationFailLogEntity testData2 = VerificationFailLogEntity.builder()
//        .phone(TESTDATA.phone)
//        .verificationCode("111111")
//        .build();
//    VerificationFailLogEntity testData3 = VerificationFailLogEntity.builder()
//        .phone(TESTDATA.phone)
//        .verificationCode("111111")
//        .build();
//    VerificationFailLogEntity testData4 = VerificationFailLogEntity.builder()
//        .phone(TESTDATA.phone)
//        .verificationCode("111111")
//        .build();
//    VerificationFailLogEntity testData5 = VerificationFailLogEntity.builder()
//        .phone(TESTDATA.phone)
//        .verificationCode("111111")
//        .build();
//    this.verificationFailLogRepository.saveAll(
//        List.of(testData1, testData2, testData3, testData4, testData5));
//
//    // when
//    MemberReqDto.checkVerificationCode query = new MemberReqDto.checkVerificationCode(
//        TESTDATA.phone,
//        "123123");
//
//    // then
//    assertThatThrownBy(() -> this.memberService.checkVerificationCode(query))
//        .hasMessageContaining(ErrorMessage.FAIL_LOG_OVER_5_TRIES);
//  }
//
//  @Test
//  @DisplayName("[이메일중복여부] 이메일 중복 여부 확인 - 중복O")
//  public void 이메일중복O() {
//    // given
//    MemberReqDto.checkEmailDuplication query = new MemberReqDto.checkEmailDuplication(
//        TESTDATA.email);
//    MemberEntity testMember1 = MemberEntity.builder()
//        .memberName(TESTDATA.memberName)
//        .sex(TESTDATA.sex)
//        .birthday(TESTDATA.birthday)
//        .phone(TESTDATA.phone)
//        .address(TESTDATA.address)
//        .email(TESTDATA.email)
//        .password(TESTDATA.password)
//        .authorization(this.authorization)
//        .build();
//    this.memberRepository.save(testMember1);
//    // when
//    // then
//    assertThatThrownBy(() -> this.memberService.checkEmailDuplication(query)).hasMessageContaining(
//        ErrorMessage.EMAIL_DUPLICATED);
//  }
//
//  @Test
//  @DisplayName("[이메일중복여부] 이메일 중복 여부 확인 - 중복X")
//  public void 이메일중복X() {
//    // given
//    MemberReqDto.checkEmailDuplication query = new MemberReqDto.checkEmailDuplication(
//        "test@test.com");
//    // when
//    this.memberService.checkEmailDuplication(query);
//    // then
//    // nothing happens
//  }
//
//  @Test
//  @DisplayName("[이메일중복여부] 이메일 중복 여부 확인 - 중복X (비활성화된 유저인 경우)")
//  public void 이메일중복X2() {
//    // given
//    String testEmail = "test@test.com";
//    MemberReqDto.checkEmailDuplication query = new MemberReqDto.checkEmailDuplication(testEmail);
//    MemberEntity testMember1 = MemberEntity.builder()
//        .memberName("테스트멤버1")
//        .sex("M")
//        .birthday(LocalDate.parse("1991-09-16"))
//        .phone(TESTDATA.phone)
//        .address("테스트 주소")
//        .email(testEmail)
//        .password("testPassword")
//        .authorization(this.authorization)
//        .build();
//    testMember1.setActive(false);
//    this.memberRepository.save(testMember1);
//
//    // when
//    this.memberService.checkEmailDuplication(query);
//
//    // then
//    // nothing happens
//  }
//
//  @Test
//  @DisplayName("[회원가입] 멤버 저장 확인")
//  public void 회원가입() {
//    // given
//    MemberReqDto.signUp body = new MemberReqDto.signUp(TESTDATA.memberName, TESTDATA.sex,
//        TESTDATA.birthday, TESTDATA.phone, TESTDATA.email, TESTDATA.password, TESTDATA.address);
//
//    // when
//    this.memberService.signUp(body);
//
//    // then
//    List<MemberEntity> memberList = this.memberRepository.findAll();
//    Optional<MemberEntity> validMember = memberList.stream()
//        .filter(member -> member.getPhone().equals(TESTDATA.phone) && member.getEmail().equals(
//            TESTDATA.email))
//        .findFirst();
//
//    assertThat(validMember).isNotNull();
//  }
//
//  @Test
//  @DisplayName("[회원가입] 멤버 저장 실패 - 휴대폰 번호 중복")
//  public void 회원가입_휴대폰번호중복() {
//    // given
//    MemberReqDto.signUp body1 = new MemberReqDto.signUp(TESTDATA.memberName, TESTDATA.sex,
//        TESTDATA.birthday, TESTDATA.phone, TESTDATA.email, TESTDATA.password, TESTDATA.address);
//    MemberReqDto.signUp body2 = new MemberReqDto.signUp(TESTDATA.memberName, TESTDATA.sex,
//        TESTDATA.birthday, TESTDATA.phone, TESTDATA.email, TESTDATA.password, TESTDATA.address);
//
//    // when
//    this.memberService.signUp(body1);
//
//    // then
//    assertThatThrownBy(() -> this.memberService.signUp(body2)).hasMessageContaining(
//        ErrorMessage.PHONE_IN_USE);
//  }
//
//  @Test
//  @DisplayName("[회원가입] 멤버 저장 실패 - 이메일 중복")
//  public void 회원가입_이메일중복() {
//    // given
//    MemberReqDto.signUp body1 = new MemberReqDto.signUp(TESTDATA.memberName, TESTDATA.sex,
//        TESTDATA.birthday, "01011112222", "duplicatedEmail@email.com", TESTDATA.password,
//        TESTDATA.address);
//    MemberReqDto.signUp body2 = new MemberReqDto.signUp(TESTDATA.memberName, TESTDATA.sex,
//        TESTDATA.birthday, "01011113333", "duplicatedEmail@email.com", TESTDATA.password,
//        TESTDATA.address);
//    // when
//    this.memberService.signUp(body1);
//
//    // then
//    assertThatThrownBy(() -> this.memberService.signUp(body2)).hasMessageContaining(
//        ErrorMessage.EMAIL_DUPLICATED);
//  }
//}