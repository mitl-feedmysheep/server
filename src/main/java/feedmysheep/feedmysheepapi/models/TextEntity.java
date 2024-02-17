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
@Table(name = "text")
@Getter
@Where(clause = "deleted_at is null")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TextEntity extends BaseEntity implements Persistable<UUID> {

  @Id
  @Column(columnDefinition = "BINARY(16)", name = "text_id")
  private UUID textId = UuidCreator.getTimeOrdered();

  @Column(name = "screen_key", length = 50, nullable = false)
  private String screenKey;

  @Column(name = "text", length = 2048, nullable = false)
  private String text;

  @Builder
  public TextEntity(String screenKey, String text) {
    this.screenKey = screenKey;
    this.text = text;
  }

  @Nullable
  @Override
  public UUID getId() {
    return this.textId;
  }

  @Override
  public boolean isNew() {
    return this.getCreatedAt() == null;
  }
}
