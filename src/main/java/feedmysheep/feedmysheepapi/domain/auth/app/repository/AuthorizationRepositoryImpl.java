package feedmysheep.feedmysheepapi.domain.auth.app.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import feedmysheep.feedmysheepapi.models.QAuthorizationEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthorizationRepositoryImpl implements AuthorizationRepositoryCustom {

  private final JPAQueryFactory queryFactory;
  QAuthorizationEntity authorization = QAuthorizationEntity.authorizationEntity;
}
