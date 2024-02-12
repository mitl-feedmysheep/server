package feedmysheep.feedmysheepapi.domain.word.app.repository;

import feedmysheep.feedmysheepapi.models.WordEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<WordEntity, UUID> {

  @Query("SELECT w FROM WordEntity w WHERE w.isValid = true and w.screenKey = :screenKey")
  List<WordEntity> getWordListByScreenKey(@Param("screenKey") String screenKey);
}