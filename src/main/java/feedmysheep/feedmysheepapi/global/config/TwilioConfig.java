package feedmysheep.feedmysheepapi.global.config;

import feedmysheep.feedmysheepapi.global.policy.CONSTANT.TWILIO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.twilio.Twilio;

@Configuration
public class TwilioConfig {
  @Bean
  public void twilioInit() {
    Twilio.init(TWILIO.ACCOUNT_SID, TWILIO.AUTH_TOKEN);
  }
}
