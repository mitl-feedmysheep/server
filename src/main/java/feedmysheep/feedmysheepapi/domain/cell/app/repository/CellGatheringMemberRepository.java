package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import feedmysheep.feedmysheepapi.models.CellGatheringMemberEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CellGatheringMemberRepository extends
    JpaRepository<CellGatheringMemberEntity, UUID>, CellGatheringMemberRepositoryCustom {

  List<CellGatheringMemberEntity> findAllByCellGatheringMemberId(UUID cellGatheringMemberId);
}
