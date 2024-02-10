package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellReqDto;
import feedmysheep.feedmysheepapi.models.CellGatheringEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CellGatheringRepository extends JpaRepository<CellGatheringEntity, Long> {

  // TODO 테스트코드 작성
  @Query("SELECT cg FROM CellGatheringEntity cg WHERE cg.cellId = :cellId and cg.isValid = true")
  List<CellGatheringEntity> getCellGatheringListByCellId(@Param("cellId") Long cellId);

  // TODO 테스트코드 작성
  @Query("SELECT cg FROM CellGatheringEntity cg WHERE cg.cellGatheringId = :cellGatheringId and cg.isValid = true")
  Optional<CellGatheringEntity> getCellGatheringByCellGatheringId(
      @Param("cellGatheringId") Long cellGatheringId);

  // TODO 테스트코드 작성
  @Transactional
  @Modifying
  @Query("UPDATE CellGatheringEntity cg SET cg.isValid = false,cg.updatedBy = :memberId WHERE cg.cellGatheringId = :cellGatheringId")
  void deleteCellGatheringByCellGatheringId(
       @Param("memberId") Long memberId, @Param("cellGatheringId") Long cellGatheringId);

  // TODO 테스트코드 작성
  @Transactional
  @Modifying
  @Query("UPDATE CellGatheringEntity cg SET cg.gatheringTitle = :#{#updateDto.gatheringTitle}, cg.gatheringDate = :#{#updateDto.gatheringDate}, cg.startedAt = :#{#updateDto.startedAt}, cg.endedAt = :#{#updateDto.endedAt}, cg.gatheringPlace = :#{#updateDto.gatheringPlace}, cg.gatheringPhotoUrl = :#{#updateDto.gatheringPhotoUrl}, cg.description = :#{#updateDto.description}, cg.leaderComment = :#{#updateDto.leaderComment}, cg.pastorComment = :#{#updateDto.pastorComment}, cg.updatedBy = :memberId WHERE cg.cellGatheringId = :cellGatheringId and cg.isValid = true")
  void updateCellGatheringByCellGatheringId(
      @Param("updateDto") CellReqDto.updateCellGathering updateDto, @Param("memberId") Long memberId, @Param("cellGatheringId") Long cellGatheringId);
}
