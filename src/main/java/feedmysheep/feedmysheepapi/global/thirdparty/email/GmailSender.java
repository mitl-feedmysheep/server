package feedmysheep.feedmysheepapi.global.thirdparty.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class GmailSender implements EmailSender {

  private final MailSender mailSender;

  @Autowired
  // FIXME 일단 무시
  public GmailSender(MailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Override
  public void send(EmailDto emailDto) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(emailDto.getTo());
    message.setSubject(emailDto.getSubject());
    message.setText(emailDto.getBody());
    mailSender.send(message);
  }
}