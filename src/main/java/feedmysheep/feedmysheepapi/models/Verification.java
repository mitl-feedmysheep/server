package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "verification")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Verification extends CreatedUpdated {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "verification_id")
  private Long verificationId;

  @Column(name = "phone", nullable = false, length = 20)
  private String phone;

  @Column(name = "verification_code", nullable = false, length = 6)
  private String verificationCode;

  @Column(name = "is_valid", nullable = false)
  private boolean isValid = true;

  @Column(name = "valid_date", nullable = false)
  private LocalDate validDate;

  @Builder
  public Verification(String phone, String verificationCode, LocalDate validDate) {
    this.phone = phone;
    this.verificationCode = verificationCode;
    this.validDate = validDate;
  }

//  @Override
//  public void setCreatedAt(LocalDateTime createdAt) {
//    super.setCreatedAt(createdAt);
//  }
}
