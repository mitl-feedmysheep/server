package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellServiceDto;
import feedmysheep.feedmysheepapi.models.CellGatheringMemberEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CellGatheringMemberRepository extends
    JpaRepository<CellGatheringMemberEntity, UUID> {

  // TODO 테스트코드 작성
  @Query("SELECT cgm FROM CellGatheringMemberEntity cgm WHERE cgm.cellGatheringId In (:cellGatheringIdList)")
  List<CellGatheringMemberEntity> getCellGatheringMemberListByCellGatheringIdList(
      @Param("cellGatheringIdList") List<Long> cellGatheringIdList);

  // TODO 테스트코드 작성
  @Query("SELECT cgm FROM CellGatheringMemberEntity cgm WHERE cgm.cellGatheringId = :cellGatheringId")
  List<CellGatheringMemberEntity> getCellGatheringMemberListByCellGatheringId(
      @Param("cellGatheringId") Long cellGatheringId);

  // TODO 테스트코드 작성
  @Transactional
  @Modifying
  @Query("UPDATE CellGatheringMemberEntity cgm SET "
      + "cgm.worshipAttendance = CASE WHEN :#{#repoDto.worshipAttendance} IS NOT NULL THEN :#{#repoDto.worshipAttendance} ELSE cgm.worshipAttendance END, "
      + "cgm.cellGatheringAttendance = CASE WHEN :#{#repoDto.cellGatheringAttendance} IS NOT NULL THEN :#{#repoDto.cellGatheringAttendance} ELSE cgm.cellGatheringAttendance END, "
      + "cgm.story = CASE WHEN :#{#repoDto.story} IS NOT NULL THEN :#{#repoDto.story} ELSE cgm.story END, "
      + "cgm.updatedBy = :#{#repoDto.memberId} "
      + "WHERE cgm.cellGatheringMemberId = :#{#repoDto.cellGatheringMemberId}")
  void updateAttendancesAndStoryWhenExisting(
      @Param("repoDto") CellServiceDto.updateAttendancesAndStoryWhenExisting repoDto);

}
