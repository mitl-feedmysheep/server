package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@MappedSuperclass
public class CreatedUpdated {

  @Column(name = "created_at", nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시'")
  private LocalDateTime createdAt;

  @Setter
  @Column(name = "created_by", nullable = false, columnDefinition = "BINARY(16) DEFAULT 0 COMMENT '생성자'")
  private UUID createdBy;

  @Column(name = "updated_at", nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시'")
  private LocalDateTime updatedAt;

  @Setter
  @Column(name = "updated_by", nullable = false, columnDefinition = "BINARY(16) DEFAULT 0 COMMENT '수정자'")
  private UUID updatedBy;

  @Setter
  @Column(name = "deleted_at", nullable = true, columnDefinition = "datetime COMMENT '삭제일시'")
  private LocalDateTime deletedAt;

  @Setter
  @Column(name = "deleted_by", nullable = true, columnDefinition = "BINARY(16) COMMENT '삭제자'")
  private UUID deletedBy;
}
