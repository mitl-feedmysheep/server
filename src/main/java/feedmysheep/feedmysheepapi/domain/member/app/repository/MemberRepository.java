package feedmysheep.feedmysheepapi.domain.member.app.repository;

import feedmysheep.feedmysheepapi.models.MemberEntity;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, UUID>,
    MemberRepositoryCustom {

  Optional<MemberEntity> findByMemberId(UUID memberId);

  Optional<MemberEntity> findByEmail(String email);

  Optional<MemberEntity> findByPhone(String phone);

  Optional<MemberEntity> findByMemberNameAndBirthday(String memberName, LocalDate birthday);

  Optional<MemberEntity> findByEmailAndMemberName(String email, String memberName);
}
