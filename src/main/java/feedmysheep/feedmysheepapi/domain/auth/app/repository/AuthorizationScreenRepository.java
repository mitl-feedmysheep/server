package feedmysheep.feedmysheepapi.domain.auth.app.repository;

import feedmysheep.feedmysheepapi.models.AuthorizationScreenEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationScreenRepository extends
    JpaRepository<AuthorizationScreenEntity, UUID>, AuthorizationScreenRepositoryCustom {

  Optional<AuthorizationScreenEntity> findByScreenKey(String screenKey);
}
