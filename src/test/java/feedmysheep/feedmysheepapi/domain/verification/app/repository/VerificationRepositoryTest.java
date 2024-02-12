package feedmysheep.feedmysheepapi.domain.verification.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.domain.DataFactory;
import feedmysheep.feedmysheepapi.domain.TestUtil;
import feedmysheep.feedmysheepapi.global.config.TestConfig;
import feedmysheep.feedmysheepapi.models.VerificationEntity;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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
@Import(TestConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class VerificationRepositoryTest {

  @Autowired
  VerificationRepository verificationRepository;

  static VerificationEntity verification1;

  @BeforeAll
  public static void setup(@Autowired VerificationRepository verificationRepository) {
    String phone = TestUtil.getRandomPhone();
    verification1 = verificationRepository.save(DataFactory.createVerificationByPhone(phone));
    verificationRepository.save(DataFactory.createVerificationByPhone(phone));
    verificationRepository.save(DataFactory.createVerificationByPhone(phone));
  }

  @AfterAll
  public static void cleanup(@Autowired VerificationRepository verificationRepository) {
    verificationRepository.deleteAll();
  }

  @Test
  @DisplayName("휴대폰번호로 오늘 인증번호 전송 횟수 조회")
  void test1() {
    // given
    LocalDate today = LocalDate.now(); // DB default로 CURRENT_DATE 잘 들어갔나 확인

    // when
    int verificationListCount = this.verificationRepository.countByPhoneAndValidDate(
        verification1.getPhone(), today);

    // then
    assertThat(verificationListCount).isEqualTo(3);
  }

  @Test
  @DisplayName("휴대폰번호와 인증번호로 전송 여부 조회 -> 성공")
  void test2() {
    // given

    // when
    Optional<VerificationEntity> validVerification = this.verificationRepository.getVerificationByPhoneAndVerificationCode(
        verification1.getPhone(),
        verification1.getVerificationCode());

    // then
    assertThat(validVerification).isPresent();
  }

  @Test
  @DisplayName("휴대폰번호와 인증번호로 전송 여부 조회 -> 실패")
  void test3() {
    // given

    // when
    Optional<VerificationEntity> invalidVerificationByPhone = this.verificationRepository.getVerificationByPhoneAndVerificationCode(
        TestUtil.getRandomPhone(),
        verification1.getVerificationCode());
    Optional<VerificationEntity> invalidVerificationByVerificationCode = this.verificationRepository.getVerificationByPhoneAndVerificationCode(
        verification1.getPhone(),
        TestUtil.getRandomString(6));

    // then
    assertThat(invalidVerificationByPhone).isNotPresent();
    assertThat(invalidVerificationByVerificationCode).isNotPresent();
  }
}