package feedmysheep.feedmysheepapi.domain.verificationfaillog.app.repository;

import static org.assertj.core.api.Assertions.*;

import feedmysheep.feedmysheepapi.models.VerificationFailLogEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class VerificationFailLogRepositoryTest {
  @Autowired
  VerificationFailLogRepository verificationFailLogRepository;

  public final String phone = "01088831954";

  @Test
  @DisplayName("오늘 실패 횟수 체크")
  public void countByPhoneAndIsFailedAndCreatedAtBetween() {
    // given
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime yesterday = now.minusHours(24);
    LocalDateTime startOfToday = LocalDate.now().atTime(LocalTime.MIN);
    LocalDateTime endOfToday = LocalDate.now().atTime(LocalTime.MAX);

    VerificationFailLogEntity testData1 = VerificationFailLogEntity.builder()
        .phone(this.phone)
        .verificationCode("111111")
        .build();
    VerificationFailLogEntity testData2 = VerificationFailLogEntity.builder()
        .phone(this.phone)
        .verificationCode("111111")
        .build();
    VerificationFailLogEntity testData3 = VerificationFailLogEntity.builder()
        .phone(this.phone)
        .verificationCode("111111")
        .build();
    VerificationFailLogEntity testData4 = VerificationFailLogEntity.builder()
        .phone(this.phone)
        .verificationCode("111111")
        .build();
    testData4.setFailed(false);
    VerificationFailLogEntity testData5 = VerificationFailLogEntity.builder()
        .phone(this.phone)
        .verificationCode("111111")
        .build();
    testData5.setCreatedAt(yesterday);
    // 카운트 되어야 하는 데이터
    verificationFailLogRepository.save(testData1);
    verificationFailLogRepository.save(testData2);
    verificationFailLogRepository.save(testData3);
    // 비활성화 시킨 데이터
    verificationFailLogRepository.save(testData4);
    // 다시 활성화 & 어제 만든 것
    verificationFailLogRepository.save(testData5);

    // when
    int failNum = this.verificationFailLogRepository.countByPhoneAndCreatedAtBetween(this.phone, startOfToday, endOfToday);

    // then
    assertThat(failNum).isEqualTo(3);
  }
}