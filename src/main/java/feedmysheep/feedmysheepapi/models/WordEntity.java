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
@Table(name = "word")
@Getter
@Where(clause = "deleted_at is null")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WordEntity extends CreatedUpdated implements Persistable<UUID> {

  @Id
  @Column(columnDefinition = "BINARY(16)", name = "word_id")
  private UUID wordId = UuidCreator.getTimeOrdered();

  @Column(name = "screen_key", nullable = false, length = 50, columnDefinition = "varchar(50) COMMENT '스크린 키'")
  private String screenKey;

  @Column(name = "words", nullable = false, length = 2048, columnDefinition = "varchar(2048) COMMENT '말씀'")
  private String words;

  @Builder
  public WordEntity(String screenKey, String words) {
    this.screenKey = screenKey;
    this.words = words;
  }

  @Nullable
  @Override
  public UUID getId() {
    return this.wordId;
  }

  @Override
  public boolean isNew() {
    return this.getCreatedAt() == null;
  }
}
