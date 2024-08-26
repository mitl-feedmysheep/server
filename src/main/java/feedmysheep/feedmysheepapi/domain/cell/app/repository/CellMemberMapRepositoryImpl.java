package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import feedmysheep.feedmysheepapi.models.CellMemberMapEntity;
import feedmysheep.feedmysheepapi.models.QCellMemberMapEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CellMemberMapRepositoryImpl implements CellMemberMapRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  private final QCellMemberMapEntity cellMemberMap = QCellMemberMapEntity.cellMemberMapEntity;

  @Override
  public List<CellMemberMapEntity> findAllByCellIdListAndMemberIdAndCurDate(List<UUID> cellIdList,
      UUID memberId) {
    LocalDate today = LocalDate.now();
    return this.queryFactory.selectFrom(cellMemberMap).where(
        cellMemberMap.cellId.in(cellIdList).and(cellMemberMap.memberId.eq(memberId))
            .and(cellMemberMap.startDate.loe(today)).and(cellMemberMap.endDate.goe(today))).fetch();
  }

  @Override
  public List<CellMemberMapEntity> findAllByCellIdAndCurDate(UUID cellId) {
    LocalDate today = LocalDate.now();
    return this.queryFactory.selectFrom(cellMemberMap).where(
        cellMemberMap.cellId.eq(cellId).and(cellMemberMap.startDate.loe(today))
            .and(cellMemberMap.endDate.goe(today))).fetch();
  }
}
