package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "authorization_screen")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorizationScreenEntity extends CreatedUpdated {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "authorization_screen_id")
  private Long authorizationScreenId;

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