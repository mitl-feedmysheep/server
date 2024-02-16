package feedmysheep.feedmysheepapi.domain.church.app.repository;

import feedmysheep.feedmysheepapi.models.ChurchMemberMapEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChurchMemberMapRepository extends JpaRepository<ChurchMemberMapEntity, UUID> {

  List<ChurchMemberMapEntity> findAllByMemberId(UUID memberId);

  Optional<ChurchMemberMapEntity> findByChurchIdAndMemberId(UUID churchId, UUID memberId);

  // TODO 이거 어떻게 할지... 고민해보기
  @Query("SELECT cmm FROM ChurchMemberMapEntity cmm WHERE cmm.deletedAt is not null and cmm.churchId = :churchId and cmm.memberId = :memberId")
  Optional<ChurchMemberMapEntity> getInvalidChurchMemberMapByChurchIdAndMemberId(
      @Param("churchId") UUID churchId, @Param("memberId") UUID memberId);
}
