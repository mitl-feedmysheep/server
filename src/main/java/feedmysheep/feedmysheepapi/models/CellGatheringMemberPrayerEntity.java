package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cell_gathering_member_prayer")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CellGatheringMemberPrayerEntity extends CreatedUpdated {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cell_gathering_member_prayer_id", nullable = false, columnDefinition = "bigint COMMENT '셀모임멤버 기도제목 아이디'")
  private Long cellGatheringMemberPrayerId;

  @Column(name = "cell_gathering_member_id", nullable = false, columnDefinition = "bigint COMMENT '셀모임멤버 아이디'")
  private Long cellGatheringMemberId;

  @Setter
  @Column(name = "prayer_request", nullable = false, length = 100, columnDefinition = "varchar(100) COMMENT '기도제목'")
  private String prayerRequest;

  @Setter
  @Column(name = "is_answered", nullable = false, columnDefinition = "tinyint(1) DEFAULT 0 NOT NULL COMMENT '기도제목 응답 여부'")
  private boolean isAnswered = false;

  @Setter
  @Column(name = "is_valid", nullable = false, columnDefinition = "tinyint(1) DEFAULT 1 NOT NULL COMMENT '유효여부'")
  private boolean isValid = true;

  @Builder
  public CellGatheringMemberPrayerEntity(Long cellGatheringMemberId, String prayerRequest,
      boolean isAnswered) {
    this.cellGatheringMemberId = cellGatheringMemberId;
    this.prayerRequest = prayerRequest;
    this.isAnswered = isAnswered;
  }
}
