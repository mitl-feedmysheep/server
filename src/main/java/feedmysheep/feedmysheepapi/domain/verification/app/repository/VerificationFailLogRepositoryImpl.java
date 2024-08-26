package feedmysheep.feedmysheepapi.domain.verification.app.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import feedmysheep.feedmysheepapi.models.QVerificationFailLogEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VerificationFailLogRepositoryImpl implements VerificationFailLogRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  QVerificationFailLogEntity verificationFailLog = QVerificationFailLogEntity.verificationFailLogEntity;

}
