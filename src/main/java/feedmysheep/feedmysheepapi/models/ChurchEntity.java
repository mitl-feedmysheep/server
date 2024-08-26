package feedmysheep.feedmysheepapi.models;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.data.domain.Persistable;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "church")
@Getter
@Where(clause = "deleted_at is null")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChurchEntity extends BaseEntity implements Persistable<UUID> {

  @Id
  @Column(columnDefinition = "BINARY(16)", name = "church_id")
  private UUID churchId = UuidCreator.getTimeOrdered();

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

  @Transient
  @Setter
  List<BodyEntity> bodyList;

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


  @Nullable
  @Override
  public UUID getId() {
    return this.churchId;
  }

  @Override
  public boolean isNew() {
    return this.getCreatedAt() == null;
  }
}