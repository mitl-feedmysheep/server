package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "word")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Word extends CreatedUpdated {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "word_id", nullable = false, columnDefinition = "bigint COMMENT '말씀 아이디'")
  private Long wordId;

  @Column(name = "main_screen", nullable = false, length = 50, columnDefinition = "varchar(50) COMMENT '메인 스크린'")
  private String mainScreen;

  @Column(name = "sub_screen", length = 50, columnDefinition = "varchar(50) COMMENT '서브 스크린'")
  private String subScreen;

  @Column(name = "is_valid", nullable = false, columnDefinition = "tinyint(1) DEFAULT 1 COMMENT '활성화 여부'")
  private boolean isValid = true;

  @Column(name = "display_start_date", nullable = false, columnDefinition = "date COMMENT '노출 시작날짜'")
  private LocalDate displayStartDate;

  @Column(name = "display_end_date", nullable = false, columnDefinition = "date COMMENT '노출 종료날짜'")
  private LocalDate displayEndDate;

  @Column(name = "words", nullable = false, length = 2048, columnDefinition = "varchar(2048) COMMENT '말씀'")
  private String words;

  @Column(name = "book", nullable = false, length = 10, columnDefinition = "varchar(10) COMMENT '서'")
  private String book;

  @Column(name = "chapter", nullable = false, columnDefinition = "int COMMENT '장'")
  private int chapter;

  @Column(name = "verse", nullable = false, columnDefinition = "int COMMENT '절'")
  private int verse;

  @Builder
  public Word(String mainScreen, String subScreen, LocalDate displayStartDate, LocalDate displayEndDate, String book, int chapter, int verse, String words) {
    this.mainScreen = mainScreen;
    this.subScreen = subScreen;
    this.displayStartDate = displayStartDate;
    this.displayEndDate = displayEndDate;
    this.book = book;
    this.chapter = chapter;
    this.verse = verse;
    this.words = words;
  }
}
