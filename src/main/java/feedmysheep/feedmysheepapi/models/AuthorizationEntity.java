package feedmysheep.feedmysheepapi.models;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.data.domain.Persistable;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "authorization")
@Getter
@Where(clause = "deleted_at is null")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorizationEntity extends CreatedUpdated  implements Persistable<UUID> {

  @Id
  @Column(columnDefinition = "BINARY(16)", name = "authorization_id")
  private UUID authorizationId = UuidCreator.getTimeOrdered();

  @Setter
  @Column(name = "level", nullable = false)
  private int level = 0;

  @Column(name = "level_name", length = 20, nullable = false)
  private String levelName;

  @Builder
  public AuthorizationEntity(int level, String levelName) {
    this.level = level;
    this.levelName = levelName;
  }

  @Nullable
  @Override
  public UUID getId() {
    return this.authorizationId;
  }

  @Override
  public boolean isNew() {
    return this.getCreatedAt() == null;
  }
}
