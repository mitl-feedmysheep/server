package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import feedmysheep.feedmysheepapi.models.CellMemberMapEntity;
import java.util.List;
import java.util.UUID;

public interface CellMemberMapRepositoryCustom {

  List<CellMemberMapEntity> findAllByCellIdListAndMemberIdAndCurDate(List<UUID> cellIdList,
      UUID memberId);

  List<CellMemberMapEntity> findAllByCellIdAndCurDate(UUID cellId);
}
