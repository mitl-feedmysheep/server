package feedmysheep.feedmysheepapi.domain.word.app.repository;

import feedmysheep.feedmysheepapi.models.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Word, Long>{
  Word findByMainScreenAndSubScreen(String mainScreen, String subScreen);
}