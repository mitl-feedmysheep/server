package feedmysheep.feedmysheepapi.models;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.springframework.data.domain.Persistable;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "verification")
@Getter
@Where(clause = "deleted_at is null")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VerificationEntity extends CreatedUpdated implements Persistable<UUID> {

  @Id
  @Column(columnDefinition = "BINARY(16)", name = "verification_id")
  private UUID verificationId = UuidCreator.getTimeOrdered();

  @Column(name = "phone", nullable = false, length = 20)
  private String phone;

  @Column(name = "verification_code", nullable = false, length = 6)
  private String verificationCode;

  @Column(name = "valid_date", nullable = false, columnDefinition = "datetime COMMENT '유효날짜'")
  private LocalDate validDate = LocalDate.now();

  @Builder
  public VerificationEntity(String phone, String verificationCode) {
    this.phone = phone;
    this.verificationCode = verificationCode;
  }

  @Nullable
  @Override
  public UUID getId() {
    return this.verificationId;
  }

  @Override
  public boolean isNew() {
    return this.getCreatedAt() == null;
  }
}
