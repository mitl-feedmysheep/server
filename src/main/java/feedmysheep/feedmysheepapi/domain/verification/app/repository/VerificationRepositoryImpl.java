package feedmysheep.feedmysheepapi.domain.verification.app.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import feedmysheep.feedmysheepapi.models.QVerificationEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VerificationRepositoryImpl implements VerificationRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  QVerificationEntity verification = QVerificationEntity.verificationEntity;

}
