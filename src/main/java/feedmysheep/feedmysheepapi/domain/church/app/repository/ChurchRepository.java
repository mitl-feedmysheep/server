package feedmysheep.feedmysheepapi.domain.church.app.repository;

import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchResDto;
import feedmysheep.feedmysheepapi.models.ChurchEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChurchRepository extends JpaRepository<ChurchEntity, Long> {

  @Query("SELECT c.churchId, c.churchName, c.churchLocation FROM ChurchEntity c WHERE c.isValid = true")
  List<ChurchResDto.getChurchList> getAllValidChurchList();
}
