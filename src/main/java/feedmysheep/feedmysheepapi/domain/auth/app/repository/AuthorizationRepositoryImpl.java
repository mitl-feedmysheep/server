package feedmysheep.feedmysheepapi.domain.auth.app.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import feedmysheep.feedmysheepapi.models.QAuthorizationEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthorizationRepositoryImpl implements AuthorizationRepositoryCustom {

  private final JPAQueryFactory queryFactory;
  QAuthorizationEntity authorization = QAuthorizationEntity.authorizationEntity;

  @Override
  public Optional<AuthorizationEntity> getByAuthorizationId(Long authorizationId) {
    return Optional.ofNullable(queryFactory.selectFrom(authorization)
        .where(authorization.authorizationId.eq(authorizationId)).fetchOne());
  }

  @Override
  public Optional<AuthorizationEntity> getByLevel(int level) {
    return Optional.ofNullable(
        queryFactory.selectFrom(authorization).where(authorization.level.eq(level)).fetchOne());
  }
}
