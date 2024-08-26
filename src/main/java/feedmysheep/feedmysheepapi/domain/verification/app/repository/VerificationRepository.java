package feedmysheep.feedmysheepapi.domain.verification.app.repository;

import feedmysheep.feedmysheepapi.models.VerificationEntity;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepository extends JpaRepository<VerificationEntity, UUID>,
    VerificationRepositoryCustom {

  @Query("SELECT COUNT(v) FROM VerificationEntity v WHERE v.phone = :phone AND v.validDate = :validDate")
  int countByPhoneAndValidDate(@Param("phone") String phone,
      @Param("validDate") LocalDate validDate);

  Optional<VerificationEntity> findByPhoneAndVerificationCode(String phone,
      String verificationCode);
}