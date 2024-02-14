package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellServiceDto.updateAttendancesAndStoryWhenExisting;
import feedmysheep.feedmysheepapi.global.utils.Util;
import feedmysheep.feedmysheepapi.models.CellGatheringMemberEntity;
import feedmysheep.feedmysheepapi.models.QCellGatheringMemberEntity;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CellGatheringMemberRepositoryImpl implements CellGatheringMemberRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  private final QCellGatheringMemberEntity cellGatheringMember = QCellGatheringMemberEntity.cellGatheringMemberEntity;

  @Override
  public List<CellGatheringMemberEntity> findAllByCellGatheringIdList(
      List<UUID> cellGatheringIdList) {
    return queryFactory.selectFrom(cellGatheringMember)
        .where(cellGatheringMember.cellGatheringId.in(cellGatheringIdList)).fetch();
  }

  @Override
  public void updateByCellGatheringMemberId(updateAttendancesAndStoryWhenExisting updateDto) {
    JPAUpdateClause clause = this.queryFactory.update(cellGatheringMember);
    if (Util.isNotNull(updateDto.getWorshipAttendance())) {
      clause.set(cellGatheringMember.worshipAttendance, updateDto.getWorshipAttendance());
    }
    if (Util.isNotNull(updateDto.getCellGatheringAttendance())) {
      clause.set(cellGatheringMember.cellGatheringAttendance,
          updateDto.getCellGatheringAttendance());
    }
    if (Util.isNotNull(updateDto.getStory())) {
      clause.set(cellGatheringMember.story, updateDto.getStory());
    }
    clause.set(cellGatheringMember.updatedBy, updateDto.getMemberId());
    clause.where(cellGatheringMember.cellGatheringMemberId.eq(updateDto.getCellGatheringMemberId()))
        .execute();
  }
}
