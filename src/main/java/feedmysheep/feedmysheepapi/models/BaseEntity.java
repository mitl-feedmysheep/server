package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

  @CreatedDate
  @Column(updatable = false, name = "created_at", nullable = true, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시'")
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(updatable = false, name = "updated_at", nullable = true, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시'")
  private LocalDateTime updatedAt;

  @Setter
  @Column(name = "deleted_at", nullable = true, columnDefinition = "datetime COMMENT '삭제일시'")
  private LocalDateTime deletedAt;
}
