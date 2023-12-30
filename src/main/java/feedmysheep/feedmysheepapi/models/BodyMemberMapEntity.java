package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "body_member_map")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BodyMemberMapEntity extends CreatedUpdated {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "body_member_map_id")
  private Long bodyMemberMapId;

  @Column(name = "body_id", nullable = false)
  private Long bodyId;

  @Column(name = "member_id", nullable = false)
  private Long memberId;

  @Setter
  @Column(name = "is_leader", nullable = false)
  private boolean isLeader = false;

  @Setter
  @Column(name = "is_valid", nullable = false)
  private boolean isValid = true;

  @Setter
  @Column(name = "invalid_reason", length = 50, nullable = true)
  private String invalidReason;

  @Setter
  @Column(name = "invalid_at", nullable = true)
  private LocalDateTime invalidAt;

  @Builder
  public BodyMemberMapEntity(Long bodyId, Long memberId, boolean isLeader) {
    this.bodyId = bodyId;
    this.memberId = memberId;
    this.isLeader = isLeader;
  }
}
