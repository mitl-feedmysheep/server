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


  //일단 사용 x 보류
  @Query("SELECT cmm FROM CellMemberMapEntity cmm WHERE cmm.memberId = :memberId and cmm.isValid = true and cmm.cellId = :cellId and CURDATE() BETWEEN cmm.startDate and cmm.endDate")
  CellMemberMapEntity getCellMemberMapByCellIdAndMemberId(
      @Param("cellId") Long cellId, @Param("memberId") Long memberId);
}
