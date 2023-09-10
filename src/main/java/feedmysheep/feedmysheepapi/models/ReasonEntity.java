package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reason")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReasonEntity extends CreatedUpdated {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "reason_id")
  private Long reasonId;

  @Column(name = "main_screen", length = 50, nullable = false)
  private String mainScreen;

  @Column(name = "sub_screen", length = 50)
  private String subScreen;

  @Column(name = "is_valid", nullable = false)
  private boolean isValid = true;

  @Column(name = "reason", length = 50, nullable = false)
  private String reason;
}
