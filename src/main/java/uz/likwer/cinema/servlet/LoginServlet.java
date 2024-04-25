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
import java.util.Random;

@WebServlet(name = "fromLoginToLoginJsp", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        Optional<User> userOpt = UserRepo.findByEmail(email);


        Integer code = new Random().nextInt(9000) + 1000;
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (user.getPassword().equals(password)) {
                Thread sendMailThread = new Thread(() -> UserRepo.sendMail(user.getEmail(), code));
                sendMailThread.start();
                
                session.setAttribute("code", code);
                session.setAttribute("userTemp", user);

                resp.sendRedirect("/checkmail.jsp");
            } else {
                session.setAttribute("error", "Invalid password");
                resp.sendRedirect("/login.jsp");
            }
        } else {
            User newUser = User.builder()
                    .email(email)
                    .password(password)
                    .build();

            Thread sendMailThread = new Thread(() -> UserRepo.sendMail(newUser.getEmail(), code));
            sendMailThread.start();

            session.setAttribute("code", code);
            session.setAttribute("userTemp", newUser);

            resp.sendRedirect("/checkmail.jsp");
        }
    }
}
