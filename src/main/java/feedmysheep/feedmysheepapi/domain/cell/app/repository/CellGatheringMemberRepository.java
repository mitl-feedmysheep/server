package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import feedmysheep.feedmysheepapi.models.CellEntity;
import feedmysheep.feedmysheepapi.models.CellGatheringEntity;
import feedmysheep.feedmysheepapi.models.CellGatheringMemberEntity;
import feedmysheep.feedmysheepapi.models.CellMemberMapEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CellGatheringMemberRepository extends
    JpaRepository<CellGatheringMemberEntity, Long> {

  // TODO 테스트코드 작성
  @Query("SELECT cgm FROM CellGatheringMemberEntity cgm WHERE cgm.cellGatheringId In (:cellGatheringIdList)")
  List<CellGatheringMemberEntity> getCellGatheringMemberListByCellGatheringIdList(
      @Param("cellGatheringIdList") List<Long> cellGatheringIdList);

  // TODO 테스트코드 작성
  @Query("SELECT cgm FROM CellGatheringMemberEntity cgm WHERE cgm.cellGatheringId = :cellGatheringId")
  List<CellGatheringMemberEntity> getCellGatheringMemberListByCellGatheringId(
      @Param("cellGatheringId") Long cellGatheringId);

  // TODO 테스트코드 작성
  @Query("SELECT cgm FROM CellGatheringMemberEntity cgm WHERE cgm.cellGatheringId In (:cellGatheringIdList) and cgm.cellMemberId In (:cellMemberMapIdList)")
  List<CellGatheringMemberEntity> getCellGatheringMemberListByCellGatheringIdListAndCellMemberMapIdList(
      @Param("cellGatheringIdList") List<CellGatheringEntity> cellGatheringIdList,
      @Param("cellMemberMapIdList") List<CellMemberMapEntity> cellMemberMapIdList);
}