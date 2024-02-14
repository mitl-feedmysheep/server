package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellServiceDto;
import feedmysheep.feedmysheepapi.models.CellGatheringMemberEntity;
import java.util.List;
import java.util.UUID;

public interface CellGatheringMemberRepositoryCustom {

  // TODO 테스트코드 작성
  List<CellGatheringMemberEntity> findAllByCellGatheringIdList(List<UUID> cellGatheringIdList);

  // TODO 테스트코드 작성
  void updateByCellGatheringMemberId(
      CellServiceDto.updateAttendancesAndStoryWhenExisting updateDto);
}
