package feedmysheep.feedmysheepapi.domain.church.app.repository;

import feedmysheep.feedmysheepapi.models.ChurchMemberMapEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChurchMemberMapRepository extends JpaRepository<ChurchMemberMapEntity, Long> {

  // TODO: 날짜 추가;;;
  @Query("SELECT cmm FROM ChurchMemberMapEntity cmm WHERE cmm.isValid = true and cmm.memberId = :memberId")
  List<ChurchMemberMapEntity> getChurchMemberMapListByMemberId(@Param("memberId") Long memberId);
}
