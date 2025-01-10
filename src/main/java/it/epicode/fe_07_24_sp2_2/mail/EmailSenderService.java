package it.epicode.fe_07_24_sp2_2.mail;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("appbellissimo");

        mailSender.send(message);
        System.out.println("Email inviata con successo a " + to);
    }

     public void sendEmailHtml(String to, String subject, String body)  {

         try {
             MimeMessage message = mailSender.createMimeMessage();
             MimeMessageHelper helper = new MimeMessageHelper(message);

             //SimpleMailMessage message = new SimpleMailMessage();

             helper.setTo(to);
             helper.setSubject(subject);
             helper.setText(body, true);
             helper.setFrom("appbellissimo");

             mailSender.send(message);
         } catch (MessagingException e) {
             throw new RuntimeException(e);
         }
         System.out.println("Email inviata con successo a " + to);
    }

}
