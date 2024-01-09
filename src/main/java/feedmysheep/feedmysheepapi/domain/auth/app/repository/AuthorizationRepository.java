package feedmysheep.feedmysheepapi.domain.auth.app.repository;

import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationRepository extends JpaRepository<AuthorizationEntity, Long> {

  @Query("SELECT a FROM AuthorizationEntity a WHERE a.authorizationId = :authorizationId")
  Optional<AuthorizationEntity> getAuthorizationByAuthorizationId(@Param("authorizationId") Long authorizationId);

  //TODO 테스트코드 작성
  @Query("SELECT a FROM AuthorizationEntity a WHERE a.level = :level")
  Optional<AuthorizationEntity> getAuthorizationByLevel(@Param("level") int level);
}
