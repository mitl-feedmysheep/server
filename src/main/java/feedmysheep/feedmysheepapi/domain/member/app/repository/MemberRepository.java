package feedmysheep.feedmysheepapi.domain.member.app.repository;

import feedmysheep.feedmysheepapi.models.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
  boolean existsMemberByPhone(String phone);
}
