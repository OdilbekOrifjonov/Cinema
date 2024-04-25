package uz.likwer.cinema.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import uz.likwer.cinema.entity.User;
import uz.likwer.cinema.repo.UserRepo;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;

@WebServlet(name = "checkEmail", value = "/login/check-mail")
public class CheckEmailServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String code = req.getParameter("code");

        Object userTempObj = session.getAttribute("userTemp");
        if (userTempObj != null) {
            Object codeObj = session.getAttribute("code");
            if (codeObj != null) {
                String sessionCode = String.valueOf(codeObj);
                if (sessionCode.equals(code)) {
                    User user = (User) userTempObj;

                    if (user.getId()==null){
                        UserRepo.save(user);
                    }

                    resp.addCookie(UserRepo.getCookieWithUser(user));
                    session.setAttribute("currentUser", user);
                    resp.sendRedirect("/");
                    session.removeAttribute("userTemp");
                    session.removeAttribute("code");
                    return;
                }
            }
        }
        resp.sendRedirect("/checkmail.jsp");
    }
}
