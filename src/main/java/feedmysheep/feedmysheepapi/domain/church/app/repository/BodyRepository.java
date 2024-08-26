package feedmysheep.feedmysheepapi.domain.church.app.repository;

import feedmysheep.feedmysheepapi.models.BodyEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyRepository extends JpaRepository<BodyEntity, UUID> {

  List<BodyEntity> findAllByChurchId(UUID churchId);

  Optional<BodyEntity> findByBodyId(UUID bodyId);
}