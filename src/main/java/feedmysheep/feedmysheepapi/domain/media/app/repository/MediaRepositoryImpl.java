package feedmysheep.feedmysheepapi.domain.media.app.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import feedmysheep.feedmysheepapi.models.QMediaEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MediaRepositoryImpl implements MediaRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  QMediaEntity mediaEntity = QMediaEntity.mediaEntity;

}
