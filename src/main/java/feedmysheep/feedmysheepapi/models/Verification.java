package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "verification")
@Getter
@Setter
public class Verification extends CreatedUpdated {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "verification_id")
  private Long verificationId;

  @Column(name = "phone", nullable = false, length = 20)
  private String phone;

  @Column(name = "verification_code", nullable = false, length = 4)
  private String verificationCode;

  @Column(name = "is_valid", nullable = false)
  private boolean isValid = true;

  @Column(name = "valid_date", nullable = false)
  private LocalDate validDate;
}
