package feedmysheep.feedmysheepapi.domain.church.app.repository;

import feedmysheep.feedmysheepapi.models.BodyEntity;
import feedmysheep.feedmysheepapi.models.BodyMemberMapEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
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

  //TODO 테스트코드
  @Query("SELECT bmm FROM BodyMemberMapEntity bmm WHERE bmm.isValid = true and bmm.bodyId = :bodyId")
  List<BodyMemberMapEntity> getMemberListByBodyId(@Param("bodyId") Long bodyId);

  //TODO 테스트코드
  @Query("SELECT bmm FROM BodyMemberMapEntity bmm WHERE bmm.isValid = true and bmm.bodyId = :bodyId")
  List<BodyMemberMapEntity> getEventMemberListByBodyId(@Param("bodyId") Long bodyId);
}
