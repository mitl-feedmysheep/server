package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "authorization")
public class Authorization {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "authorization_id")
  private Long authorizationId;

  @Column(name = "level", nullable = false)
  private int level = 100;

  @Column(name = "level_name", length = 20, nullable = false)
  private String levelName;

  @OneToOne(mappedBy = "authorization")
  private Member member;
}
