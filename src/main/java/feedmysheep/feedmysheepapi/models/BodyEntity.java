package feedmysheep.feedmysheepapi.models;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.springframework.data.domain.Persistable;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "body")
@Getter
@Where(clause = "deleted_at is null")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BodyEntity extends CreatedUpdated implements Persistable<UUID> {

  @Id
  @Column(columnDefinition = "BINARY(16)", name = "body_id")
  private UUID bodyId = UuidCreator.getTimeOrdered();

  @Column(name = "church_id", nullable = false, columnDefinition = "BINARY(16)")
  private UUID churchId;

  @Column(name = "body_name", nullable = false, length = 50, columnDefinition = "varchar(50) COMMENT '바디 이름 (ex. 새청)'")
  private String bodyName;

  @Column(name = "body_logo_url", length = 200, columnDefinition = "varchar(200) COMMENT '바디 로고 URL'")
  private String bodyLogoUrl;

  @Column(name = "body_location", length = 200, columnDefinition = "varchar(200) COMMENT '바디 위치'")
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


  @Builder
  public BodyEntity(UUID churchId, String bodyName, String bodyLogoUrl, String bodyLocation,
      String bodyNumber, String bodyDescription, String youtubeUrl, String instagramUrl,
      String facebookUrl) {
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

  @Nullable
  @Override
  public UUID getId() {
    return this.bodyId;
  }

  @Override
  public boolean isNew() {
    return this.getCreatedAt() == null;
  }
}
