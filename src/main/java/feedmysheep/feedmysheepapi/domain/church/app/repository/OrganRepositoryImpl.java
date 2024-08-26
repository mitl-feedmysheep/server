package feedmysheep.feedmysheepapi.domain.church.app.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import feedmysheep.feedmysheepapi.models.QOrganEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrganRepositoryImpl implements OrganRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  QOrganEntity organEntity = QOrganEntity.organEntity;

}
