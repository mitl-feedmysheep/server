package feedmysheep.feedmysheepapi.domain;

import feedmysheep.feedmysheepapi.domain.church.app.repository.BodyRepository;
import feedmysheep.feedmysheepapi.domain.church.app.repository.ChurchRepository;
import feedmysheep.feedmysheepapi.models.BodyEntity;
import feedmysheep.feedmysheepapi.models.ChurchEntity;

public class DataFactory {

  private final ChurchRepository churchRepository;

  private final BodyRepository bodyRepository;

  public DataFactory(ChurchRepository churchRepository, BodyRepository bodyRepository) {
    this.churchRepository = churchRepository;
    this.bodyRepository = bodyRepository;
  }

  // 교회 생성
  public ChurchEntity createTestChurch(boolean isValid) {
    ChurchEntity church = ChurchEntity.builder()
        .churchName(TestUtil.getRandomString())
        .churchLocation(TestUtil.getRandomString())
        .build();
    church.setValid(isValid);

    return this.churchRepository.save(church);
  }

  // 바디 생성
  public BodyEntity createTestBodyByChurchId(Long churchId, boolean isValid) {
    BodyEntity body = BodyEntity.builder()
        .churchId(churchId)
        .bodyName(TestUtil.getRandomString())
        .bodyLocation(TestUtil.getRandomString())
        .build();
    body.setValid(isValid);

    return this.bodyRepository.save(body);
  }
}
