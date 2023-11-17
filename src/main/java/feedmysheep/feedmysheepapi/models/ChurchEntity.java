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
@Table(name = "church")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChurchEntity extends CreatedUpdated {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "church_id", nullable = false, columnDefinition = "bigint COMMENT '교회 아이디'")
  private Long churchId;

  @Column(name = "church_name", nullable = false, length = 50, columnDefinition = "varchar(50) COMMENT '교회 이름 (ex. 번동제일교회)'")
  private String churchName;

  @Column(name = "church_logo_url", length = 200, columnDefinition = "varchar(200) COMMENT '교회 로고 URL'")
  private String churchLogoUrl;

  @Column(name = "church_location", nullable = false, length = 200, columnDefinition = "varchar(200) COMMENT '교회 위치'")
  private String churchLocation;

  @Column(name = "church_number", length = 20, columnDefinition = "varchar(20) COMMENT '교회 전화번호'")
  private String churchNumber;

  @Column(name = "homepage_url", length = 100, columnDefinition = "varchar(100) COMMENT '홈페이지 주소'")
  private String homepageUrl;

  @Column(name = "church_description", length = 100, columnDefinition = "varchar(100) COMMENT '교회 설명'")
  private String churchDescription;

  @Setter
  @Column(name = "is_valid", nullable = false, columnDefinition = "tinyint(1) NOT NULL COMMENT '유효여부'")
  private boolean isValid = false;
  
//  @OneToMany(mappedBy = "church")
//  private List<BodyEntity> bodyList = new ArrayList<>();

//  @OneToMany(mappedBy = "church")
//  private List<ChurchMemberMapEntity> churchMemberList = new ArrayList<>();

  @Builder
  public ChurchEntity(String churchName, String churchLogoUrl, String churchLocation,
      String churchNumber, String homepageUrl, String churchDescription) {
    this.churchName = churchName;
    this.churchLogoUrl = churchLogoUrl;
    this.churchLocation = churchLocation;
    this.churchNumber = churchNumber;
    this.homepageUrl = homepageUrl;
    this.churchDescription = churchDescription;
  }
}