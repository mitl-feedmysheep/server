package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import java.util.UUID;

public interface CellGatheringRepositoryCustom {

  // TODO 테스트코드
  void deleteByCellGatheringId(UUID memberId, UUID cellGatheringId);
}
