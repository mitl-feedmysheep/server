package feedmysheep.feedmysheepapi.domain.church.app.repository;

import feedmysheep.feedmysheepapi.models.ChurchMemberMapEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChurchMemberMapRepository extends JpaRepository<ChurchMemberMapEntity, Long> {

  @Query("SELECT cmm FROM ChurchMemberMapEntity cmm WHERE cmm.isValid = true and cmm.memberId = :memberId")
  List<ChurchMemberMapEntity> getChurchMemberMapListByMemberId(@Param("memberId") Long memberId);

  //TODO 테스트코드
  @Query("SELECT cmm FROM ChurchMemberMapEntity cmm WHERE cmm.isValid = true and cmm.churchId = :churchId and cmm.memberId = :memberId")
  Optional<ChurchMemberMapEntity> getValidChurchMemberMapByChurchIdAndMemberId(
      @Param("churchId") Long churchId, @Param("memberId") Long memberId);

  //TODO 테스트코드
  @Query("SELECT cmm FROM ChurchMemberMapEntity cmm WHERE cmm.isValid = false and cmm.churchId = :churchId and cmm.memberId = :memberId")
  Optional<ChurchMemberMapEntity> getInvalidChurchMemberMapByChurchIdAndMemberId(
      @Param("churchId") Long churchId, @Param("memberId") Long memberId);
}
