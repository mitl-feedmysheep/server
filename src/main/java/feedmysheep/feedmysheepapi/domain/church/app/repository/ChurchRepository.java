package feedmysheep.feedmysheepapi.domain.church.app.repository;

import feedmysheep.feedmysheepapi.models.ChurchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChurchRepository extends JpaRepository<ChurchEntity, Long> {}
