package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import feedmysheep.feedmysheepapi.models.CellGatheringMemberPrayerEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CellGatheringMemberPrayerRepository extends
    JpaRepository<CellGatheringMemberPrayerEntity, Long> {

  // TODO 테스트코드 작성
  @Query("SELECT cgmp FROM CellGatheringMemberPrayerEntity cgmp WHERE cgmp.cellGatheringMemberId In (:cellGatheringMemberIdList)")
  List<CellGatheringMemberPrayerEntity> getCellGatheringMemberPrayerListByCellGatheringMemberIdList(
      @Param("cellGatheringMemberIdList") List<Long> cellGatheringMemberIdList);

  // TODO 테스트 코드 작성
  @Query("SELECT cgmp FROM CellGatheringMemberPrayerEntity cgmp WHERE cgmp.cellGatheringMemberId = :cellGatheringMemberId")
  List<CellGatheringMemberPrayerEntity> getCellGatheringMemberPrayerListByCellGatheringMemberId(
      @Param("cellGatheringMemberId") Long cellGatheringMemberId);
}
