package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "body")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BodyEntity extends CreatedUpdated {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "body_id", nullable = false, columnDefinition = "bigint COMMENT '바디 아이디'")
  private Long bodyId;

  @ManyToOne
  @JoinColumn(name = "church_id", nullable = false)
  private ChurchEntity church;

  @Column(name = "body_name", nullable = false, length = 50, columnDefinition = "varchar(50) COMMENT '바디 이름 (ex. 새청)'")
  private String bodyName;

  @Column(name = "body_logo_url", length = 200, columnDefinition = "varchar(200) COMMENT '바디 로고 URL'")
  private String bodyLogoUrl;

  @Column(name = "body_location", nullable = false, length = 200, columnDefinition = "varchar(200) COMMENT '바디 위치'")
  private String bodyLocation;

  @Column(name = "body_number", length = 20, columnDefinition = "varchar(20) COMMENT '바디 전화번호'")
  private String bodyNumber;

  @Column(name = "body_description", length = 100, columnDefinition = "varchar(100) COMMENT '설명'")
  private String bodyDescription;

  @Column(name = "is_valid", nullable = false)
  private boolean isValid = true;

  @Column(name = "youtube_url", length = 100, columnDefinition = "varchar(100) COMMENT '유투브 주소'")
  private String youtubeUrl;

  @Column(name = "instagram_url", length = 100, columnDefinition = "varchar(100) COMMENT '인스타그램 주소'")
  private String instagramUrl;

  @Column(name = "facebook_url", length = 100, columnDefinition = "varchar(100) COMMENT '페이스북 주소'")
  private String facebookUrl;

  @OneToMany(mappedBy = "body")
  private List<OrganEntity> organList = new ArrayList<>();
}
