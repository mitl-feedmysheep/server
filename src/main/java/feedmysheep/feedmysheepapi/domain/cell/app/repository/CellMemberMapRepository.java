package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import feedmysheep.feedmysheepapi.models.CellMemberMapEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CellMemberMapRepository extends JpaRepository<CellMemberMapEntity, Long> {

  @Query("SELECT cmm FROM CellMemberMapEntity cmm WHERE cmm.memberId = :memberId and cmm.isValid = true and cmm.cellId IN (:cellIdList) and CURDATE() BETWEEN cmm.startDate and cmm.endDate")
  List<CellMemberMapEntity> getCellMemberMapListByCellIdListAndMemberId(
      @Param("cellIdList") List<Long> cellIdList, @Param("memberId") Long memberId);

  @Query("SELECT cmm FROM CellMemberMapEntity cmm WHERE cmm.cellId = :cellId and cmm.isValid = true and CURDATE() BETWEEN cmm.startDate and cmm.endDate")
  List<CellMemberMapEntity> getCellMemberMapListByCellId(@Param("cellId") Long cellId);

  // TODO 테스트코드 작성
  @Query("SELECT cmm FROM CellMemberMapEntity cmm WHERE cmm.cellMemberMapId = :cellMemberMapId and cmm.isValid = true")
  Optional<CellMemberMapEntity> getCellMemberMapByCellMemberMapId(
      @Param("cellMemberMapId") Long cellMemberMapId);

  // TODO 테스트코드 작성
  @Query("SELECT cmm FROM CellMemberMapEntity cmm WHERE cmm.cellMemberMapId = :cellMemberMapId and cmm.isValid = true")
  List<CellMemberMapEntity> getCellMemberMapListByMemberId(
      @Param("cellMemberMapId") Long cellMemberMapId);



}
