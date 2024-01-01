package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "verification_fail_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VerificationFailLogEntity extends CreatedUpdated {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "verification_fail_log_id")
  private Long verificationFailLogId;

  @Column(name = "phone", nullable = false, length = 20)
  private String phone;

  // 유저입력 로그용
  @Column(name = "verification_code", nullable = false, length = 6)
  private String verificationCode;

  @Setter
  @Column(name = "is_failed", nullable = false, columnDefinition = "tinyint(1) NOT NULL COMMENT '유효여부'")
  private boolean isFailed = true;

  @Builder
  public VerificationFailLogEntity(String phone, String verificationCode) {
    this.phone = phone;
    this.verificationCode = verificationCode;
  }
}
