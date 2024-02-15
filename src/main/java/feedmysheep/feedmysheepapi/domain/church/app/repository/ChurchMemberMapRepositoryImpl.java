package feedmysheep.feedmysheepapi.domain.church.app.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import feedmysheep.feedmysheepapi.models.QChurchMemberMapEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChurchMemberMapRepositoryImpl implements ChurchMemberMapRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  QChurchMemberMapEntity churchMemberMapEntity = QChurchMemberMapEntity.churchMemberMapEntity;
}
