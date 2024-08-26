package feedmysheep.feedmysheepapi.domain.word.app.repository;

import feedmysheep.feedmysheepapi.models.WordEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<WordEntity, UUID>, WordRepositoryCustom {

  List<WordEntity> findAllByScreenKey(String screenKey);
}