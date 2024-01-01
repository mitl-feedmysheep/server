package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "media")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MediaEntity extends CreatedUpdated {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "media_id", nullable = false, columnDefinition = "bigint COMMENT '미디어 아이디'")
    private Long mediaId;

    @Column(name = "screen_key", length = 50, nullable = false)
    private String screenKey;

    @Setter
    @Column(name = "is_valid", nullable = false, columnDefinition = "tinyint(1) NOT NULL COMMENT '유효여부'")
    private boolean isValid = true;

    @Column(name = "sort", nullable = false)
    private int sort;

    @Column(name = "media_url", length = 2048, nullable = false)
    private String mediaUrl;

    @Builder
    public MediaEntity(String screenKey, int sort, String mediaUrl) {
      this.screenKey = screenKey;
      this.sort = sort;
      this.mediaUrl =  mediaUrl;
    }
}
