package feedmysheep.feedmysheepapi.domain.auth.app.repository;

import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationRepository extends JpaRepository<AuthorizationEntity, UUID>,
    AuthorizationRepositoryCustom {

  Optional<AuthorizationEntity> findByAuthorizationId(UUID authorizationId);

  Optional<AuthorizationEntity> findByLevel(int level);
}
