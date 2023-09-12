package feedmysheep.feedmysheepapi.domain.verification.app.repository;

import feedmysheep.feedmysheepapi.models.VerificationEntity;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepository extends JpaRepository<VerificationEntity, Long> {
  int countByPhoneAndValidDate(String phone, LocalDate validDate);
  Optional<VerificationEntity> findByPhoneAndVerificationCode(String phone, String verificationCode);
}