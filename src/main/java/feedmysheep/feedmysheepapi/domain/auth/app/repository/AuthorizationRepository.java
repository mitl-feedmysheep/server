package feedmysheep.feedmysheepapi.domain.auth.app.repository;

import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationRepository extends JpaRepository<AuthorizationEntity, Long> {

}
