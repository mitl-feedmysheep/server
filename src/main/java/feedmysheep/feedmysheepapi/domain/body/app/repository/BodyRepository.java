package feedmysheep.feedmysheepapi.domain.body.app.repository;

import feedmysheep.feedmysheepapi.models.BodyEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyRepository extends JpaRepository<BodyEntity, Long> {

  //  List<BodyEntity> getBodyListByValidIsTrueAndChurch_ChurchId(Long churchId);
  @Query("SELECT b FROM BodyEntity b WHERE b.church.churchId = :churchId and b.isValid = true")
  List<BodyEntity> getBodyListByChurchId(@Param("churchId") Long churchId);
}