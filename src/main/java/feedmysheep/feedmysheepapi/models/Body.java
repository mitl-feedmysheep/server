package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.*;

@Entity
@Table(name = "body")
public class Body extends CreatedUpdated {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "body_id", nullable = false, columnDefinition = "bigint COMMENT '바디 아이디'")
  private Long bodyId;

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

  @Column(name = "youtube_url", length = 100, columnDefinition = "varchar(100) COMMENT '유투브 주소'")
  private String youtubeUrl;

  @Column(name = "instagram_url", length = 100, columnDefinition = "varchar(100) COMMENT '인스타그램 주소'")
  private String instagramUrl;

  @Column(name = "facebook_url", length = 100, columnDefinition = "varchar(100) COMMENT '페이스북 주소'")
  private String facebookUrl;
}
