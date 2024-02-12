package feedmysheep.feedmysheepapi.models;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
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
@Table(name = "cell_member_map")
@Getter
@Where(clause = "deleted_at is null")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CellMemberMapEntity extends CreatedUpdated implements Persistable<UUID> {

  @Id
  @Column(columnDefinition = "BINARY(16)", name = "cell_member_map_id")
  private UUID cellMemberMapId = UuidCreator.getTimeOrdered();

  @Column(name = "cell_id", nullable = false, columnDefinition = "BINARY(16)")
  private UUID cellId;

  @Column(name = "member_id", nullable = false, columnDefinition = "BINARY(16)")
  private UUID memberId;

  @Setter
  @Column(name = "is_leader", nullable = false, columnDefinition = "tinyint(1) NOT NULL COMMENT '리더여부'")
  private boolean isLeader = false;

  @Setter
  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @Setter
  @Column(name = "end_date", nullable = false)
  private LocalDate endDate;

  @Setter
  @Column(name = "delete_reason", length = 50, nullable = true)
  private String deleteReason;

  @Builder
  public CellMemberMapEntity(UUID cellId, UUID memberId, boolean isLeader, LocalDate startDate,
      LocalDate endDate) {
    this.cellId = cellId;
    this.memberId = memberId;
    this.isLeader = isLeader;
    // Default: 이번 해 첫 날로 지정
    this.startDate = (startDate != null) ? startDate : LocalDate.now().withDayOfYear(1);
    // Default: 이번 해 마지막 날로 지정
    this.endDate = (endDate != null) ? endDate : LocalDate.now().withDayOfYear(365);
  }

  @Nullable
  @Override
  public UUID getId() {
    return this.cellMemberMapId;
  }

  @Override
  public boolean isNew() {
    return this.getCreatedAt() == null;
  }
}
