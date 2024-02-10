package feedmysheep.feedmysheepapi.global.thirdparty.sms;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SmsDto {
  private String to;
  private String body;
}
