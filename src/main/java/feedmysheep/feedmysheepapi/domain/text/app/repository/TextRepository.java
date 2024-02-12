package feedmysheep.feedmysheepapi.domain.text.app.repository;

import feedmysheep.feedmysheepapi.models.TextEntity;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TextRepository extends JpaRepository<TextEntity, UUID> {

  @Query("SELECT t FROM TextEntity t WHERE t.isValid = true and t.screenKey = :screenKey")
  Optional<TextEntity> getTextByScreenKey(@Param("screenKey") String screenKey);
}
