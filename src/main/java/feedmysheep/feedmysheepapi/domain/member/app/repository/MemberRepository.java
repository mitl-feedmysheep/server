package feedmysheep.feedmysheepapi.domain.member.app.repository;

import feedmysheep.feedmysheepapi.models.MemberEntity;
import java.time.LocalDate;
import java.util.List;
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

  //TODO 테스트 코드
  @Query("SELECT m FROM MemberEntity m WHERE m.isActive = true and m.memberId IN (:memberIdList) and MONTH(m.birthday) = :birthday")
  List<MemberEntity> getMemberListByMemberIdList(@Param("memberIdList") List<Long> memberIdList, @Param("birthday") int monthOfBirthday);

  //질문: List<Long> memberList를 (:memberIdList)라고 작성은 안하고, :memberIdList라고 작성을 하게되면,
  // (1), (2), (3) ... 이런식으로 검색을 하게된다는 거지?
}
