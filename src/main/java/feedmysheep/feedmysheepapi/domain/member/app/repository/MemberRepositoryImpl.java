package feedmysheep.feedmysheepapi.domain.member.app.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import feedmysheep.feedmysheepapi.models.QMemberEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  QMemberEntity member = QMemberEntity.memberEntity;

  @Override
  public List<MemberEntity> findAllByMemberIdListAndMonth(List<UUID> memberIdList, Integer month,
      Integer offset, Integer limit) {
    return this.queryFactory.selectFrom(member)
        .where(member.memberId.in(memberIdList).and(member.birthday.month().eq(month)))
        .offset((long) offset).limit(limit).fetch();
  }

  @Override
  public List<MemberEntity> findAllByMemberIdList(List<UUID> memberIdList) {
    return this.queryFactory.selectFrom(member).where(member.memberId.in(memberIdList)).fetch();
  }

  @Override
  public void updatePasswordByMemberId(UUID memberId, String newPassword) {
    this.queryFactory.update(member).where(member.memberId.eq(memberId))
        .set(member.password, newPassword).set(member.updatedBy, memberId).execute();
  }

  @Override
  public void deactivate(UUID memberId) {
    this.queryFactory.update(member).where(member.memberId.eq(memberId))
        .set(member.deletedAt, LocalDateTime.now()).set(member.deletedBy, memberId).execute();
  }
}
