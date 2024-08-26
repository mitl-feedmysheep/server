package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import feedmysheep.feedmysheepapi.models.CellGatheringEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CellGatheringRepository extends JpaRepository<CellGatheringEntity, UUID>,
    CellGatheringRepositoryCustom {

  List<CellGatheringEntity> findAllByCellId(UUID cellId);

  Optional<CellGatheringEntity> findByCellGatheringId(UUID cellGatheringId);
}
