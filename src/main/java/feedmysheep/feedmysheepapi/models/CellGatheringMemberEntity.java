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
@Table(name = "cell_gathering_member")
@Getter
@Where(clause = "deleted_at is null")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CellGatheringMemberEntity extends BaseEntity implements Persistable<UUID> {

  @Id
  @Column(columnDefinition = "BINARY(16)", name = "cell_gathering_member_id")
  private UUID cellGatheringMemberId = UuidCreator.getTimeOrdered();

  @Column(name = "cell_gathering_id", nullable = false, columnDefinition = "BINARY(16)")
  private UUID cellGatheringId;

  @Column(name = "cell_member_map_id", nullable = false, columnDefinition = "BINARY(16)")
  private UUID cellMemberMapId;

  @Setter
  @Column(name = "worship_attendance", nullable = false, columnDefinition = "tinyint(1) DEFAULT 0 NOT NULL COMMENT '예배 참석 여부'")
  private boolean worshipAttendance = false;

  @Setter
  @Column(name = "cell_gathering_attendance", nullable = false, columnDefinition = "tinyint(1) DEFAULT 0 NOT NULL COMMENT '셀모임 참석 여부'")
  private boolean cellGatheringAttendance = false;

  @Setter
  @Column(name = "story", nullable = true, length = 500)
  private String story;

  @Setter
  @Column(name = "leader_comment", nullable = true, length = 100)
  private String leaderComment;

  @Builder
  public CellGatheringMemberEntity(UUID cellGatheringId, UUID cellMemberMapId,
      boolean worshipAttendance, boolean cellGatheringAttendance, String story) {
    this.cellGatheringId = cellGatheringId;
    this.cellMemberMapId = cellMemberMapId;
    this.worshipAttendance = worshipAttendance;
    this.cellGatheringAttendance = cellGatheringAttendance;
    this.story = story;
  }

  @Nullable
  @Override
  public UUID getId() {
    return this.cellGatheringMemberId;
  }

  @Override
  public boolean isNew() {
    return this.getCreatedAt() == null;
  }
}
