package feedmysheep.feedmysheepapi.domain.media.app.repository;

import feedmysheep.feedmysheepapi.models.MediaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<MediaEntity, Long> {

  @Query("SELECT m FROM MediaEntity m WHERE m.isValid = true and m.screenKey = :screenKey")
  List<MediaEntity> getMediasByScreenKey(@Param("screenKey") String screenKey);
}
