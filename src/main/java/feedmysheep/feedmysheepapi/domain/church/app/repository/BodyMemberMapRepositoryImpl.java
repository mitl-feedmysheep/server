package feedmysheep.feedmysheepapi.domain.church.app.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import feedmysheep.feedmysheepapi.models.QBodyMemberMapEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BodyMemberMapRepositoryImpl implements BodyMemberMapRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  QBodyMemberMapEntity bodyMemberMapEntity = QBodyMemberMapEntity.bodyMemberMapEntity;

}
