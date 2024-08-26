package feedmysheep.feedmysheepapi.domain.church.app.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import feedmysheep.feedmysheepapi.models.QBodyEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BodyRepositoryImpl implements BodyRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  QBodyEntity bodyEntity = QBodyEntity.bodyEntity;

}
