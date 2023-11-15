package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "church_member_map")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChurchMemberMapEntity extends CreatedUpdated {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "church_member_map_id")
  private Long churchMemberMapId;

  //  @ManyToOne
//  @JoinColumn(name = "church_id", referencedColumnName = "church_id")
//  private ChurchEntity church;
//
//  @ManyToOne
//  @JoinColumn(name = "member_id", referencedColumnName = "member_id")
//  private MemberEntity member;
  
  @Column(name = "church_id", nullable = false)
  private Long churchId;

  @Column(name = "member_id", nullable = false)
  private Long memberId;

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
