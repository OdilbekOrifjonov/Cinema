package uz.likwer.cinema.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uz.likwer.cinema.entity.Session;
import uz.likwer.cinema.entity.User;
import uz.likwer.cinema.repo.SeatRepo;
import uz.likwer.cinema.repo.SessionRepo;
import uz.likwer.cinema.utils.QRCodeGenerator;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "TicketServlet", urlPatterns = "/ticket")
public class TicketServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Object currentUserObj = session.getAttribute("currentUser");

        if (currentUserObj != null) {
            User user = (User) currentUserObj;

            Object seatIdObj = req.getParameter("seatId");

            if (seatIdObj != null) {
                UUID seatId = UUID.fromString(String.valueOf(seatIdObj));

                Object sessionIdObj = req.getParameter("sessionId");

                if (sessionIdObj != null) {
                    Session sessionMy = SessionRepo.findById(UUID.fromString(String.valueOf(sessionIdObj)));

                    SeatRepo.bookSeat(seatId, user);

//                    resp.sendRedirect("/seats.jsp?sessionId="+sessionMy.getId());


                    resp.setContentType("text/html;charset=UTF-8");
                    String qrPath = getServletContext().getRealPath("/tempFiles/qrcode.png");

                    // Generate QR code
                    QRCodeGenerator.generateQRCode("asd");

                    // Output the HTML response
                    resp.getWriter().println("<html>");
                    resp.getWriter().println("<head>");
                    resp.getWriter().println("<title>iTicket</title>");
                    resp.getWriter().println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css\" rel=\"stylesheet\">");
                    resp.getWriter().println("</head>");
                    resp.getWriter().println("<body>");
                    resp.getWriter().println("<div style=\"justify-content: center; text-align: center;\" class=\"row\">");
                    resp.getWriter().println("<h1>Hall Name:"+1+"</h1>");
                    resp.getWriter().println("<h1>Start:"+1+"</h1>");
                    resp.getWriter().println("<h1>End:"+1+"</h1>");
                    resp.getWriter().println("<h1>Seat Number:"+1+"</h1>");

                    File file = new File(qrPath);
                    if (file.exists()) {
                        resp.getWriter().println("<img src=\"/getqrcode\" style=\"width: 300px;\" alt=\"QR Code\">");
                    }

                    resp.getWriter().println("</div>");
                    resp.getWriter().println("</body>");
                    resp.getWriter().println("</html>");

                    return;
                }
            }
        }
        resp.sendRedirect("/login");
    }
}
