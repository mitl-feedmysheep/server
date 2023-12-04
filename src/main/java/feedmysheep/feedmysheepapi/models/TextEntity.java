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
@Table(name = "text")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TextEntity extends CreatedUpdated {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "text_id", nullable = false, columnDefinition = "bigint COMMENT '텍스트 아이디'")
    private Long textId;

    @Column(name = "screen_key", length = 50, nullable = false)
    private String screenKey;

    @Setter
    @Column(name = "is_valid", nullable = false)
    private boolean isValid;

    @Column(name = "text", length = 2048, nullable = false)
    private String text;

    @Builder
    public TextEntity(String screenKey, String text) {
      this.screenKey = screenKey;
      this.text = text;
    }
}
