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
@Table(name = "authorization")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorizationEntity extends CreatedUpdated {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "authorization_id")
  private Long authorizationId;

  @Setter
  @Column(name = "level", nullable = false)
  private int level = 0;

  @Column(name = "level_name", length = 20, nullable = false)
  private String levelName;

  @Builder
  public AuthorizationEntity(int level, String levelName) {
    this.level = level;
    this.levelName = levelName;
  }
}
