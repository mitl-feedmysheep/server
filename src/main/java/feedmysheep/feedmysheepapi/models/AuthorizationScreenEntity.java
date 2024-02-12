package feedmysheep.feedmysheepapi.models;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.Instant;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "authorization_screen")
@Getter
@Where(clause = "deleted_at is null")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorizationScreenEntity extends CreatedUpdated {
  
  @Id
  @Column(columnDefinition = "BINARY(16)", name = "authorization_screen_id")
  private UUID authorizationScreenId = UuidCreator.getTimeOrdered();

  @Column(name = "authorization_id", nullable = false)
  private Long authorizationId;

  @Column(name = "screen_key", nullable = false, length = 50)
  private String screenKey;

  @Builder
  public AuthorizationScreenEntity(Long authorizationId, String screenKey) {
    this.authorizationId = authorizationId;
    this.screenKey = screenKey;
  }
}