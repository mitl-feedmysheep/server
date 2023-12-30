package feedmysheep.feedmysheepapi.domain.church.app.repository;

import feedmysheep.feedmysheepapi.models.BodyEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyRepository extends JpaRepository<BodyEntity, Long> {
  @Query("SELECT b FROM BodyEntity b WHERE b.isValid = true and b.churchId = :churchId")
  List<BodyEntity> getBodyListByChurchId(@Param("churchId") Long churchId);
}