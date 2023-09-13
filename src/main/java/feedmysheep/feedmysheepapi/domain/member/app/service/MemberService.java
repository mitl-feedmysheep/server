package feedmysheep.feedmysheepapi.domain.member.app.service;

import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberReqDto;
import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberResDto;
import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
import feedmysheep.feedmysheepapi.domain.verification.app.repository.VerificationRepository;
import feedmysheep.feedmysheepapi.domain.verification.app.repository.VerificationFailLogRepository;
import feedmysheep.feedmysheepapi.global.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.response.error.ErrorMessage;
import feedmysheep.feedmysheepapi.global.thirdparty.twilio.TwilioService;
import feedmysheep.feedmysheepapi.models.VerificationEntity;
import feedmysheep.feedmysheepapi.models.VerificationFailLogEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
  private final MemberRepository memberRepository;
  private final VerificationRepository verificationRepository;

  private final VerificationFailLogRepository verificationFailLogRepository;
  private final TwilioService twilioService;
  private final int maxCodeGenNum;
  private final int maxCodeTryNum;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public MemberService(
      MemberRepository memberRepository,
      VerificationRepository verificationRepository,
      VerificationFailLogRepository verificationFailLogRepository,
      TwilioService twilioService,
      @Value("${verification.maxCodeGenNum}") int maxCodeGenNum,
      @Value("${verification.maxCodeTryNum}") int maxCodeTryNum,
      PasswordEncoder passwordEncoder
  ) {
    this.memberRepository = memberRepository;
    this.verificationRepository = verificationRepository;
    this.verificationFailLogRepository = verificationFailLogRepository;
    this.twilioService = twilioService;
    this.maxCodeGenNum = maxCodeGenNum;
    this.maxCodeTryNum = maxCodeTryNum;
    this.passwordEncoder = passwordEncoder;
  };

  public void sendVerificationCode(MemberReqDto.sendVerificationCode query) {
    String phone = query.getPhone();
    LocalDate today = LocalDate.now();
    LocalDateTime startOfToday = LocalDate.now().atTime(LocalTime.MIN);
    LocalDateTime endOfToday = LocalDate.now().atTime(LocalTime.MAX);

    // 1. 휴대폰 사용 여부 체크
    boolean isDuplicated = this.memberRepository.existsMemberByPhone(phone);
    if (isDuplicated) throw new CustomException(ErrorMessage.PHONE_IN_USE);

    // 2. FailLog 5회 이상 여부 체크
    int failCount = this.verificationFailLogRepository.countByPhoneAndCreatedAtBetween(phone, startOfToday, endOfToday);
    System.out.println("failCount" + failCount);
    if (failCount >= 5) throw new CustomException(ErrorMessage.FAIL_LOG_OVER_5_TRIES);

    // 3. 인증코드 발급 5회 미만 여부 체크
    int usedCount = this.verificationRepository.countByPhoneAndValidDate(phone, today);
    if (usedCount >= this.maxCodeGenNum) throw new CustomException(ErrorMessage.CODE_GEN_TODAY_EXCEEDED);

    // 4. 인증코드 generate
    Random random = new Random();
    int min = 100000;
    int max = 999999;
    String verificationCode = Integer.toString(random.nextInt(max - min + 1) + min);

    // 5. 인증코드 전송
    String phoneWithCountry = "+" + "82" + phone;
    String messageBody = "[피마쉽(FeedMySheep)] 인증번호는 " + verificationCode + "입니다.";
    try {
      this.twilioService.sendSMS(phoneWithCountry, messageBody);
    } catch (Exception e) {
      System.out.println(e);
      // TODO 슬랙 메시지

      // 문자 메시지 전송 에러
      throw new CustomException(e.getMessage());
    }

    // 6. 인증코드 DB 저장
    VerificationEntity verification = VerificationEntity.builder()
      .phone(phone)
      .verificationCode(verificationCode)
      .validDate(today)
      .build();

    this.verificationRepository.save(verification);
  }

  public void checkVerificationCode(MemberReqDto.checkVerificationCode query) {
    String phone = query.getPhone();
    String code = query.getCode();
    LocalDateTime startOfToday = LocalDate.now().atTime(LocalTime.MIN);
    LocalDateTime endOfToday = LocalDate.now().atTime(LocalTime.MAX);

    // 1. 휴대폰 사용 여부 체크
    boolean isDuplicated = this.memberRepository.existsMemberByPhone(phone);
    if (isDuplicated) throw new CustomException(ErrorMessage.PHONE_IN_USE);

    // 2. 금일 인증실패 5회 여부 체크
    int failCount = this.verificationFailLogRepository.countByPhoneAndCreatedAtBetween(phone, startOfToday, endOfToday);
    if (failCount >= maxCodeTryNum) throw new CustomException(ErrorMessage.FAIL_LOG_OVER_5_TRIES);

    // 3. 휴대폰 번호와 인증코드 여부 체크
    Optional<VerificationEntity> optionalVerificationEntity = this.verificationRepository.findByPhoneAndVerificationCode(phone, code);
    VerificationEntity verification = optionalVerificationEntity.orElseThrow(() -> new CustomException(ErrorMessage.NO_VERIFICATION_CODE));
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime threeMinLater = verification.getCreatedAt().plusMinutes(3);
    // - 존재하지 않는다면, 인증실패 저장 후 재시도 요청
    if (now.isAfter(threeMinLater)) {
      VerificationFailLogEntity failLog = VerificationFailLogEntity.builder()
          .phone(phone)
          .verificationCode(code)
          .build();
      this.verificationFailLogRepository.save(failLog);
      throw new CustomException(ErrorMessage.OVER_3_MIN_THEN_EXPIRED);
    }
  }

  public void checkEmailDuplication(MemberReqDto.checkEmailDuplication query) {
    String email = query.getEmail();

    boolean isDuplicated = this.memberRepository.existsMemberByEmail(email);
    if (isDuplicated) throw new CustomException(ErrorMessage.EMAIL_DUPLICATED);
  }

  public MemberResDto.signUp signUp(MemberReqDto.signUp body) {
    // 1. 비밀번호 암호화
    // 2. 멤버 저장
    // 3. access / refresh 토큰 만들기
    // 4. refresh 토큰 저장
  }
}
