package feedmysheep.feedmysheepapi.global.thirdparty.sms;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import feedmysheep.feedmysheepapi.global.policy.CONSTANT.TWILIO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TwilioSender implements SmsSender {

  public void send(SmsDto smsDto) {
    Message.creator(new PhoneNumber(smsDto.getTo()), new PhoneNumber(TWILIO.FROM_PHONE_NUMBER),
        smsDto.getBody()).create();
  }
}