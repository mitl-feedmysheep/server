package feedmysheep.feedmysheepapi.domain.verification.app.repository;

import static org.assertj.core.api.Assertions.*;

import feedmysheep.feedmysheepapi.models.VerificationEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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

  @Test
  @DisplayName("휴대폰번호와 인증번호 유효날짜를 통해 횟수 조회")
  public void countByPhoneAndValidDate() {
    // given
    VerificationEntity test1 = VerificationEntity.builder()
        .phone("01011112222")
        .verificationCode("111111")
        .validDate(yesterday)
        .build();
    VerificationEntity test2 = VerificationEntity.builder()
        .phone("01011112222")
        .verificationCode("222222")
        .validDate(yesterday)
        .build();
    VerificationEntity test3 = VerificationEntity.builder()
        .phone("01011112222")
        .verificationCode("333333")
        .validDate(today)
        .build();
    verificationRepository.save(test1);
    verificationRepository.save(test2);
    verificationRepository.save(test3);
    // when
    int yesterdayCount = verificationRepository.countByPhoneAndValidDate("01011112222", yesterday);
    int todayCount = verificationRepository.countByPhoneAndValidDate("01011112222", today);
    // then
    assertThat(yesterdayCount).isEqualTo(2);
    assertThat(todayCount).isEqualTo(1);
  }

  @Test
  @DisplayName("휴대폰번호와 인증번호가 3분이내에 존재하는지 조회")
  public void findByPhoneAndVerificationCodeAndCreatedAtBetween() {
    // given
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime fourMinAgo = now.minusMinutes(4);
    LocalDateTime threeMinAgo = now.minusMinutes(3);
    VerificationEntity test1 = VerificationEntity.builder()
        .phone("01011112222")
        .verificationCode("111111")
        .validDate(yesterday)
        .build();
    test1.setCreatedAt(fourMinAgo);
    VerificationEntity test2 = VerificationEntity.builder()
        .phone("01011112222")
        .verificationCode("111111")
        .validDate(today)
        .build();
    test2.setCreatedAt(now);
    verificationRepository.save(test1);
    verificationRepository.save(test2);
    // when
    VerificationEntity validVerification = this.verificationRepository.findByPhoneAndVerificationCodeAndCreatedAtBetween("01011112222", "111111", threeMinAgo, now);
    List<VerificationEntity> verificationList = this.verificationRepository.findAll();
    // then
    assertThat(validVerification.getVerificationCode()).isEqualTo("111111");
    assertThat(validVerification.getValidDate()).isEqualTo(today);
    assertThat(verificationList.size()).isEqualTo(2);
  }
}