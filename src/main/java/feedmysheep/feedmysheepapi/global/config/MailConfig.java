//package feedmysheep.feedmysheepapi.global.config;
//
//import feedmysheep.feedmysheepapi.global.policy.CONSTANT.GMAIL;
//import java.util.Properties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//@Configuration
//public class MailConfig {
//
//  @Bean
//  public JavaMailSender javaMailSender() {
//    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//    mailSender.setHost(GMAIL.HOST);
//    mailSender.setPort(GMAIL.PORT);
//    mailSender.setUsername(GMAIL.USERNAME);
//    mailSender.setPassword(GMAIL.PASSWORD);
//
//    Properties props = mailSender.getJavaMailProperties();
//    props.put("mail.transport.protocol", "smtp");
//    props.put("mail.smtp.auth", "true");
//    props.put("mail.smtp.starttls.enable", "true");
//    props.put("mail.debug", "true");
//
//    return mailSender;
//  }
//}
