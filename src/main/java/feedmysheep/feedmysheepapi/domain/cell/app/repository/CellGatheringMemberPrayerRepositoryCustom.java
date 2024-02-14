package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellServiceDto.updatePrayerById;
import feedmysheep.feedmysheepapi.models.CellGatheringMemberPrayerEntity;
import java.util.List;
import java.util.UUID;

public interface CellGatheringMemberPrayerRepositoryCustom {

  // TODO 테스트코드
  List<CellGatheringMemberPrayerEntity> findAllByCellGatheringMemberIdList(
      List<UUID> cellGatheringMemberIdList);

  // TODO 테스트코드
  void updateByCellGatheringMemberPrayerId(updatePrayerById updateDto);

  // TODO 테스트코드
  void deleteByCellGatheringMemberPrayerId(UUID memberId, UUID cellGatheringMemberPrayerId);
}
