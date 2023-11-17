package feedmysheep.feedmysheepapi.domain.church.app.repository;

import feedmysheep.feedmysheepapi.models.ChurchEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChurchRepository extends JpaRepository<ChurchEntity, Long> {

  @Query("SELECT c FROM ChurchEntity c WHERE c.isValid = true")
  List<ChurchEntity> getChurchList();

  @Query("SELECT c FROM ChurchEntity c WHERE c.isValid = true and c.churchName = :churchName")
  List<ChurchEntity> getChurchListByChurchName(@Param("churchName") String churchName);
}
