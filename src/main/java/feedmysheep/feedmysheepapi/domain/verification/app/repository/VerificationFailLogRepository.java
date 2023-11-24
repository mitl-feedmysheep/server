package feedmysheep.feedmysheepapi.domain.verification.app.repository;

import feedmysheep.feedmysheepapi.models.VerificationFailLogEntity;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationFailLogRepository extends
    JpaRepository<VerificationFailLogEntity, Long> {

  @Query("SELECT COUNT(vfl) FROM VerificationFailLogEntity vfl WHERE vfl.phone = :phone AND vfl.isFailed = true AND vfl.createdAt BETWEEN :startOfToday AND :endOfToday")
  int countByPhoneAndCreatedAtBetween(@Param("phone") String phone,
      @Param("startOfToday") LocalDateTime startOfToday,
      @Param("endOfToday") LocalDateTime endOfToday);
}