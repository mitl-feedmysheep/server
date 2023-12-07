package feedmysheep.feedmysheepapi.domain.church.app.repository;

import feedmysheep.feedmysheepapi.models.OrganEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganRepository extends JpaRepository<OrganEntity, Long> {
  // TODO 테스트코드
  // TODO 날짜를 적어야 하는가....
  @Query("SELECT o FROM OrganEntity o")
  List<OrganEntity> getOrganListByBodyId(@Param("bodyId") Long bodyId);
}
