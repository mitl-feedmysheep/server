package feedmysheep.feedmysheepapi.domain.verification.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.global.config.TestQueryDslConfig;
import feedmysheep.feedmysheepapi.models.VerificationFailLogEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestQueryDslConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class VerificationFailLogRepositoryTest {

  @Autowired
  VerificationFailLogRepository verificationFailLogRepository;

  @AfterAll
  public static void tearDown(@Autowired VerificationRepository verificationRepository) {
    verificationRepository.deleteAll();
  }

  @Test
  @DisplayName("같은 번호로 저장되어 있을 때 -> 유효한 데이터의 개수 가져오기")
  public void test1() {
    // given
    LocalDateTime startOfToday = LocalDate.now().atTime(LocalTime.MIN);
    LocalDateTime endOfToday = LocalDate.now().atTime(LocalTime.MAX);

    String phone = "01011112222";
    this.verificationFailLogRepository.save(DataFactory.createVerificationFailLogByPhone(phone));
    this.verificationFailLogRepository.save(DataFactory.createVerificationFailLogByPhone(phone));
    VerificationFailLogEntity invalidVerificationFailLog = DataFactory.createVerificationFailLogByPhone(
        phone);
    invalidVerificationFailLog.setFailed(false);
    this.verificationFailLogRepository.save(invalidVerificationFailLog);
    this.verificationFailLogRepository.save(DataFactory.createVerificationFailLogByPhone(phone));

    // when
    int verificationFailLogCount = this.verificationFailLogRepository.countByPhoneAndCreatedAtBetween(
        phone, startOfToday, endOfToday);

    // then
    assertThat(verificationFailLogCount).isEqualTo(3);
  }
}