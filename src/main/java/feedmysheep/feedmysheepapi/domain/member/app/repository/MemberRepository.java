package feedmysheep.feedmysheepapi.domain.member.app.repository;

import feedmysheep.feedmysheepapi.models.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

  @Query("SELECT EXISTS (SELECT 1 FROM MemberEntity m WHERE m.isActive = true and m.phone = :phone)")
  boolean existsMemberByPhone(@Param("phone") String phone);

  @Query("SELECT EXISTS (SELECT 1 FROM MemberEntity m WHERE m.isActive = true and m.email = :email)")
  boolean existsMemberByEmail(@Param("email") String email);
}
