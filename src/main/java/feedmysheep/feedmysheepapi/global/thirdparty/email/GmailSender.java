package feedmysheep.feedmysheepapi.global.thirdparty.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GmailSender implements EmailSender {

  // FIXME 일단 무시
  private final MailSender mailSender;

  @Override
  public void send(EmailDto emailDto) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(emailDto.getTo());
    message.setSubject(emailDto.getSubject());
    message.setText(emailDto.getBody());
    mailSender.send(message);
  }
}