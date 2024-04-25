package uz.likwer.cinema.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet(name = "getQRCode",value = "/getqrcode")
public class GetQRCodeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream outputStream = resp.getOutputStream();
        outputStream.write(Files.readAllBytes(Path.of("C:\\Users\\User\\IdeaProjects\\Cinema\\src\\main\\webapp\\tempFiles\\qrcode.png")));
    }
}
