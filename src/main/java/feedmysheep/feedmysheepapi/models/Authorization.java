package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "authorization")
@Getter
@Setter
public class Authorization extends CreatedUpdated {

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
