package feedmysheep.feedmysheepapi.domain.media.app.repository;

import feedmysheep.feedmysheepapi.models.MediaEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<MediaEntity, UUID> {

  List<MediaEntity> findAllByScreenKey(String screenKey);
}
