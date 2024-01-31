package feedmysheep.feedmysheepapi.domain.member.app.repository;

import feedmysheep.feedmysheepapi.models.MemberEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

  @Query("SELECT m FROM MemberEntity m WHERE m.isActive = true and m.memberId = :memberId")
  Optional<MemberEntity> getMemberByMemberId(@Param("memberId") Long memberId);

  @Query("SELECT m FROM MemberEntity m WHERE m.isActive = true and m.email = :email")
  Optional<MemberEntity> getMemberByEmail(@Param("email") String email);

  @Query("SELECT m FROM MemberEntity m WHERE m.isActive = true and m.phone = :phone")
  Optional<MemberEntity> getMemberByPhone(@Param("phone") String phone);

  // TODO 테스트코드
  @Query("SELECT m FROM MemberEntity m WHERE m.isActive = true and m.memberId IN (:memberIdList)")
  List<MemberEntity> getMemberListByMemberIdList(List<Long> memberIdList);

  // TODO 테스트코드
  @Query("SELECT m FROM MemberEntity m WHERE m.isActive = true and m.memberName = :memberName and m.birthday = :birthday")
  Optional<MemberEntity> getMemberByMemberNameAndBirthday(@Param("memberName") String memberName,
      @Param("birthday") LocalDate birthday);

  // TODO 테스트코드
  @Transactional
  @Modifying
  @Query("UPDATE MemberEntity m SET m.password = :newPassword WHERE m.memberId = :memberId and m.updatedBy = :memberId")
  void updatePasswordByMemberId(@Param("memberId") Long memberId,
      @Param("newPassword") String newPassword);

  // TODO 테스트코드
  @Transactional
  @Modifying
  @Query("UPDATE MemberEntity m SET m.isActive = false, m.updatedBy = :memberId WHERE m.memberId = :memberId")
  void deactivate(@Param("memberId") Long memberId);
}
