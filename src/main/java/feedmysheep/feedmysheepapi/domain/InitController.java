package feedmysheep.feedmysheepapi.domain;

import feedmysheep.feedmysheepapi.domain.auth.app.repository.AuthorizationRepository;
import feedmysheep.feedmysheepapi.domain.church.app.repository.BodyRepository;
import feedmysheep.feedmysheepapi.domain.church.app.repository.ChurchRepository;
import feedmysheep.feedmysheepapi.domain.word.app.repository.WordRepository;
import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import feedmysheep.feedmysheepapi.models.BodyEntity;
import feedmysheep.feedmysheepapi.models.ChurchEntity;
import feedmysheep.feedmysheepapi.models.WordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class InitController {

  private final WordRepository wordRepository;
  private final AuthorizationRepository authorizationRepository;
  private final ChurchRepository churchRepository;
  private final BodyRepository bodyRepository;

  @Autowired
  public InitController(WordRepository wordRepository,
      AuthorizationRepository authorizationRepository, ChurchRepository churchRepository,
      BodyRepository bodyRepository) {
    this.wordRepository = wordRepository;
    this.authorizationRepository = authorizationRepository;
    this.churchRepository = churchRepository;
    this.bodyRepository = bodyRepository;
  }

  // health-check 나중에 해야 함

  @PostMapping("/data-seeding")
  public void insertDataSeeds() {
    // 스플래시 화면 말씀
    WordEntity seedWord = WordEntity.builder()
        .screenKey("splash")
        .words("내 계명은 곧 내가 너희를 사랑한 것 같이 너희도 서로 사랑하라 하는 이것이니라 (요한복음 15:12)")
        .build();
    this.wordRepository.save(seedWord);

    // 기본 권한
    AuthorizationEntity seedAuth = AuthorizationEntity.builder()
        .level(100)
        .levelName("기본 권한")
        .build();
    this.authorizationRepository.save(seedAuth);

    // 교회
    ChurchEntity seedChurch1 = ChurchEntity.builder()
        .churchName("번동제일교회")
        .churchLocation("서울 강북구 수유")
        .build();

    seedChurch1.setValid(true);
    ChurchEntity seedChurch1Saved = this.churchRepository.save(seedChurch1);
    ChurchEntity seedChurch2 = ChurchEntity.builder()
        .churchName("양곡교회")
        .churchLocation("창원 성산구 신촌동")
        .build();
    seedChurch2.setValid(true);
    this.churchRepository.save(seedChurch2);

    // 교회별 부서
    BodyEntity seedBody1 = BodyEntity.builder()
        .churchId(seedChurch1Saved.getChurchId())
        .bodyName("새청")
        .bodyLocation("3층 중예배실")
        .bodyDescription("새벽이슬 같은 청년")
        .build();
    BodyEntity seedBody1Saved = this.bodyRepository.save(seedBody1);
    BodyEntity seedBody2 = BodyEntity.builder()
        .churchId(seedChurch1Saved.getChurchId())
        .bodyName("신혼부부모임")
        .bodyLocation("2층 헤븐")
        .bodyDescription("신혼부부 모임입니다.")
        .build();
    this.bodyRepository.save(seedBody2);
  }
}
