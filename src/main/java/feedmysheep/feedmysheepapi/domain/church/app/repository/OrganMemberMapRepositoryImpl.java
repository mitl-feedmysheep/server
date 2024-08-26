package feedmysheep.feedmysheepapi.domain.church.app.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import feedmysheep.feedmysheepapi.models.OrganMemberMapEntity;
import feedmysheep.feedmysheepapi.models.QOrganMemberMapEntity;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrganMemberMapRepositoryImpl implements OrganMemberMapRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  QOrganMemberMapEntity organMemberMapEntity = QOrganMemberMapEntity.organMemberMapEntity;

  @Override
  public List<OrganMemberMapEntity> findAllByOrganIdListAndMemberId(List<UUID> organIdList,
      UUID memberId) {
    return queryFactory.selectFrom(organMemberMapEntity).where(
        organMemberMapEntity.organId.in(organIdList)
            .and(organMemberMapEntity.memberId.eq(memberId))).fetch();
  }
}
