package feedmysheep.feedmysheepapi.global.thirdparty.twilio;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import feedmysheep.feedmysheepapi.global.policy.CONSTANT.TWILIO;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

  public void sendSMS(String toPhoneNumber, String messageBody) {
    Message.creator(new PhoneNumber(toPhoneNumber), new PhoneNumber(TWILIO.FROM_PHONE_NUMBER),
        messageBody).create();
  }
}