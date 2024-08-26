package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellServiceDto.updatePrayerById;
import feedmysheep.feedmysheepapi.models.CellGatheringMemberPrayerEntity;
import java.util.List;
import java.util.UUID;

public interface CellGatheringMemberPrayerRepositoryCustom {

  List<CellGatheringMemberPrayerEntity> findAllByCellGatheringMemberIdList(
      List<UUID> cellGatheringMemberIdList);

  void updateByCellGatheringMemberPrayerId(updatePrayerById updateDto);

  void deleteByCellGatheringMemberPrayerId(UUID cellGatheringMemberPrayerId);
}
