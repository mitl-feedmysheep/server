package feedmysheep.feedmysheepapi.domain.auth.app.repository;

import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import java.util.Optional;
import org.springframework.data.repository.query.Param;

public interface AuthorizationRepositoryCustom {

  Optional<AuthorizationEntity> getByAuthorizationId(Long authorizationId);

  Optional<AuthorizationEntity> getByLevel(int level);
}
