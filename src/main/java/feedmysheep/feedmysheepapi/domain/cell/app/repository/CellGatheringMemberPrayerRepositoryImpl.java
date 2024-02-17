package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellServiceDto.updatePrayerById;
import feedmysheep.feedmysheepapi.models.CellGatheringMemberPrayerEntity;
import feedmysheep.feedmysheepapi.models.QCellGatheringMemberPrayerEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CellGatheringMemberPrayerRepositoryImpl implements
    CellGatheringMemberPrayerRepositoryCustom {

  private final JPAQueryFactory queryFactory;
  private final QCellGatheringMemberPrayerEntity cellGatheringMemberPrayer = QCellGatheringMemberPrayerEntity.cellGatheringMemberPrayerEntity;

  @Override
  public List<CellGatheringMemberPrayerEntity> findAllByCellGatheringMemberIdList(
      List<UUID> cellGatheringMemberIdList) {
    return queryFactory.selectFrom(cellGatheringMemberPrayer)
        .where(cellGatheringMemberPrayer.cellGatheringMemberId.in(cellGatheringMemberIdList))
        .fetch();
  }

  @Override
  public void updateByCellGatheringMemberPrayerId(updatePrayerById updateDto) {
    this.queryFactory.update(cellGatheringMemberPrayer)
        .set(cellGatheringMemberPrayer.prayerRequest, updateDto.getPrayerRequest()).where(
            cellGatheringMemberPrayer.cellGatheringMemberPrayerId.eq(
                updateDto.getCellGatheringMemberPrayerId())).execute();
  }

  @Override
  public void deleteByCellGatheringMemberPrayerId(UUID memberId, UUID cellGatheringMemberPrayerId) {
    this.queryFactory.update(cellGatheringMemberPrayer)
        .set(cellGatheringMemberPrayer.deletedAt, LocalDateTime.now()).where(
            cellGatheringMemberPrayer.cellGatheringMemberPrayerId.eq(cellGatheringMemberPrayerId))
        .execute();
  }
}
