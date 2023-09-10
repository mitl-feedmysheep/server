package feedmysheep.feedmysheepapi.domain.verificationfaillog.app.repository;

import feedmysheep.feedmysheepapi.models.VerificationFailLogEntity;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationFailLogRepository extends JpaRepository<VerificationFailLogEntity, Long> {
  int countByPhoneAndCreatedAtBetween(String phone, LocalDateTime todayStart, LocalDateTime todayEnd);
}



//@Repository
//public interface VerificationRepository extends JpaRepository<Verification, Long> {
//  int countByPhoneAndValidDate(String phone, LocalDate validDate);
//  Verification findByPhoneAndVerificationCodeAndCreatedAtBetween(String phone, String verificationCode, LocalDateTime threeMinBefore, LocalDateTime now);
//}