package feedmysheep.feedmysheepapi.domain.member.app.service;

import static org.assertj.core.api.Assertions.*;

import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberReqDto;
import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
import feedmysheep.feedmysheepapi.domain.verification.app.repository.VerificationRepository;
import feedmysheep.feedmysheepapi.global.thirdparty.twilio.TwilioService;
import feedmysheep.feedmysheepapi.models.Verification;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class MemberServiceTest {
  @Autowired
  private VerificationRepository verificationRepository;

  @Autowired
  private MemberRepository memberRepository;

//  @Autowired
//  private MemberService memberService;

  @BeforeEach
  public void setup() {}

  @Test
  @DisplayName("인증코드가 정상적으로 발송되고, 인증코드가 저장되었는지 확인 (실패여부는 Repo 테스트코드에서 확인)")
  public void sendVerificationCode() {
    // given
    String phone = "01088831954";
    MemberReqDto.sendVerificationCode query = new MemberReqDto.sendVerificationCode(phone);
    query.setPhone(phone);
    //// mock 생성
    TwilioService twilioServiceMock = Mockito.mock(TwilioService.class);
    MemberService memberService = new MemberService(memberRepository, verificationRepository, twilioServiceMock);

    // when
    memberService.sendVerificationCode(query);

    //then
    List<Verification> verificationList = verificationRepository.findAll();
    Boolean isMatchedPhone = verificationList.stream().anyMatch(verification -> verification.getPhone().equals(phone));
    assertThat(isMatchedPhone).isEqualTo(true);
  }
}