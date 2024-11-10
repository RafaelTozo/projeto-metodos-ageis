import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.Properties;

public class FuncoesMail {
    private String codigo;

    private int gerarCodigo() {
        SecureRandom random = new SecureRandom();
        return 100000 + random.nextInt(900000);
    }

    public String enviarEmail(String email, String assunto) {

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");


        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("automail.sender.passmanager@gmail.com", "uhxc xzrv deyg skjf");
            }
        });

        try {
            codigo =  Integer.toString(gerarCodigo()) ;
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("automail.sender.passmanager@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(assunto);
            message.setText(codigo);


            Transport.send(message);
            return codigo;

        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
