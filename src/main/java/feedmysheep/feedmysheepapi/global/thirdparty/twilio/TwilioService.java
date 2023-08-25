package feedmysheep.feedmysheepapi.global.thirdparty.twilio;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

  @Value("${twilio.fromPhoneNumber}")
  private String fromPhoneNumber;

  public void sendSMS(String toPhoneNumber, String messageBody) {
    Message.creator(
        new PhoneNumber(toPhoneNumber),
        new PhoneNumber(fromPhoneNumber),
        messageBody
    ).create();
  }
}
