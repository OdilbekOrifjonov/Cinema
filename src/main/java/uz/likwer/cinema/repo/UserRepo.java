package uz.likwer.cinema.repo;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.Cookie;
import uz.likwer.cinema.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

import static uz.likwer.cinema.config.DataLoader.emf;

public class UserRepo {

    public static List<User> findAll() {
        return emf.createEntityManager().createQuery("from User", User.class).getResultList();
    }
    public static List<User> findAllExceptCurrentUser(User user) {
        return emf.createEntityManager().createQuery("select u from User u where id!=:id", User.class).setParameter("id",user.getId()).getResultList();
    }

    public static User findById(UUID uuid) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.find(User.class, uuid);
    }

    public static Optional<User> findByEmail(String email) {
        return emf.createEntityManager().createQuery("select t from User t where email=:email", User.class).setParameter("email", email).getResultList().stream().findFirst();
    }

    public static void save(User user) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public static Cookie getCookieWithUser(User user) {
        Cookie cookie = new Cookie("currentUser", user.getId().toString());
        cookie.setMaxAge(60 * 60 * 24 * 30);
        cookie.setPath("/");
        cookie.setSecure(false);
        return cookie;
    }
    public static void sendMail(String toEmail, Integer code) {
        String companyEmail = "qwertyggg7878@gmail.com";
        String companyPassword = "albjiatyqspthkrz";

        try{
            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(companyEmail, companyPassword);
                }
            });
            Message message = new MimeMessage(session);
            message.setText(code+"");
            message.setFrom(new InternetAddress(companyEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            Transport.send(message);
        }catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
