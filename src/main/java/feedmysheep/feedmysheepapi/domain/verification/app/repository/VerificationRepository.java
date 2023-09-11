package feedmysheep.feedmysheepapi.domain.verification.app.repository;

import feedmysheep.feedmysheepapi.models.VerificationEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface VerificationRepository extends JpaRepository<VerificationEntity, Long> {
  int countByPhoneAndValidDate(String phone, LocalDate validDate);
  VerificationEntity findByPhoneAndVerificationCode(String phone, String verificationCode);
}