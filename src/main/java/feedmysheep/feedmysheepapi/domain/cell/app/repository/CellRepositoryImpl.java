package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import feedmysheep.feedmysheepapi.models.CellEntity;
import feedmysheep.feedmysheepapi.models.QCellEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CellRepositoryImpl implements CellRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  QCellEntity cellEntity = QCellEntity.cellEntity;

  @Override
  public List<CellEntity> findAllByOrganIdListAndCurDate(List<UUID> organIdList) {
    LocalDate today = LocalDate.now();

    return this.queryFactory.selectFrom(cellEntity).where(
        cellEntity.organId.in(organIdList).and(cellEntity.startDate.loe(today))
            .and(cellEntity.endDate.goe(today))).fetch();
  }
}
