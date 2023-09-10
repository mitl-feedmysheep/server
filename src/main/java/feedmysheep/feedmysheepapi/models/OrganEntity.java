package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.*;
import java.util.Date;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "organ")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrganEntity extends CreatedUpdated {

  @Id
  @Column(name = "organ_id")
  private Long organId;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private MemberEntity member;

  @ManyToOne
  @JoinColumn(name = "body_id")
  private BodyEntity body;

  @Column(name = "organ_name", length = 50, nullable = false)
  private String organName;

  @Column(name = "organ_logo_url", length = 200)
  private String organLogoUrl;

  @Column(name = "organ_role", length = 200, nullable = false)
  private String organRole;

  @Column(name = "organ_words", length = 200, nullable = false)
  private String organWords;

  @Column(name = "organ_goal", length = 200, nullable = false)
  private String organGoal;

  @Column(name = "organ_description", length = 200)
  private String organDescription;

  @Column(name = "is_valid", nullable = false)
  private boolean isValid;

  @Column(name = "start_date", nullable = false)
  private Date startDate;

  @Column(name = "end_date", nullable = false)
  private Date endDate;
}
