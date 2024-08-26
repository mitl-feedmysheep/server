package feedmysheep.feedmysheepapi.domain.text.app.repository;

import feedmysheep.feedmysheepapi.models.TextEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextRepository extends JpaRepository<TextEntity, UUID>, TextRepositoryCustom {

  Optional<TextEntity> findByScreenKey(String screenKey);
}
