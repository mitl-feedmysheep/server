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
    System.out.println("now -->" + now);
    LocalDateTime yesterday = now.minusHours(24);
    System.out.println("yesterday -->" + yesterday);
    LocalDateTime startOfToday = LocalDate.now().atTime(LocalTime.MIN);
    System.out.println("startOfToday -->" + startOfToday);
    LocalDateTime endOfToday = LocalDate.now().atTime(LocalTime.MAX);
    System.out.println("endOfToday -->" + endOfToday);


    VerificationFailLogEntity testData = VerificationFailLogEntity.builder()
        .phone(this.phone)
        .verificationCode("111111")
        .build();
    // 카운트 되어야 하는 데이터
    verificationFailLogRepository.save(testData);
    verificationFailLogRepository.save(testData);
    verificationFailLogRepository.save(testData);
    // 비활성화 시킨 데이터
    testData.setFailed(false);
    verificationFailLogRepository.save(testData);
    // 다시 활성화 & 어제 만든 것
    testData.setFailed(true);
    testData.setCreatedAt(yesterday);
    verificationFailLogRepository.save(testData);

    // when
    int failNum = this.verificationFailLogRepository.countByPhoneAndIsFailedAndCreatedAtBetween(this.phone, true, startOfToday, endOfToday);

    // then
    assertThat(failNum).isEqualTo(3);
  }
}