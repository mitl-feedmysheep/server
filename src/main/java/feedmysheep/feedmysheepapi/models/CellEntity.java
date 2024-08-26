package feedmysheep.feedmysheepapi.models;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDate;
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
@Table(name = "cell")
@Getter
@Where(clause = "deleted_at is null")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CellEntity extends BaseEntity implements Persistable<UUID> {

  @Id
  @Column(columnDefinition = "BINARY(16)", name = "cell_id")
  private UUID cellId = UuidCreator.getTimeOrdered();

  @Column(name = "organ_id", nullable = false, columnDefinition = "BINARY(16)")
  private UUID organId;

  @Column(name = "cell_name", nullable = false, length = 50, columnDefinition = "varchar(50) COMMENT '바디 아이디'")
  private String cellName;

  @Column(name = "cell_logo_url", length = 200, columnDefinition = "varchar(200) COMMENT '셀 로고 URL'")
  private String cellLogoUrl;

  @Column(name = "cell_place", length = 50, columnDefinition = "varchar(50) COMMENT '셀 모임 일반적인 장소'")
  private String cellPlace;

  @Column(name = "description", length = 100, columnDefinition = "varchar(100) COMMENT '셀 설명'")
  private String description;

  @Setter
  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @Setter
  @Column(name = "end_date", nullable = false)
  private LocalDate endDate;

  // DTO
  @Transient
  @Setter
  int cellMemberCount;

  @Builder
  public CellEntity(UUID organId, String cellName, String cellLogoUrl, String cellPlace,
      String description, LocalDate startDate, LocalDate endDate) {
    this.organId = organId;
    this.cellName = cellName;
    this.cellLogoUrl = cellLogoUrl;
    this.cellPlace = cellPlace;
    this.description = description;
    // Default: 이번 해 첫 날로 지정
    this.startDate = (startDate != null) ? startDate : LocalDate.now().withDayOfYear(1);
    // Default: 이번 해 마지막 날로 지정
    this.endDate = (endDate != null) ? endDate : LocalDate.now().withDayOfYear(365);
  }

  @Nullable
  @Override
  public UUID getId() {
    return this.cellId;
  }

  @Override
  public boolean isNew() {
    return this.getCreatedAt() == null;
  }
}
