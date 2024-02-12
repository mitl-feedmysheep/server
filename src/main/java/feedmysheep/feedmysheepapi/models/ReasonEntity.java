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
@Table(name = "reason")
@Getter
@Where(clause = "deleted_at is null")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReasonEntity extends CreatedUpdated implements Persistable<UUID> {

  @Id
  @Column(columnDefinition = "BINARY(16)", name = "reason_id")
  private UUID reasonId = UuidCreator.getTimeOrdered();

  @Column(name = "screen_key", nullable = false, length = 50)
  private String screenKey;

  @Column(name = "reason", length = 50, nullable = false)
  private String reason;

  @Builder
  public ReasonEntity(String screenKey, String reason) {
    this.screenKey = screenKey;
    this.reason = reason;
  }

  @Nullable
  @Override
  public UUID getId() {
    return this.reasonId;
  }

  @Override
  public boolean isNew() {
    return this.getCreatedAt() == null;
  }
}
