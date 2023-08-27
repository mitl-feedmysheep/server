package feedmysheep.feedmysheepapi.domain.verification.app.repository;

import static org.assertj.core.api.Assertions.*;

import feedmysheep.feedmysheepapi.models.Verification;
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
class VerificationRepositoryTest {
  @Autowired
  VerificationRepository verificationRepository;

  public final LocalDate today = LocalDate.now();
  public final LocalDate yesterday = today.minusDays(1);

  @BeforeEach
  public void setup() {
    Verification test1 = Verification.builder()
        .phone("01011112222")
        .verificationCode("111111")
        .validDate(yesterday)
        .build();
    Verification test2 = Verification.builder()
        .phone("01011112222")
        .verificationCode("222222")
        .validDate(yesterday)
        .build();
    Verification test3 = Verification.builder()
        .phone("01011112222")
        .verificationCode("333333")
        .validDate(today)
        .build();
    verificationRepository.save(test1);
    verificationRepository.save(test2);
    verificationRepository.save(test3);
  }

  @Test
  @DisplayName("휴대폰번호와 인증번호 유효날짜를 통해 횟수 조회")
  public void countByPhoneAndValidDate() {
    int yesterdayCount = verificationRepository.countByPhoneAndValidDate("01011112222", yesterday);
    int todayCount = verificationRepository.countByPhoneAndValidDate("01011112222", today);
    assertThat(yesterdayCount).isEqualTo(2);
    assertThat(todayCount).isEqualTo(1);
  }
}