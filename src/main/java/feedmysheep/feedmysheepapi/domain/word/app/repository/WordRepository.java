package feedmysheep.feedmysheepapi.domain.word.app.repository;

import feedmysheep.feedmysheepapi.domain.word.app.dto.WordDto;
import feedmysheep.feedmysheepapi.models.Word;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
  WordDto.getWordByMainAndSubScreen findByMainScreenAndSubScreen(String mainScreen, String subScreen);
}