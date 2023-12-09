package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import feedmysheep.feedmysheepapi.models.CellEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CellRepository extends JpaRepository<CellEntity, Long> {

  // TODO 테스트코드
  @Query("SELECT c FROM CellEntity c WHERE c.organId IN (:organIdList)")
  List<CellEntity> getCellListByOrganIdList(@Param("organIdList") List<Long> organIdList);
}
