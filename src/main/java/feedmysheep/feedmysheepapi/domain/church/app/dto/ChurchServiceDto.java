package feedmysheep.feedmysheepapi.domain.church.app.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ChurchServiceDto {

  @AllArgsConstructor
  @Getter
  @Setter
  public static class memberEvent {

    private Long memberId;
    private String memberName;
    private String sex;
    private LocalDate birthday;
    private String dayOfWeek;
    private String phone;
    private String profileImageUrl;
    private String eventName;
  }
}
