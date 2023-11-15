package feedmysheep.feedmysheepapi.domain;

import feedmysheep.feedmysheepapi.domain.auth.app.repository.AuthorizationRepository;
import feedmysheep.feedmysheepapi.domain.word.app.repository.WordRepository;
import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import feedmysheep.feedmysheepapi.models.WordEntity;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class InitController {

  private final WordRepository wordRepository;
  private final AuthorizationRepository authorizationRepository;

  @Autowired
  public InitController(WordRepository wordRepository,
      AuthorizationRepository authorizationRepository) {
    this.wordRepository = wordRepository;
    this.authorizationRepository = authorizationRepository;
  }

  // health-check 나중에 해야 함

  @PostMapping("/data-seeding")
  public void insertDataSeeds() {
    // 스플래시 화면 말씀
    WordEntity seedWord = WordEntity.builder()
        .mainScreen("splash")
        .displayStartDate(LocalDate.parse("2023-01-01"))
        .displayEndDate(LocalDate.parse("2024-12-31"))
        .book("요한복음").chapter(15).verse(12)
        .words("내 계명은 곧 내가 너희를 사랑한 것 같이 너희도 서로 사랑하라 하는 이것이니라")
        .build();
    this.wordRepository.save(seedWord);

    // 기본 권한
    AuthorizationEntity seedAuth = AuthorizationEntity.builder()
        .level(100)
        .levelName("기본 권한")
        .build();
    this.authorizationRepository.save(seedAuth);
  }
}
