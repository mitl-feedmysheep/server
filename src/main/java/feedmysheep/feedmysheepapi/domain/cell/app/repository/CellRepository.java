package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import feedmysheep.feedmysheepapi.models.CellEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CellRepository extends JpaRepository<CellEntity, UUID> {

  @Query("SELECT c FROM CellEntity c WHERE c.organId IN (:organIdList) and c.isValid = true and CURDATE() BETWEEN c.startDate and c.endDate")
  List<CellEntity> getCellListByOrganIdList(@Param("organIdList") List<Long> organIdList);

  //TODO 테스트 코드
  @Query("SELECT c FROM CellEntity c WHERE c.cellId = :cellId and c.isValid = true")
  Optional<CellEntity> getCellByCellId(@Param("cellId") Long cellId);
}
