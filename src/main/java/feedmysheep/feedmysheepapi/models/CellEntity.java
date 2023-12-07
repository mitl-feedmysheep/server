package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table(name = "cell")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CellEntity extends CreatedUpdated {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cell_id", nullable = false, columnDefinition = "bigint COMMENT '셀 아이디")
  private Long cellId;

  @Column(name = "body_id", nullable = false, columnDefinition = "bigint COMMENT '바디 아이디")
  private Long bodyId;

  @Column(name = "cell_name", nullable = false, length = 50, columnDefinition = "varchar(50) COMMENT '바디 아이디")
  private String cellName;

  @Column(name = "cell_logo_url", length = 200, columnDefinition = "varchar(200) COMMENT '셀 로고 URL")
  private String cellLogoUrl;

  @Column(name = "cell_place", length = 50, columnDefinition = "varchar(50) COMMENT '셀 모임 일반적인 장소")
  private String cellPlace;

  @Column(name = "description", length = 100, columnDefinition = "varchar(100) COMMENT '셀 설명")
  private String description;

  @Setter
  @Column(name = "is_valid", nullable = false)
  private boolean isValid = true;

  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @Column(name = "end_date", nullable = false)
  private LocalDate endDate;

  @Builder
  public CellEntity(Long cellId, Long bodyId, String cellName, String cellLogoUrl, String description, LocalDate startDate, LocalDate endDate) {
    this.cellId = cellId;
    this.bodyId = bodyId;
    this.cellName = cellName;
    this.cellLogoUrl = cellLogoUrl;
    this.description = description;
    this.startDate = startDate;
    this.endDate = endDate;
  }
}
