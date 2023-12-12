package feedmysheep.feedmysheepapi.domain.church.app.repository;

import feedmysheep.feedmysheepapi.models.OrganMemberMapEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganMemberMapRepository extends JpaRepository<OrganMemberMapEntity, Long> {

  // TODO 테스트코드
  @Query("SELECT omm FROM OrganMemberMapEntity omm WHERE omm.isValid = true and omm.memberId = :memberId and omm.organId IN (:organIdList)")
  List<OrganMemberMapEntity> getOrganMemberMapListByOrganIdListAndMemberId(
      @Param("organIdList") List<Long> organIdList, @Param("memberId") Long memberId);
}
