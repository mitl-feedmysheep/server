package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.*;
import java.util.Date;
import lombok.AccessLevel;
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

  @ManyToOne
  @JoinColumn(name = "church_id", referencedColumnName = "church_id")
  private ChurchEntity church;

  @ManyToOne
  @JoinColumn(name = "member_id", referencedColumnName = "member_id")
  private MemberEntity member;

  @Column(name = "is_valid")
  private boolean isValid = true;

  @Column(name = "serving_start_date")
  @Temporal(TemporalType.DATE)
  private Date servingStartDate;

  @Column(name = "serving_end_date")
  @Temporal(TemporalType.DATE)
  private Date servingEndDate;

  @Column(name = "invalid_reason", length = 50)
  private String invalidReason;
}
