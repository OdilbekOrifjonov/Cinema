package com.example.chat.servlet;

import com.example.chat.entity.User;
import com.example.chat.repo.UserRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Optional;

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
}
