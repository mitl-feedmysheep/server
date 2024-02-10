package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cell_gathering")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CellGatheringEntity extends CreatedUpdated {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cell_gathering_id", nullable = false, columnDefinition = "bigint COMMENT '셀모임 아이디'")
  private Long cellGatheringId;

  @Column(name = "cell_id", nullable = false, columnDefinition = "bigint COMMENT '셀 아이디'")
  private Long cellId;

  @Setter
  @Column(name = "is_valid", nullable = false, columnDefinition = "tinyint(1) DEFAULT 0 NOT NULL COMMENT '유효여부'")
  private boolean isValid = true;

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
  public CellGatheringEntity(Long cellId, boolean isValid, String gatheringTitle,
      LocalDate gatheringDate, LocalDateTime startedAt, LocalDateTime endedAt,
      String gatheringPlace, String gatheringPhotoUrl, String description, String leaderComment,
      String pastorComment) {
    this.cellId = cellId;
    this.isValid = isValid;
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
}


