package feedmysheep.feedmysheepapi.domain.word.app.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import feedmysheep.feedmysheepapi.models.QWordEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WordRepositoryImpl implements WordRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  QWordEntity word = QWordEntity.wordEntity;

}
