package uz.likwer.cinema.repo;

import jakarta.persistence.EntityManager;
import uz.likwer.cinema.entity.Movie;
import uz.likwer.cinema.entity.Ticket;

import java.util.List;
import java.util.UUID;

import static uz.likwer.cinema.config.DataLoader.emf;

public class TicketRepo {

    public static List<Ticket> findAll() {
        return emf.createEntityManager().createQuery("from Ticket", Ticket.class).getResultList();
    }
    public static List<Ticket> findAllByUserId(UUID userId) {
        return emf.createEntityManager().createQuery("from Ticket t where t.user.id=:userId", Ticket.class).setParameter("userId",userId).getResultList();
    }
    public static Ticket findById(UUID uuid) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.find(Ticket.class, uuid);
    }

    public static void save(Ticket ticket) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(ticket);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
