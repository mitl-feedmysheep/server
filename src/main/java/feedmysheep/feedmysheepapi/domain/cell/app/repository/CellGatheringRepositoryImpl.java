package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import feedmysheep.feedmysheepapi.models.QCellGatheringEntity;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CellGatheringRepositoryImpl implements CellGatheringRepositoryCustom {

  private final JPAQueryFactory queryFactory;
  private final QCellGatheringEntity cellGathering = QCellGatheringEntity.cellGatheringEntity;

  @Override
  public void deleteByCellGatheringId(UUID memberId, UUID cellGatheringId) {
    this.queryFactory.update(cellGathering).set(cellGathering.deletedAt, LocalDateTime.now())
        .set(cellGathering.deletedBy, memberId)
        .where(cellGathering.cellGatheringId.eq(cellGatheringId)).execute();
  }
}
