package feedmysheep.feedmysheepapi.domain.church.app.repository;

import feedmysheep.feedmysheepapi.models.OrganMemberMapEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganMemberMapRepository extends JpaRepository<OrganMemberMapEntity, UUID>,
    OrganMemberMapRepositoryCustom {

}
