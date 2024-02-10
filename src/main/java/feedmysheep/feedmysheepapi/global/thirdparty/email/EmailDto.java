package feedmysheep.feedmysheepapi.global.thirdparty.email;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailDto {
  private String to;
  private String subject;
  private String body;
}
