package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "verification_fail_log")
@Getter
@Setter
public class VerificationFailLogEntity extends CreatedUpdated {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "verification_fail_log_id")
  private Long verificationFailLogId;

  @Column(name = "phone", nullable = false, length = 20)
  private String phone;

  @Column(name = "verification_code", nullable = false, length = 4)
  private String verificationCode;

  @Column(name = "is_failed", nullable = false)
  private boolean isFailed = true;
}
