package feedmysheep.feedmysheepapi.domain.church.app.repository;

import feedmysheep.feedmysheepapi.models.ChurchMemberMapEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChurchMemberMapRepository extends JpaRepository<ChurchMemberMapEntity, Long> {

  @Query("SELECT EXISTS (SELECT 1 FROM ChurchMemberMapEntity cmm WHERE cmm.isValid = true and cmm.memberId = :memberId)")
  boolean existsChurchMemberByMemberId(@Param("memberId") Long memberId);

//  @Query("SELECT EXISTS (SELECT 1 FROM MemberEntity m WHERE m.isActive = true and m.phone = :phone)")
//  boolean existsMemberByPhone(@Param("phone") String phone);
}
