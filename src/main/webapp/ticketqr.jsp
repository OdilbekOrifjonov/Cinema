<%@ page import="uz.likwer.cinema.utils.QRCodeGenerator" %>
<%@ page import="java.util.UUID" %>
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
    if (ticketIdStr != null) {
        if (seatIdStr != null) {
            if (userIdStr != null) {
                if (Utils.isUUIDv4(ticketIdStr)){
                    if (Utils.isUUIDv4(seatIdStr)){
                        if (Utils.isUUIDv4(userIdStr)){
                            UUID ticketId = UUID.fromString(ticketIdStr);
                            UUID seatId = UUID.fromString(seatIdStr);
                            UUID userId = UUID.fromString(userIdStr);

                            QRCodeGenerator.generateQRCode("http://10.10.1.117:8080/checkTicket.jsp?ticketId="+ticketId+"&seatId="+seatId+"&userId="+userId);
                        }
                    }
                }
            }
        }
    }
%>

<div style="display: flex; justify-content: center; ">
    <img src="${pageContext.request.contextPath}/getqrcode" style="height: 35%; width: 35%;" alt="QR Code">
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
