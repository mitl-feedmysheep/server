package feedmysheep.feedmysheepapi.domain.church.app.repository;

import feedmysheep.feedmysheepapi.models.OrganEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganRepository extends JpaRepository<OrganEntity, Long> {

  @Query("SELECT o FROM OrganEntity o WHERE o.bodyId = :bodyId and o.isValid = true")
  List<OrganEntity> getOrganListByBodyId(@Param("bodyId") Long bodyId);
}
