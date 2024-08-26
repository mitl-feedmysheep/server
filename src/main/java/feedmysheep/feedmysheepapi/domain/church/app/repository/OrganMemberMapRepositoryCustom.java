package feedmysheep.feedmysheepapi.domain.church.app.repository;

import feedmysheep.feedmysheepapi.models.OrganMemberMapEntity;
import java.util.List;
import java.util.UUID;

public interface OrganMemberMapRepositoryCustom {

  List<OrganMemberMapEntity> findAllByOrganIdListAndMemberId(List<UUID> organIdList, UUID memberId);
}
