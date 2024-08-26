package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellServiceDto;
import feedmysheep.feedmysheepapi.models.CellGatheringMemberEntity;
import java.util.List;
import java.util.UUID;

public interface CellGatheringMemberRepositoryCustom {

  List<CellGatheringMemberEntity> findAllByCellGatheringIdList(List<UUID> cellGatheringIdList);

  void updateByCellGatheringMemberId(
      CellServiceDto.updateAttendancesAndStoryWhenExisting updateDto);
}
