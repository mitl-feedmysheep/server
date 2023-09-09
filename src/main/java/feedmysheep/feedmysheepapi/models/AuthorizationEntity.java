package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "authorization")
@Getter
@Setter
public class AuthorizationEntity extends CreatedUpdated {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "authorization_id")
  private Long authorizationId;

  @Column(name = "level", nullable = false)
  private int level = 100;

  @Column(name = "level_name", length = 20, nullable = false)
  private String levelName;

  @OneToOne(mappedBy = "authorization")
  private MemberEntity member;
}
