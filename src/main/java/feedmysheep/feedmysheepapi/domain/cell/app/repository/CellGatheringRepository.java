package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import feedmysheep.feedmysheepapi.models.CellGatheringEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CellGatheringRepository extends JpaRepository<CellGatheringEntity, Long> {

  // TODO 테스트코드 작성
  @Query("SELECT cg FROM CellGatheringEntity cg WHERE cg.cellId = :cellId and cg.isValid = true")
  List<CellGatheringEntity> getCellGatheringListByCellId(@Param("cellId") Long cellId);
}
