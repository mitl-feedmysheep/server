package feedmysheep.feedmysheepapi.domain.auth.app.repository;

import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationRepository extends JpaRepository<AuthorizationEntity, Long>,
    AuthorizationRepositoryCustom {

  Optional<AuthorizationEntity> findByAuthorizationId(Long authorizationId);

  Optional<AuthorizationEntity> findByLevel(int level);
}
