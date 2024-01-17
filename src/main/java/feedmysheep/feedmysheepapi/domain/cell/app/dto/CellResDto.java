package feedmysheep.feedmysheepapi.domain.cell.app.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
public class CellResDto {

  @Getter
  @Setter
  @RequiredArgsConstructor
  public static class getCellMemberByCellId {

    private Long memberId;
    private boolean isLeader;
    private boolean isBirthdayThisMonth;
    private String birthday;
    private String profileImageUrl;
  }

  @Getter
  @Setter
  @RequiredArgsConstructor
  public static class getGatheringsAndPrayersCount {

    private int totalGatheringCount;
    private int totalPrayerRequestCount;
  }
}
