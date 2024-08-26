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
import org.hibernate.annotations.Where;
import org.springframework.data.domain.Persistable;
import org.springframework.lang.Nullable;


@Entity
@Table(name = "authorization_screen")
@Getter
@Where(clause = "deleted_at is null")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorizationScreenEntity extends BaseEntity implements Persistable<UUID> {

  @Id
  @Column(columnDefinition = "BINARY(16)", name = "authorization_screen_id")
  private UUID authorizationScreenId = UuidCreator.getTimeOrdered();

  @Column(name = "authorization_id", nullable = false, columnDefinition = "BINARY(16)")
  private UUID authorizationId;

  @Column(name = "screen_key", nullable = false, length = 50)
  private String screenKey;

  @Builder
  public AuthorizationScreenEntity(UUID authorizationId, String screenKey) {
    this.authorizationId = authorizationId;
    this.screenKey = screenKey;
  }

  @Nullable
  @Override
  public UUID getId() {
    return this.authorizationScreenId;
  }

  @Override
  public boolean isNew() {
    return this.getCreatedAt() == null;
  }
}
