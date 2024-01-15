package feedmysheep.feedmysheepapi.domain.auth.app.repository;

import feedmysheep.feedmysheepapi.models.AuthorizationScreenEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationScreenRepository extends
    JpaRepository<AuthorizationScreenEntity, Long> {

  // TODO 테스트 코드 작성
  @Query("SELECT a FROM AuthorizationScreenEntity a WHERE a.screenKey = :screenKey")
  Optional<AuthorizationScreenEntity> getAuthorizationScreenByScreenKey(
      @Param("screenKey") String screenKey);
}
