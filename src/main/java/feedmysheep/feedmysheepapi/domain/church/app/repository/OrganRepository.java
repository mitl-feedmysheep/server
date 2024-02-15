package feedmysheep.feedmysheepapi.domain.church.app.repository;

import feedmysheep.feedmysheepapi.models.OrganEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganRepository extends JpaRepository<OrganEntity, UUID> {

  List<OrganEntity> findAllByBodyId(UUID bodyId);
}

