package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import feedmysheep.feedmysheepapi.models.CellEntity;
import java.util.List;
import java.util.UUID;

public interface CellRepositoryCustom {
  List<CellEntity> findAllByOrganIdListAndCurDate(List<UUID> organIdList);

}
