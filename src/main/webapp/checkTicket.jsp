<%@ page import="java.util.UUID" %>
<%@ page import="uz.likwer.cinema.repo.TicketRepo" %>
<%@ page import="uz.likwer.cinema.entity.Ticket" %>
<%@ page import="uz.likwer.cinema.utils.Utils" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>iTicket</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
<%
    String ticketIdStr = request.getParameter("ticketId");
    String seatIdStr = request.getParameter("seatId");
    String userIdStr = request.getParameter("userId");

    boolean checkTicket = false;
    if (ticketIdStr != null) {
        if (seatIdStr != null) {
            if (userIdStr != null) {
                if (Utils.isUUIDv4(ticketIdStr)) {
                    if (Utils.isUUIDv4(seatIdStr)) {
                        if (Utils.isUUIDv4(userIdStr)) {
                            UUID ticketId = UUID.fromString(ticketIdStr);
                            UUID seatId = UUID.fromString(seatIdStr);
                            UUID userId = UUID.fromString(userIdStr);

                            Ticket ticket = TicketRepo.findById(ticketId);

                            if (ticket.getSeat().getId().equals(seatId)) {
                                if (ticket.getUser().getId().equals(userId)) {
                                    checkTicket = true;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
%>

<div style="display: flex; justify-content: center;">
    <% if (checkTicket) { %>
    <img src="static/success.png" style="height: 35%; width: 35%" alt="SUCCESS ✅">
    <% } else { %>
    <img src="static/error.png" style="height: 35%; width: 35%" alt="ERROR ❌">
    <% } %>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
