package feedmysheep.feedmysheepapi.models;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
@Table(name = "cell_gathering")
@Getter
@Where(clause = "deleted_at is null")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CellGatheringEntity extends CreatedUpdated implements Persistable<UUID> {

  @Id
  @Column(columnDefinition = "BINARY(16)", name = "cell_gathering_id")
  private UUID cellGatheringId = UuidCreator.getTimeOrdered();

  @Column(name = "cell_id", nullable = false, columnDefinition = "BINARY(16)")
  private UUID cellId;

  @Setter
  @Column(name = "gathering_title", nullable = false, length = 100)
  private String gatheringTitle;

  @Setter
  @Column(name = "gathering_date", nullable = false)
  private LocalDate gatheringDate;

  @Setter
  @Column(name = "started_at", nullable = false)
  private LocalDateTime startedAt;

  @Setter
  @Column(name = "ended_at", nullable = false)
  private LocalDateTime endedAt;

  @Setter
  @Column(name = "gathering_place", nullable = false, length = 50)
  private String gatheringPlace;

  @Setter
  @Column(name = "gathering_photo_url", length = 200)
  private String gatheringPhotoUrl;

  @Setter
  @Column(name = "description", length = 100)
  private String description;

  @Setter
  @Column(name = "leader_comment", nullable = true, length = 100)
  private String leaderComment;

  @Setter
  @Column(name = "pastor_comment", nullable = true, length = 100)
  private String pastorComment;

  @Builder
  public CellGatheringEntity(UUID cellId, String gatheringTitle, LocalDate gatheringDate,
      LocalDateTime startedAt, LocalDateTime endedAt, String gatheringPlace,
      String gatheringPhotoUrl, String description, String leaderComment, String pastorComment) {
    this.cellId = cellId;
    this.gatheringTitle = gatheringTitle;
    this.gatheringDate = gatheringDate;
    this.startedAt = startedAt;
    this.endedAt = endedAt;
    this.gatheringPlace = gatheringPlace;
    this.gatheringPhotoUrl = gatheringPhotoUrl;
    this.description = description;
    this.leaderComment = leaderComment;
    this.pastorComment = pastorComment;
  }

  @Transient
  @Setter
  int numberOfGathering;

  @Transient
  @Setter
  String dayOfWeek;

  @Transient
  @Setter
  int totalWorshipAttendanceCount;

  @Transient
  @Setter
  int totalCellGatheringAttendanceCount;

  @Nullable
  @Override
  public UUID getId() {
    return this.cellGatheringId;
  }

  @Override
  public boolean isNew() {
    return this.getCreatedAt() == null;
  }
}


