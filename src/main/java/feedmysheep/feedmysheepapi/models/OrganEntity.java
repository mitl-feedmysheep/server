package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "organ")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrganEntity extends CreatedUpdated {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "organ_id")
  private Long organId;

  @Column(name = "member_id", nullable = false)
  private Long memberId;

  @Column(name = "body_id", nullable = false)
  private Long bodyId;

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
