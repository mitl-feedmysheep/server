package feedmysheep.feedmysheepapi.domain.verification.app.repository;

import feedmysheep.feedmysheepapi.models.Verification;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, Long> {
  int countByPhoneAndValidDate(String phone, LocalDate validDate);
  Verification findByPhoneAndVerificationCodeAndCreatedAtBetween(String phone, String verificationCode, LocalDateTime threeMinBefore, LocalDateTime now);
}