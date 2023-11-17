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
@Table(name = "body")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BodyEntity extends CreatedUpdated {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "body_id", nullable = false, columnDefinition = "bigint COMMENT '바디 아이디'")
  private Long bodyId;

  @Column(name = "church_id", nullable = false)
  private Long churchId;

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

  @Setter
  @Column(name = "is_valid", nullable = false)
  private boolean isValid = true;

  @Column(name = "youtube_url", length = 100, columnDefinition = "varchar(100) COMMENT '유투브 주소'")
  private String youtubeUrl;

  @Column(name = "instagram_url", length = 100, columnDefinition = "varchar(100) COMMENT '인스타그램 주소'")
  private String instagramUrl;

  @Column(name = "facebook_url", length = 100, columnDefinition = "varchar(100) COMMENT '페이스북 주소'")
  private String facebookUrl;

  @Builder
  public BodyEntity(Long churchId, String bodyName, String bodyLogoUrl, String bodyLocation,
      String bodyNumber,
      String bodyDescription, String youtubeUrl, String instagramUrl, String facebookUrl) {
    this.churchId = churchId;
    this.bodyName = bodyName;
    this.bodyLogoUrl = bodyLogoUrl;
    this.bodyLocation = bodyLocation;
    this.bodyNumber = bodyNumber;
    this.bodyDescription = bodyDescription;
    this.youtubeUrl = youtubeUrl;
    this.instagramUrl = instagramUrl;
    this.facebookUrl = facebookUrl;
  }
}
