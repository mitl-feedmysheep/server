package feedmysheep.feedmysheepapi.domain.member.app.repository;

import feedmysheep.feedmysheepapi.models.MemberEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

  @Query("SELECT m FROM MemberEntity m WHERE m.isActive = true and m.memberId = :memberId")
  Optional<MemberEntity> getMemberByMemberId(@Param("memberId") Long memberId);

  @Query("SELECT m FROM MemberEntity m WHERE m.isActive = true and m.email = :email")
  Optional<MemberEntity> getMemberByEmail(@Param("email") String email);

  @Query("SELECT m FROM MemberEntity m WHERE m.isActive = true and m.phone = :phone")
  Optional<MemberEntity> getMemberByPhone(@Param("phone") String phone);
}
