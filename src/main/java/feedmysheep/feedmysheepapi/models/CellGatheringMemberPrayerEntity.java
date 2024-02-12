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
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.data.domain.Persistable;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "cell_gathering_member_prayer")
@Getter
@Where(clause = "deleted_at is null")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CellGatheringMemberPrayerEntity extends CreatedUpdated implements Persistable<UUID> {

  @Id
  @Column(columnDefinition = "BINARY(16)", name = "cell_gathering_member_prayer_id")
  private UUID cellGatheringMemberPrayerId = UuidCreator.getTimeOrdered();

  @Column(name = "cell_gathering_member_id", nullable = false, columnDefinition = "BINARY(16)")
  private UUID cellGatheringMemberId;

  @Setter
  @Column(name = "prayer_request", nullable = false, length = 100, columnDefinition = "varchar(100) COMMENT '기도제목'")
  private String prayerRequest;

  @Setter
  @Column(name = "is_answered", nullable = false, columnDefinition = "tinyint(1) DEFAULT 0 NOT NULL COMMENT '기도제목 응답 여부'")
  private boolean isAnswered = false;

  @Builder
  public CellGatheringMemberPrayerEntity(UUID cellGatheringMemberId, String prayerRequest,
      boolean isAnswered) {
    this.cellGatheringMemberId = cellGatheringMemberId;
    this.prayerRequest = prayerRequest;
    this.isAnswered = isAnswered;
  }

  @Nullable
  @Override
  public UUID getId() {
    return this.cellGatheringMemberPrayerId;
  }

  @Override
  public boolean isNew() {
    return this.getCreatedAt() == null;
  }
}
