package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import feedmysheep.feedmysheepapi.models.CellGatheringEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CellGatheringRepository extends JpaRepository<CellGatheringEntity, UUID>, CellGatheringRepositoryCustom {

  List<CellGatheringEntity> findAllByCellId(UUID cellId);

  // TODO 이거 옵셔널하게 가져오는지 테스트 꼭 해보기
  Optional<CellGatheringEntity> findByCellGatheringId(UUID cellGatheringId);
}
