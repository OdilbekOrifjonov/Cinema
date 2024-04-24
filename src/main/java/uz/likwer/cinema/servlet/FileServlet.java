package uz.likwer.cinema.servlet;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import uz.likwer.cinema.entity.User;
import uz.likwer.cinema.repo.UserRepo;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

@WebServlet(name = "fromLoginToLoginJsp", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Optional<User> userOpt = UserRepo.findByUserName(username);


        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (user.getPassword().equals(password)) {
                resp.addCookie(UserRepo.getCookieWithUser(user));

                session.setAttribute("currentUser", user);
                resp.sendRedirect("/");
            } else {
                session.setAttribute("error", "Invalid password");
                resp.sendRedirect("/login.jsp");
            }
        } else {
            User newUser = User.builder()
                    .username(username)
                    .password(password)
                    .build();
            UserRepo.save(newUser);

            Cookie cookie = UserRepo.getCookieWithUser(newUser);

            resp.addCookie(cookie);

            session.setAttribute("success", "User created successfully");
            session.setAttribute("currentUser", newUser);
            resp.sendRedirect("/");
        }






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
