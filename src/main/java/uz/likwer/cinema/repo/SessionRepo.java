package uz.likwer.cinema.repo;

import jakarta.persistence.EntityManager;
import uz.likwer.cinema.entity.Movie;
import uz.likwer.cinema.entity.Seat;
import uz.likwer.cinema.entity.Session;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static uz.likwer.cinema.config.DataLoader.emf;

public class SessionRepo {

    public static List<Session> findAll() {
        return emf.createEntityManager().createQuery("from Session", Session.class).getResultList();
    }
    public static Session findById(UUID uuid) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.find(Session.class, uuid);
    }

    public static void save(Session session) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(session);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public static List<Session> findAllByMovieId(UUID movieId) {
        return emf.createEntityManager().createQuery("select t from Session t where t.movie.id=:movieId", Session.class).setParameter("movieId",movieId).getResultList();
    }

    public static Optional<Session> findBySeatId(UUID seatId) {
        for (Session session : findAll())
            for (Seat seat : session.getSeats()) if (seat.getId().equals(seatId)) return Optional.of(session);
        return Optional.empty();
    }
}
