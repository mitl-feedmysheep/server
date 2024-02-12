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
public interface BodyMemberMapRepository extends JpaRepository<BodyMemberMapEntity, UUID> {

  @Query("SELECT bmm FROM BodyMemberMapEntity bmm WHERE bmm.isValid = true and bmm.bodyId = :bodyId and bmm.memberId = :memberId")
  Optional<BodyMemberMapEntity> geValidBodyMemberMapByBodyIdAndMemberId(
      @Param("bodyId") Long bodyId, @Param("memberId") Long memberId);

  // TODO 테스트코드
  @Query("SELECT bmm FROM BodyMemberMapEntity bmm WHERE bmm.isValid = false and bmm.bodyId = :bodyId and bmm.memberId = :memberId")
  Optional<BodyMemberMapEntity> geInvalidBodyMemberMapByBodyIdAndMemberId(
      @Param("bodyId") Long bodyId, @Param("memberId") Long memberId);

  //TODO 테스트코드
  @Query("SELECT bmm FROM BodyMemberMapEntity bmm WHERE bmm.isValid = true and bmm.bodyId = :bodyId")
  List<BodyMemberMapEntity> getBodyMemberListByBodyId(@Param("bodyId") Long bodyId);
}
