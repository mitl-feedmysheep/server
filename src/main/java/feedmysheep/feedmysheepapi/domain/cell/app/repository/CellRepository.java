package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellResDto;
import feedmysheep.feedmysheepapi.models.CellEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CellRepository extends JpaRepository<CellEntity, Long> {

  @Query("SELECT c FROM CellEntity c WHERE c.organId IN (:organIdList) and c.isValid = true and CURDATE() BETWEEN c.startDate and c.endDate")
  List<CellEntity> getCellListByOrganIdList(@Param("organIdList") List<Long> organIdList);

  //TODO 테스트 코드
  @Query("SELECT c FROM CellEntity c WHERE c.cellId IN (:cellList) and c.isValid = true and CURDATE() BETWEEN c.startDate and c.endDate")
  List<CellEntity> getCellList(@Param("cellList") List<Long> cellList);
}

