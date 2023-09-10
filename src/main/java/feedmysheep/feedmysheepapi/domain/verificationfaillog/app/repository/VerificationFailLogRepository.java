package feedmysheep.feedmysheepapi.domain.verificationfaillog.app.repository;

import feedmysheep.feedmysheepapi.models.VerificationFailLogEntity;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationFailLogRepository extends JpaRepository<VerificationFailLogEntity, Long> {
  int countByPhoneAndIsFailedAndCreatedAtBetween(String phone, boolean isFailed, LocalDateTime todayStart, LocalDateTime todayEnd);
}