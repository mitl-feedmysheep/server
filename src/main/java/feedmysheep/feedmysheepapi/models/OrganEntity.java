package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Date;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "organ")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrganEntity extends CreatedUpdated {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "organ_id")
  private Long organId;

  @Column(name = "body_id", nullable = false)
  private Long bodyId;

  @Column(name = "organ_name", length = 50, nullable = false)
  private String organName;

  @Setter
  @Column(name = "organ_logo_url", length = 200)
  private String organLogoUrl;

  @Setter
  @Column(name = "organ_role", length = 200)
  private String organRole;

  @Setter
  @Column(name = "organ_words", length = 200)
  private String organWords;

  @Setter
  @Column(name = "organ_goal", length = 200)
  private String organGoal;

  @Setter
  @Column(name = "organ_description", length = 200)
  private String organDescription;

  @Setter
  @Column(name = "is_valid", nullable = false)
  private boolean isValid = true;

  @Builder
  OrganEntity(Long bodyId, String organName, String organLogoUrl, String organRole,
      String organWords, String organGoal, String organDescription) {
    this.bodyId = bodyId;
    this.organName = organName;
    this.organLogoUrl = organLogoUrl;
    this.organRole = organRole;
    this.organWords = organWords;
    this.organGoal = organGoal;
    this.organDescription = organDescription;
  }

}
