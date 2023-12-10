package feedmysheep.feedmysheepapi.domain.text.app.repository;

import feedmysheep.feedmysheepapi.models.TextEntity;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TextRepository extends JpaRepository<TextEntity, Long> {

  @Query("SELECT t FROM TextEntity t WHERE t.isValid = true and t.screenKey = :screenKey")
  List<TextEntity> getTextListByScreenKey(@Param("screenKey") String screenKey);
}
