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
@Table(name = "cell_member_map")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CellMemberMapEntity extends CreatedUpdated {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cell_member_map_id")
  private Long cellMemberMapId;

  @Column(name = "cell_id", nullable = false)
  private Long cellId;

  @Column(name = "member_id", nullable = false)
  private Long memberId;

  @Setter
  @Column(name = "is_leader", nullable = false, columnDefinition = "tinyint(1) NOT NULL COMMENT '리더여부'")
  private boolean isLeader = false;

  @Setter
  @Column(name = "is_valid", nullable = false, columnDefinition = "tinyint(1) DEFAULT 0 NOT NULL COMMENT '유효여부'")
  private boolean isValid = false;

  @Setter
  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @Setter
  @Column(name = "end_date", nullable = false)
  private LocalDate endDate;

  @Setter
  @Column(name = "invalid_reason", length = 50, nullable = true)
  private String invalidReason;

  @Setter
  @Column(name = "invalid_at", nullable = true)
  private LocalDateTime invalidAt;

  @Builder
  public CellMemberMapEntity(Long cellId, Long memberId, boolean isLeader, LocalDate startDate,
      LocalDate endDate, boolean isValid) {
    this.cellId = cellId;
    this.memberId = memberId;
    this.isLeader = isLeader;
    // Default: 이번 해 첫 날로 지정
    this.startDate = (startDate != null) ? startDate : LocalDate.now().withDayOfYear(1);
    // Default: 이번 해 마지막 날로 지정
    this.endDate = (endDate != null) ? endDate : LocalDate.now().withDayOfYear(365);
    this.isValid = isValid;
  }
}
