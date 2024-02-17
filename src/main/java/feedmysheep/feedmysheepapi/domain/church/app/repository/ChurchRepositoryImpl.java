package feedmysheep.feedmysheepapi.domain.church.app.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import feedmysheep.feedmysheepapi.models.QChurchEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChurchRepositoryImpl implements ChurchRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  QChurchEntity churchEntity = QChurchEntity.churchEntity;
}
