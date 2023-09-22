package feedmysheep.feedmysheepapi.domain.church.app.service;

import feedmysheep.feedmysheepapi.domain.church.app.repository.ChurchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChurchService {
  private final ChurchRepository churchRepository;

  @Autowired
  public ChurchService (ChurchRepository churchRepository) {
    this.churchRepository = churchRepository;
  }
}
