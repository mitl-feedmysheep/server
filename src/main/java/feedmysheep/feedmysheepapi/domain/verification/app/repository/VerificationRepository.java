package feedmysheep.feedmysheepapi.domain.verification.app.repository;

import feedmysheep.feedmysheepapi.models.VerificationEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepository extends JpaRepository<VerificationEntity, Long> {
  int countByPhoneAndValidDate(String phone, LocalDate validDate);
  VerificationEntity findByPhoneAndVerificationCodeAndCreatedAtBetween(String phone, String verificationCode, LocalDateTime threeMinBefore, LocalDateTime now);
}