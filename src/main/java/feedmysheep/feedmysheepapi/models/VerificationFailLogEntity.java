package feedmysheep.feedmysheepapi.models;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.data.domain.Persistable;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "verification_fail_log")
@Getter
@Where(clause = "deleted_at is null")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VerificationFailLogEntity extends CreatedUpdated implements Persistable<UUID> {

  @Id
  @Column(columnDefinition = "BINARY(16)", name = "verification_fail_log_id")
  private UUID verificationFailLogId = UuidCreator.getTimeOrdered();

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

  @Nullable
  @Override
  public UUID getId() {
    return this.verificationFailLogId;
  }

  @Override
  public boolean isNew() {
    return this.getCreatedAt() == null;
  }
}
