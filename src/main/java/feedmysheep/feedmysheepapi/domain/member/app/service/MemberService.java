package feedmysheep.feedmysheepapi.domain.member.app.service;

import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberReqDto;
import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
import feedmysheep.feedmysheepapi.domain.verification.app.repository.VerificationRepository;
import feedmysheep.feedmysheepapi.global.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.response.error.ErrorMessage;
import feedmysheep.feedmysheepapi.global.thirdparty.twilio.TwilioService;
import feedmysheep.feedmysheepapi.models.Verification;
import java.time.LocalDate;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
  private final MemberRepository memberRepository;
  private final VerificationRepository verificationRepository;
  private final TwilioService twilioService;
  @Value("${verification.maxCodeGenNum}")
  private int maxCodeGenNum;
  @Value("${verification.maxCodeTryNum}")
  private int maxCodeTryNum;

  @Autowired
  public MemberService(MemberRepository memberRepository, VerificationRepository verificationRepository, TwilioService twilioService) {
    this.memberRepository = memberRepository;
    this.verificationRepository = verificationRepository;
    this.twilioService = twilioService;
  };

  public void sendVerificationCode(MemberReqDto.sendVerificationCode query) {
    String phone = query.getPhone();
    LocalDate today = LocalDate.now();

    // 1. 휴대폰 사용 여부 체크
    boolean isDuplicated = this.memberRepository.existsMemberByPhone(phone);
    if (isDuplicated) throw new CustomException(ErrorMessage.PHONE_IN_USE);

    // 2. 인증코드 발급 5회 미만 여부
    int usedCount = this.verificationRepository.countByPhoneAndValidDate(phone, today);
    if (usedCount >= this.maxCodeGenNum) throw new CustomException(ErrorMessage.CODE_GEN_TODAY_EXCEEDED);

    // 3. 인증코드 generate
    Random random = new Random();
    int min = 100000;
    int max = 999999;
    String verificationCode = Integer.toString(random.nextInt(max - min + 1) + min);

    // 4. 인증코드 전송
    String phoneWithCountry = "+" + "82" + phone;
    String messageBody = "[피마쉽(FeedMySheep)] 인증번호는 " + verificationCode + "입니다.";
    try {
      twilioService.sendSMS(phoneWithCountry, messageBody);
    } catch (Exception e) {
      // TODO 슬랙 메시지

      // 문자 메시지 전송 에러
      throw new CustomException(e.getMessage());
    }

    // 5. 인증코드 DB 저장
    Verification.builder()
      .phone(phone)
      .verificationCode(verificationCode)
      .validDate(today)
      .build()
      ;
  }
}
