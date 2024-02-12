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
@Table(name = "body_member_map")
@Getter
@Where(clause = "deleted_at is null")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BodyMemberMapEntity extends CreatedUpdated implements Persistable<UUID> {

  @Id
  @Column(columnDefinition = "BINARY(16)", name = "body_member_map_id")
  private UUID bodyMemberMapId = UuidCreator.getTimeOrdered();

  @Column(name = "body_id", nullable = false, columnDefinition = "BINARY(16)")
  private UUID bodyId;

  @Column(name = "member_id", nullable = false, columnDefinition = "BINARY(16)")
  private UUID memberId;

  @Setter
  @Column(name = "is_leader", nullable = false, columnDefinition = "tinyint(1) NOT NULL COMMENT '리더여부'")
  private boolean isLeader = false;

  @Setter
  @Column(name = "delete_reason", length = 50, nullable = true)
  private String deleteReason;

  @Builder
  public BodyMemberMapEntity(UUID bodyId, UUID memberId, boolean isLeader) {
    this.bodyId = bodyId;
    this.memberId = memberId;
    this.isLeader = isLeader;
  }

  @Nullable
  @Override
  public UUID getId() {
    return this.bodyId;
  }

  @Override
  public boolean isNew() {
    return this.getCreatedAt() == null;
  }
}
