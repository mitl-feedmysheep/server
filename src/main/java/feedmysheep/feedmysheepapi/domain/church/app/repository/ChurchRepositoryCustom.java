package feedmysheep.feedmysheepapi.domain.church.app.repository;

import feedmysheep.feedmysheepapi.models.ChurchEntity;
import java.util.List;

public interface ChurchRepositoryCustom {
  List<ChurchEntity> findAll();

}
