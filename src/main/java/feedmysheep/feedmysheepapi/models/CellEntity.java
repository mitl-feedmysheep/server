package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
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
  @Column(name = "cell_id", nullable = false, columnDefinition = "bigint COMMENT '셀 아이디'")
  private Long cellId;

  @Column(name = "organ_id", nullable = false, columnDefinition = "bigint COMMENT '기관 아이디'")
  private Long organId;

  @Column(name = "cell_name", nullable = false, length = 50, columnDefinition = "varchar(50) COMMENT '바디 아이디'")
  private String cellName;

  @Column(name = "cell_logo_url", length = 200, columnDefinition = "varchar(200) COMMENT '셀 로고 URL'")
  private String cellLogoUrl;

  @Column(name = "cell_place", length = 50, columnDefinition = "varchar(50) COMMENT '셀 모임 일반적인 장소'")
  private String cellPlace;

  @Column(name = "description", length = 100, columnDefinition = "varchar(100) COMMENT '셀 설명'")
  private String description;

  @Setter
  @Column(name = "is_valid", nullable = false)
  private boolean isValid = true;

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
  public CellEntity(Long organId, String cellName, String cellLogoUrl,
      String description, LocalDate startDate, LocalDate endDate) {
    this.organId = organId;
    this.cellName = cellName;
    this.cellLogoUrl = cellLogoUrl;
    this.description = description;
    // Default: 이번 해 첫 날로 지정
    this.startDate = (startDate != null) ? startDate : LocalDate.now().withDayOfYear(1);
    // Default: 이번 해 마지막 날로 지정
    this.endDate = (endDate != null) ? endDate : LocalDate.now().withDayOfYear(365);
  }
}
