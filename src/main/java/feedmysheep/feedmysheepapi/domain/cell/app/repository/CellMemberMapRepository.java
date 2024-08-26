package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import feedmysheep.feedmysheepapi.models.CellMemberMapEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CellMemberMapRepository extends JpaRepository<CellMemberMapEntity, UUID>,
    CellMemberMapRepositoryCustom {

  Optional<CellMemberMapEntity> findByCellMemberMapId(UUID cellMemberMapId);
}
