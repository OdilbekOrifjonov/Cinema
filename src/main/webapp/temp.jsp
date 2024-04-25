<%@ page import="uz.likwer.cinema.utils.QRCodeGenerator" %>
<%@ page import="java.io.File" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>iTicket</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
<% QRCodeGenerator.generateQRCode("asd"); %>

<div style="justify-content: center; text-align: center;" class="row">
    <h1>Hall Name:</h1>
    <h1>Start:</h1>
    <h1>End:</h1>
    <h1>Seat Number:</h1>

    <%
        File file = new File(QRCodeGenerator.QR_PATH);

        if (file.exists()) { %>
            <img src="/getqrcode" style="width: 300px;" alt="QR Code">
    <%  } %>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
