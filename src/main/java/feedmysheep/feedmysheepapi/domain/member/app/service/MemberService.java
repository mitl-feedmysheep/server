package feedmysheep.feedmysheepapi.domain.member.app.service;

import feedmysheep.feedmysheepapi.domain.auth.app.repository.AuthorizationRepository;
import feedmysheep.feedmysheepapi.domain.church.app.repository.ChurchMemberMapRepository;
import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberMapper;
import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberReqDto;
import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberResDto;
import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
import feedmysheep.feedmysheepapi.domain.verification.app.repository.VerificationFailLogRepository;
import feedmysheep.feedmysheepapi.domain.verification.app.repository.VerificationRepository;
import feedmysheep.feedmysheepapi.global.policy.CONSTANT.VERIFICATION;
import feedmysheep.feedmysheepapi.global.thirdparty.twilio.TwilioService;
import feedmysheep.feedmysheepapi.global.utils.jwt.CustomUserDetails;
import feedmysheep.feedmysheepapi.global.utils.jwt.JwtDto;
import feedmysheep.feedmysheepapi.global.utils.jwt.JwtDto.memberInfo;
import feedmysheep.feedmysheepapi.global.utils.jwt.JwtTokenProvider;
import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;
import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import feedmysheep.feedmysheepapi.models.ChurchMemberMapEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import feedmysheep.feedmysheepapi.models.VerificationEntity;
import feedmysheep.feedmysheepapi.models.VerificationFailLogEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

  private final MemberRepository memberRepository;
  private final VerificationRepository verificationRepository;
  private final VerificationFailLogRepository verificationFailLogRepository;
  private final AuthorizationRepository authorizationRepository;
  private final TwilioService twilioService;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;

  private final ChurchMemberMapRepository churchMemberMapRepository;
  private final MemberMapper memberMapper;

  @Autowired
  public MemberService(
      MemberRepository memberRepository,
      VerificationRepository verificationRepository,
      VerificationFailLogRepository verificationFailLogRepository,
      AuthorizationRepository authorizationRepository,
      ChurchMemberMapRepository churchMemberMapRepository,
      TwilioService twilioService,
      PasswordEncoder passwordEncoder,
      JwtTokenProvider jwtTokenProvider,
      MemberMapper memberMapper) {
    this.memberRepository = memberRepository;
    this.verificationRepository = verificationRepository;
    this.verificationFailLogRepository = verificationFailLogRepository;
    this.authorizationRepository = authorizationRepository;
    this.churchMemberMapRepository = churchMemberMapRepository;
    this.twilioService = twilioService;
    this.passwordEncoder = passwordEncoder;
    this.jwtTokenProvider = jwtTokenProvider;
    this.memberMapper = memberMapper;
  }

  ;

  public void sendVerificationCode(MemberReqDto.sendVerificationCode query) {
    String phone = query.getPhone();
    LocalDate today = LocalDate.now();
    LocalDateTime startOfToday = LocalDate.now().atTime(LocalTime.MIN);
    LocalDateTime endOfToday = LocalDate.now().atTime(LocalTime.MAX);

    // 1. 휴대폰 사용 여부 체크
    this.memberRepository.getMemberByPhone(phone)
        .orElseThrow((() -> new CustomException(ErrorMessage.PHONE_IN_USE)));

    // 2. FailLog 5회 이상 여부 체크
    int failCount = this.verificationFailLogRepository.countByPhoneAndCreatedAtBetween(phone,
        startOfToday, endOfToday);
    if (failCount >= 5) {
      throw new CustomException(ErrorMessage.FAIL_LOG_OVER_5_TRIES);
    }

    // 3. 인증코드 발급 5회 미만 여부 체크
    int usedCount = this.verificationRepository.countByPhoneAndValidDate(phone, today);
    if (usedCount >= VERIFICATION.MAX_CODE_GEN_NUM) {
      throw new CustomException(ErrorMessage.CODE_GEN_TODAY_EXCEEDED);
    }

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
    this.memberRepository.getMemberByPhone(phone)
        .orElseThrow(() -> new CustomException(ErrorMessage.PHONE_IN_USE));

    // 2. 금일 인증실패 5회 여부 체크
    int failCount = this.verificationFailLogRepository.countByPhoneAndCreatedAtBetween(phone,
        startOfToday, endOfToday);
    if (failCount >= VERIFICATION.MAX_CODE_TRY_NUM) {
      throw new CustomException(ErrorMessage.FAIL_LOG_OVER_5_TRIES);
    }

    // 3. 휴대폰 번호와 인증코드 여부 체크
    VerificationEntity verification = this.verificationRepository.findByPhoneAndVerificationCode(
        phone, code).orElseThrow(() -> new CustomException(ErrorMessage.NO_VERIFICATION_CODE));
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

    this.memberRepository.getMemberByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorMessage.EMAIL_DUPLICATED));
  }

  @Transactional
  public MemberResDto.signUp signUp(MemberReqDto.signUp body) {
    // 1. 비밀번호 암호화
    body.setPassword(this.passwordEncoder.encode(body.getPassword()));

    // 2. Validation - 방어로직
    this.memberRepository.getMemberByPhone(body.getPhone())
        .orElseThrow(() -> new CustomException(ErrorMessage.PHONE_IN_USE));
    this.memberRepository.getMemberByEmail(body.getEmail())
        .orElseThrow(() -> new CustomException(ErrorMessage.EMAIL_DUPLICATED));

    // 3. 기본 authroization 가져오기
    AuthorizationEntity authorization = this.authorizationRepository.findById(1L)
        .orElseThrow(() -> new CustomException(ErrorMessage.NO_AUTHORIZATION));

    // 4. 멤버 저장
    MemberEntity memberToSave = MemberEntity.builder()
        .authorizationId(authorization.getAuthorizationId())
        .memberName(body.getMemberName())
        .sex(body.getSex())
        .birthday(body.getBirthday())
        .phone(body.getPhone())
        .address(body.getAddress())
        .email(body.getEmail())
        .password(body.getPassword())
        .build();
    MemberEntity member = this.memberRepository.save(memberToSave);

    // 5. access / refresh 토큰 만들기
    JwtDto.memberInfo memberInfo = new memberInfo();
    memberInfo.setMemberId(member.getMemberId());
    memberInfo.setLevel(authorization.getLevel());
    memberInfo.setMemberName(member.getMemberName());
    String refreshToken = this.jwtTokenProvider.createRefreshToken(memberInfo);
    String accessToken = this.jwtTokenProvider.createAccessToken(memberInfo);

    return new MemberResDto.signUp(refreshToken, accessToken);
  }

  public MemberResDto.signIn signIn(MemberReqDto.signIn body) {
    // 1. 이메일 유저 여부 체크
    MemberEntity member = this.memberRepository.getMemberByEmail(body.getEmail()).orElseThrow(
        () -> new CustomException(ErrorMessage.NO_EMAIL_MEMBER_FOUND));
    AuthorizationEntity authorization = this.authorizationRepository.findById(
            member.getAuthorizationId())
        .orElseThrow(() -> new CustomException(ErrorMessage.NO_USER_AUTHORIZATION));

    // 2. 유저 비밀번호 체크
    if (!this.passwordEncoder.matches(body.getPassword(), member.getPassword())) {
      throw new CustomException(ErrorMessage.WRONG_PASSWORD);
    }

    // 3. access / refresh 토큰 만들기
    JwtDto.memberInfo memberInfo = new memberInfo();
    memberInfo.setMemberId(member.getMemberId());
    memberInfo.setLevel(authorization.getLevel());
    memberInfo.setMemberName(member.getMemberName());
    String refreshToken = this.jwtTokenProvider.createRefreshToken(memberInfo);
    String accessToken = this.jwtTokenProvider.createAccessToken(memberInfo);

    return new MemberResDto.signIn(refreshToken, accessToken);
  }

  public MemberResDto.checkChurchMember checkChurchMember(CustomUserDetails customUserDetails) {
    List<ChurchMemberMapEntity> churchMemberMapList =
        this.churchMemberMapRepository.getChurchMemberMapListByMemberId(
            customUserDetails.getMemberId());
    boolean isChurchMember = churchMemberMapList.size() > 0;

    return new MemberResDto.checkChurchMember(isChurchMember);
  }
}
