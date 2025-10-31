package sunuguide.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;
    // Doit correspondre à l'email configuré dans application.properties
    private final String senderEmail = "votre.email@gmail.com";

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Envoie le code OTP à l'adresse spécifiée.
     * @param toEmail L'adresse e-mail du destinataire.
     * @param code Le code OTP généré.
     */
    public void sendVerificationCode(String toEmail, String code) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(senderEmail);
        message.setTo(toEmail);
        message.setSubject("Votre code de vérification SunuGuide");

        String emailContent = String.format(
                "Bonjour,\n\n" +
                        "Voici votre code à usage unique (OTP) pour vous connecter à SunuGuide : %s\n\n" +
                        "Ce code est valide pendant 5 minutes.\n\n" +
                        "L'équipe SunuGuide",
                code
        );
        message.setText(emailContent);

        try {
            mailSender.send(message);
        } catch (Exception e) {
            // IMPORTANT : Relancer une RuntimeException pour déclencher l'annulation (rollback) de la transaction
            throw new RuntimeException("Échec de l'envoi du code par e-mail. Veuillez vérifier la configuration SMTP.", e);
        }
    }
}