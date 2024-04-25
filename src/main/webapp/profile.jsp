<%@ page import="uz.likwer.cinema.entity.User" %>
<%@ page import="uz.likwer.cinema.repo.TicketRepo" %>
<%@ page import="java.util.List" %>
<%@ page import="uz.likwer.cinema.entity.Ticket" %>
<%@ page import="uz.likwer.cinema.repo.SeatRepo" %>
<%@ page import="uz.likwer.cinema.entity.Session" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="uz.likwer.cinema.entity.Seat" %>
<%@ page import="uz.likwer.cinema.utils.Utils" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>iTicket - Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
<%
    Object currentUserObj = session.getAttribute("currentUser");
    User currentUser = null;
    List<Ticket> tickets;
    if (currentUserObj == null) {
        response.sendRedirect("/");
        return;
    } else {
        currentUser = (User) currentUserObj;
        tickets = TicketRepo.findAllByUserId(currentUser.getId());
    }
%>

<header>
    <nav id="main-navbar" class="navbar navbar-expand-lg navbar-light bg-white fixed-top">
        <!-- Container wrapper -->
        <div class="container-fluid">
            <ul class="navbar-nav ms-auto d-flex flex-row">
                <li class="nav-item dropdown" style="margin-right: 5px">
                    <a class="btn btn-success" href="${pageContext.request.contextPath}/index.jsp">Home</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="btn btn-success" href="${pageContext.request.contextPath}/login/logout">Logout</a>
                </li>
            </ul>
        </div>
    </nav>
</header>

<main>
    <div style="height: 57px"></div>

    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title text-center">Profile</h3>
                    </div>
                    <div class="card-body">
                        <div class="text-center">

                            <table class="table">
                                <thead>
                                <tr>
                                    <th>Start</th>
                                    <th>End</th>
                                    <th>Hall Name</th>
                                    <th>Movie Title</th>
                                    <th>Seat Number</th>
                                    <th>QR</th>
                                </tr>
                                </thead>
                                <tbody>
                                <% for (Ticket ticket : tickets) { %>
                                <tr>
                                    <%
                                        Session sessionMy = SeatRepo.getSession(ticket.getSeat().getId());

                                        sessionMy.getSeats().sort(Comparator.comparing(Seat::getId));

                                        int index = 0;
                                        for (int i = 0; i < sessionMy.getSeats().size(); i++) {
                                            if (ticket.getSeat().getId().equals(sessionMy.getSeats().get(i).getId())) {
                                                index = i;
                                                break;
                                            }
                                        }

                                    %>
                                    <td><%=Utils.prettyDateTime(sessionMy.getStartTime())%></td>
                                    <td><%=Utils.prettyDateTime(sessionMy.getEndTime())%></td>
                                    <td><%=sessionMy.getHall().getName()%></td>
                                    <td><%=sessionMy.getMovie().getTitle()%></td>
                                    <td><%=index + 1%></td>
                                    <td><a class="btn btn-primary" href="http://localhost:8080/ticketqr.jsp?ticketId=<%=ticket.getId()%>&seatId=<%=ticket.getSeat().getId()%>&userId=<%=ticket.getUser().getId()%>">QR</a></td>
                                </tr>
                                <%}%>
                                </tbody>
                            </table>


                            <a href="${pageContext.request.contextPath}/" class="btn btn-primary">test</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
