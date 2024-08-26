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
@Table(name = "organ_member_map")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrganMemberMapEntity extends CreatedUpdated {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "organ_member_map_id")
  private Long organMemberMapId;

  @Column(name = "organ_id", nullable = false)
  private Long organId;

  @Column(name = "member_id", nullable = false)
  private Long memberId;

  @Setter
  @Column(name = "is_leader", nullable = false, columnDefinition = "tinyint(1) NOT NULL COMMENT '리더여부'")
  private boolean isLeader = false;

  @Setter
  @Column(name = "is_valid", nullable = false, columnDefinition = "tinyint(1) DEFAULT 0 NOT NULL COMMENT '유효여부'")
  private boolean isValid = false;

  @Setter
  @Column(name = "invalid_reason", length = 50, nullable = true)
  private String invalidReason;

  @Setter
  @Column(name = "invalid_at", nullable = true)
  private LocalDateTime invalidAt;

  @Builder
  public OrganMemberMapEntity(Long organId, Long memberId, boolean isLeader, boolean isValid) {
    this.organId = organId;
    this.memberId = memberId;
    this.isLeader = isLeader;
    this.isValid = isValid;
  }
}
