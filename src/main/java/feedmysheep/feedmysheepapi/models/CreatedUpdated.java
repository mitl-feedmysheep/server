package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@MappedSuperclass
public class CreatedUpdated {

  @Column(name = "created_at", nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시'")
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "created_by", nullable = false, columnDefinition = "int DEFAULT 0 COMMENT '생성자'")
  private int createdBy;

  @Setter
  @Column(name = "updated_at", nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시'")
  private LocalDateTime updatedAt = LocalDateTime.now();

  @Setter
  @Column(name = "updated_by", nullable = false, columnDefinition = "int DEFAULT 0 COMMENT '수정자'")
  private int updatedBy;
}
