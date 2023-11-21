package feedmysheep.feedmysheepapi.domain.church.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import feedmysheep.feedmysheepapi.models.ChurchEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ChurchRepositoryTest {

  @Autowired
  private ChurchRepository churchRepository;

  @BeforeEach
  public void setup() {
    ChurchEntity church1 = ChurchEntity.builder()
        .churchName("양곡교회")
        .churchLocation("창원시 신촌동")
        .churchNumber("055-111-1111")
        .churchDescription("고향 교회")
        .build();
    ChurchEntity church2 = ChurchEntity.builder()
        .churchName("번동제일교회")
        .churchLocation("서울 수유")
        .churchNumber("02-111-1111")
        .churchDescription("서울 교회")
        .build();
    church2.setValid(true);
    this.churchRepository.saveAll(List.of(church1, church2));
  }

  @Test
  @DisplayName("유효한 교회만 조회")
  public void 유효한교회만조회() {
    List<ChurchEntity> validChurchList = this.churchRepository.getChurchList();

    assertThat(validChurchList.size()).isEqualTo(1);
    assertThat(validChurchList.get(0).getChurchName()).isEqualTo("번동제일교회");
  }
}