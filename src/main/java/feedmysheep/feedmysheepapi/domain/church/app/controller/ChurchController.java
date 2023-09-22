package feedmysheep.feedmysheepapi.domain.church.app.controller;

import feedmysheep.feedmysheepapi.domain.church.app.service.ChurchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/church")
public class ChurchController {
  private final ChurchService churchService;

  @Autowired
  public ChurchController(ChurchService churchService) { this.churchService = churchService };
}
