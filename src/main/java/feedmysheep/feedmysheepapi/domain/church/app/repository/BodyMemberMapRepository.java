package feedmysheep.feedmysheepapi.domain.church.app.repository;

import feedmysheep.feedmysheepapi.models.BodyMemberMapEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyMemberMapRepository extends JpaRepository<BodyMemberMapEntity, Long> {

  @Query("SELECT bmm FROM BodyMemberMapEntity bmm WHERE bmm.isValid = true and bmm.bodyId = :bodyId and bmm.memberId = :memberId")
  Optional<BodyMemberMapEntity> getBodyMemberMapByBodyIdAndMemberId(@Param("bodyId") Long bodyId,
      @Param("memberId") Long memberId);
}
