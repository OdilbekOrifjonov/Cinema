package uz.likwer.cinema.repo;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.Cookie;
import uz.likwer.cinema.entity.Movie;
import uz.likwer.cinema.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static uz.likwer.cinema.config.DataLoader.emf;

public class MovieRepo {

    public static List<Movie> findAll() {
        return emf.createEntityManager().createQuery("from Movie", Movie.class).getResultList();
    }
    public static Movie findById(UUID uuid) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.find(Movie.class, uuid);
    }

    public static void save(Movie movie) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(movie);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
