package feedmysheep.feedmysheepapi.domain.verification.app.repository;

import feedmysheep.feedmysheepapi.models.VerificationEntity;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepository extends JpaRepository<VerificationEntity, UUID> {

  @Query("SELECT COUNT(v) FROM VerificationEntity v WHERE v.phone = :phone AND v.validDate = :validDate")
  int countByPhoneAndValidDate(@Param("phone") String phone,
      @Param("validDate") LocalDate validDate);

  @Query("SELECT v FROM VerificationEntity v WHERE v.phone = :phone AND v.verificationCode = :verificationCode")
  Optional<VerificationEntity> getVerificationByPhoneAndVerificationCode(
      @Param("phone") String phone, @Param("verificationCode") String verificationCode);
}