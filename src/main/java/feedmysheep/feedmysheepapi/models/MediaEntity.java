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
@Table(name = "media")
@Getter
@Where(clause = "deleted_at is null")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MediaEntity extends CreatedUpdated implements Persistable<UUID> {

  @Id
  @Column(columnDefinition = "BINARY(16)", name = "media_id")
  private UUID mediaId = UuidCreator.getTimeOrdered();

  @Column(name = "screen_key", length = 50, nullable = false)
  private String screenKey;

  @Column(name = "sort", nullable = false)
  private int sort;

  @Column(name = "media_url", length = 2048, nullable = false)
  private String mediaUrl;

  @Builder
  public MediaEntity(String screenKey, int sort, String mediaUrl) {
    this.screenKey = screenKey;
    this.sort = sort;
    this.mediaUrl = mediaUrl;
  }

  @Nullable
  @Override
  public UUID getId() {
    return this.mediaId;
  }

  @Override
  public boolean isNew() {
    return this.getCreatedAt() == null;
  }
}
