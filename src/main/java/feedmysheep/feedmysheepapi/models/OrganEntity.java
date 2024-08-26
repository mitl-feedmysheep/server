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
@Table(name = "organ")
@Getter
@Where(clause = "deleted_at is null")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrganEntity extends BaseEntity implements Persistable<UUID> {

  @Id
  @Column(columnDefinition = "BINARY(16)", name = "organ_id")
  private UUID organId = UuidCreator.getTimeOrdered();

  @Column(name = "body_id", nullable = false, columnDefinition = "BINARY(16)")
  private UUID bodyId;

  @Column(name = "organ_name", length = 50, nullable = false)
  private String organName;

  @Setter
  @Column(name = "organ_logo_url", length = 200)
  private String organLogoUrl;

  @Setter
  @Column(name = "organ_role", length = 200)
  private String organRole;

  @Setter
  @Column(name = "organ_words", length = 200)
  private String organWords;

  @Setter
  @Column(name = "organ_goal", length = 200)
  private String organGoal;

  @Setter
  @Column(name = "organ_description", length = 200)
  private String organDescription;

  @Builder
  OrganEntity(UUID bodyId, String organName, String organLogoUrl, String organRole,
      String organWords, String organGoal, String organDescription) {
    this.bodyId = bodyId;
    this.organName = organName;
    this.organLogoUrl = organLogoUrl;
    this.organRole = organRole;
    this.organWords = organWords;
    this.organGoal = organGoal;
    this.organDescription = organDescription;
  }

  @Nullable
  @Override
  public UUID getId() {
    return this.organId;
  }

  @Override
  public boolean isNew() {
    return this.getCreatedAt() == null;
  }
}
