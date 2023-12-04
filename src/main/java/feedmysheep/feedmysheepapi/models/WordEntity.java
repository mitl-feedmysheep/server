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

@Entity
@Table(name = "word")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WordEntity extends CreatedUpdated {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "word_id", nullable = false, columnDefinition = "bigint COMMENT '말씀 아이디'")
  private Long wordId;

  @Column(name = "screen_key", nullable = false, length = 50, columnDefinition = "varchar(50) COMMENT '스크린 키'")
  private String screenKey;

  @Column(name = "is_valid", nullable = false, columnDefinition = "tinyint(1) DEFAULT 1 COMMENT '활성화 여부'")
  private boolean isValid = true;

  @Column(name = "words", nullable = false, length = 2048, columnDefinition = "varchar(2048) COMMENT '말씀'")
  private String words;

  @Column(name = "book", nullable = false, length = 10, columnDefinition = "varchar(10) COMMENT '서'")
  private String book;

  @Column(name = "chapter", nullable = false, columnDefinition = "int COMMENT '장'")
  private int chapter;

  @Column(name = "verse", nullable = false, columnDefinition = "int COMMENT '절'")
  private int verse;

  @Builder
  public WordEntity(String screenKey, String book, int chapter, int verse,
      String words) {
    this.screenKey = screenKey;
    this.book = book;
    this.chapter = chapter;
    this.verse = verse;
    this.words = words;
  }
}
