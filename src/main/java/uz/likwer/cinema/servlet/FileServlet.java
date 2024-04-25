package uz.likwer.cinema.servlet;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.http.Part;
import uz.likwer.cinema.entity.Attachment;
import uz.likwer.cinema.entity.AttachmentContent;
import uz.likwer.cinema.entity.User;
import uz.likwer.cinema.repo.UserRepo;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

import static uz.likwer.cinema.config.DataLoader.emf;

@WebServlet(name = "file", value = "/file")
@MultipartConfig
public class FileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Part filePart = req.getPart("file");
        String fileName = filePart.getSubmittedFileName();
        String contentType = fileName.substring(fileName.lastIndexOf(".") + 1);

        AttachmentContent attachmentContent = AttachmentContent.builder()
                .attachment(Attachment.builder()
                        .contentType(contentType)
                        .build())
                .build();

        em.persist(attachmentContent);

        req.getSession().setAttribute("attachmentId", attachmentContent.getAttachment().getId());

        em.getTransaction().commit();
        em.close();


    }
}
