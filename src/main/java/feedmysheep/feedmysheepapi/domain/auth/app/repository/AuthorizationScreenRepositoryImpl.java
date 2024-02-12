package feedmysheep.feedmysheepapi.domain.auth.app.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import feedmysheep.feedmysheepapi.models.QAuthorizationScreenEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthorizationScreenRepositoryImpl {

  private final JPAQueryFactory queryFactory;

  QAuthorizationScreenEntity authorizationScreen = QAuthorizationScreenEntity.authorizationScreenEntity;
}
