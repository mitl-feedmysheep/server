package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "church_member_map")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChurchMemberMapEntity extends CreatedUpdated {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "church_member_map_id")
  private Long churchMemberMapId;

  @Column(name = "church_id", nullable = false)
  private Long churchId;

  @Column(name = "member_id", nullable = false)
  private Long memberId;

  @Setter
  @Column(name = "is_valid")
  private boolean isValid = true;

  @Column(name = "serving_start_date", nullable = true)
  private LocalDate servingStartDate;

  @Setter
  @Column(name = "serving_end_date", nullable = true)
  private LocalDate servingEndDate;

  @Setter
  @Column(name = "invalid_reason", length = 50, nullable = true)
  private String invalidReason;

  @Builder
  public ChurchMemberMapEntity(Long churchId, Long memberId) {
    this.churchId = churchId;
    this.memberId = memberId;
  }
}
