package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

public class CreatedUpdated {
  @Column(name = "created_at", nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시'")
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "created_by", nullable = false, columnDefinition = "int COMMENT '생성자'")
  private int createdBy;

  @Column(name = "updated_at", nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시'")
  private LocalDateTime updatedAt = LocalDateTime.now();

  @Column(name = "updated_by", nullable = false, columnDefinition = "int COMMENT '수정자'")
  private int updatedBy;
}
