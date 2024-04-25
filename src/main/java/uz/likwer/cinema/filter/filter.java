package uz.likwer.cinema.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uz.likwer.cinema.entity.User;
import uz.likwer.cinema.repo.UserRepo;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebFilter(filterName = "CookieUserToSession",urlPatterns = {"/*"})
public class filter implements Filter {
    private static final List<String> openUrls = List.of(
            "login.jsp",
            "login",
            "checkmail.jsp",
            "login/check-mail",
            "checkTicket.jsp",
            "ticketqr.jsp"
    );
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        HttpSession session = req.getSession();

        for (String openUrl : openUrls) {
            if (req.getRequestURI().endsWith(openUrl)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }


        Object currentUserObj = session.getAttribute("currentUser");

        if (currentUserObj != null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        } else {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("currentUser")) {
                        User currentUser = UserRepo.findById(UUID.fromString(cookie.getValue()));

                        session.setAttribute("currentUser", currentUser);
                        filterChain.doFilter(servletRequest, servletResponse);
                        return;
                    }
                }
            }
        }
        resp.sendRedirect("/login.jsp");
    }
}
