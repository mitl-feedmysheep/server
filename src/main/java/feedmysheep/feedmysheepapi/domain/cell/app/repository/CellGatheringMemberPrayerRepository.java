package feedmysheep.feedmysheepapi.domain.cell.app.repository;

import feedmysheep.feedmysheepapi.models.CellGatheringMemberPrayerEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CellGatheringMemberPrayerRepository extends
    JpaRepository<CellGatheringMemberPrayerEntity, UUID>,
    CellGatheringMemberPrayerRepositoryCustom {

  List<CellGatheringMemberPrayerEntity> findAllByCellGatheringMemberId(UUID cellGatheringMemberId);
}
