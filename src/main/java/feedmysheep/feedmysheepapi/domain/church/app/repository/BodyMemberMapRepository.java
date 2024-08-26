package feedmysheep.feedmysheepapi.domain.church.app.repository;

import feedmysheep.feedmysheepapi.models.BodyMemberMapEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyMemberMapRepository extends JpaRepository<BodyMemberMapEntity, UUID>,
    BodyMemberMapRepositoryCustom {

  Optional<BodyMemberMapEntity> findByBodyIdAndMemberId(UUID bodyId, UUID memberId);

  // TODO 테스트코드 --> 이건 어떻게 할지 고민 좀..
  @Query("SELECT bmm FROM BodyMemberMapEntity bmm WHERE bmm.deletedAt is not null and bmm.bodyId = :bodyId and bmm.memberId = :memberId")
  Optional<BodyMemberMapEntity> geInvalidBodyMemberMapByBodyIdAndMemberId(
      @Param("bodyId") UUID bodyId, @Param("memberId") UUID memberId);

  List<BodyMemberMapEntity> findAllByBodyId(UUID bodyId);
}
