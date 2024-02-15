package feedmysheep.feedmysheepapi.domain.church.app.repository;

import feedmysheep.feedmysheepapi.models.ChurchEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChurchRepository extends JpaRepository<ChurchEntity, UUID>,
    ChurchRepositoryCustom {

  List<ChurchEntity> findByChurchName(String churchName);

  Optional<ChurchEntity> findByChurchId(UUID churchId);
}
