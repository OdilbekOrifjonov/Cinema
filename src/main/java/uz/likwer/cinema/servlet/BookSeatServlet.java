package uz.likwer.cinema.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import uz.likwer.cinema.entity.Seat;
import uz.likwer.cinema.entity.Ticket;
import uz.likwer.cinema.entity.User;
import uz.likwer.cinema.repo.SeatRepo;
import uz.likwer.cinema.repo.TicketRepo;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "BookSeat",value = "/seat/book")
public class BookSeatServlet extends HttpServlet {
    @Override
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
                    SeatRepo.bookSeat(seatId, user);

                    Seat seat = SeatRepo.findById(seatId);

                    Ticket ticket = Ticket.builder()
                            .seat(seat)
                            .user(user)
                            .build();

                    TicketRepo.save(ticket);
                    resp.sendRedirect("/seats.jsp?sessionId="+ UUID.fromString(String.valueOf(sessionIdObj)));
                    return;
                }
            }
        }
        resp.sendRedirect("/login");
    }

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        HttpSession session = req.getSession();
//
//        Object currentUserObj = session.getAttribute("currentUser");
//
//        if (currentUserObj != null) {
//            User user = (User) currentUserObj;
//
//            Object seatIdObj = req.getParameter("seatId");
//
//            if (seatIdObj != null) {
//                UUID seatId = UUID.fromString(String.valueOf(seatIdObj));
//
//                Object sessionIdObj = req.getParameter("sessionId");
//
//                if (sessionIdObj != null) {
//                    Session sessionMy = SessionRepo.findById(UUID.fromString(String.valueOf(sessionIdObj)));
//
//                    SeatRepo.bookSeat(seatId, user);
//
//
//                    resp.setContentType("text/html;charset=UTF-8");
//                    String qrPath = getServletContext().getRealPath("/tempFiles/qrcode.png");
//
//                    QRCodeGenerator.generateQRCode("asd");
//
//                    resp.getWriter().println("<html>");
//                    resp.getWriter().println("<head>");
//                    resp.getWriter().println("<title>iTicket</title>");
//                    resp.getWriter().println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css\" rel=\"stylesheet\">");
//                    resp.getWriter().println("</head>");
//                    resp.getWriter().println("<body>");
//                    resp.getWriter().println("<div style=\"justify-content: center; text-align: center;\" class=\"row\">");
//                    resp.getWriter().println("<h1>Hall Name:"+sessionMy.getHall().getName()+"</h1>");
//                    resp.getWriter().println("<h1>Start:"+ Utils.prettyDateTime(sessionMy.getStartTime()) +"</h1>");
//                    resp.getWriter().println("<h1>End:"+Utils.prettyDateTime(sessionMy.getEndTime())+"</h1>");
//
//                    sessionMy.getSeats().sort(Comparator.comparing(Seat::getId));
//
//
//
//                    int index = 0;
//                    for (int i = 0; i < sessionMy.getSeats().size(); i++) {
//                        if (seatId.equals(sessionMy.getSeats().get(i).getId())) {
//                            index = i;
//                            break;
//                        }
//                    }
//
//
//                    resp.getWriter().println("<h1>Seat Number:"+(index+1)+"</h1>");
//
//                    File file = new File(qrPath);
//                    if (file.exists()) {
//                        resp.getWriter().println("<img src=\"/getqrcode\" style=\"width: 300px;\" alt=\"QR Code\">");
//                    }
//
//                    resp.getWriter().println("</div>");
//                    resp.getWriter().println("</body>");
//                    resp.getWriter().println("</html>");
//
//                    return;
//                }
//            }
//        }
//        resp.sendRedirect("/login");
//    }
}
