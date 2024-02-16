package feedmysheep.feedmysheepapi.domain.text.app.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import feedmysheep.feedmysheepapi.models.QTextEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TextRepositoryImpl implements TextRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  QTextEntity textEntity = QTextEntity.textEntity;

}
