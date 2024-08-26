package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cell_gathering_member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CellGatheringMemberEntity extends CreatedUpdated {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cell_gathering_member_id", nullable = false, columnDefinition = "bigint COMMENT '셀모임멤버 아이디'")
  private Long cellGatheringMemberId;

  @Column(name = "cell_gathering_id", nullable = false, columnDefinition = "bigint COMMENT '셀모임 아이디'")
  private Long cellGatheringId;

  @Column(name = "cell_member_map_id", nullable = false, columnDefinition = "bigint COMMENT '셀멤버 아이디'")
  private Long cellMemberMapId;

  @Setter
  @Column(name = "worship_attendance", nullable = false, columnDefinition = "tinyint(1) DEFAULT 0 NOT NULL COMMENT '예배 참석 여부'")
  private boolean worshipAttendance = false;

  @Setter
  @Column(name = "cell_gathering_attendance", nullable = false, columnDefinition = "tinyint(1) DEFAULT 0 NOT NULL COMMENT '셀모임 참석 여부'")
  private boolean cellGatheringAttendance = false;

  @Setter
  @Column(name = "story", nullable = false, length = 500)
  private String story;

  @Setter
  @Column(name = "leader_comment", nullable = false, length = 100)
  private String leaderComment;

  @Builder
  public CellGatheringMemberEntity(Long cellGatheringId, Long cellMemberMapId,
      boolean worshipAttendance, boolean cellGatheringAttendance, String story) {
    this.cellGatheringId = cellGatheringId;
    this.cellMemberMapId = cellMemberMapId;
    this.worshipAttendance = worshipAttendance;
    this.cellGatheringAttendance = cellGatheringAttendance;
    this.story = story;
  }
}
