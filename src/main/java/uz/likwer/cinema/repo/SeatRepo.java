package uz.likwer.cinema.repo;

import jakarta.persistence.EntityManager;
import uz.likwer.cinema.entity.Hall;
import uz.likwer.cinema.entity.Seat;
import uz.likwer.cinema.entity.Session;
import uz.likwer.cinema.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static uz.likwer.cinema.config.DataLoader.emf;

public class SeatRepo {

    public static List<Seat> findAll() {
        return emf.createEntityManager().createQuery("from Seat", Seat.class).getResultList();
    }
    public static Seat findById(UUID uuid) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.find(Seat.class, uuid);
    }

    public static void save(Seat seat) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(seat);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public static List<Seat> generateSeats(int count) {
        List<Seat> seats = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            seats.add(Seat.builder().isBooked(false).build());
        }
        return seats;
    }

    public static List<Seat> findAllBySessionId(UUID sessionId) {
        Session session = SessionRepo.findById(sessionId);

        return session!=null ? session.getHall().getSeats() : new ArrayList<>();
    }

    public static void bookSeat(UUID seatId, User user) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        Seat seat = entityManager.find(Seat.class, seatId);
        seat.setIsBooked(true);
        seat.setUser(user);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public static Session getSession(UUID seatId){
        return SessionRepo.findBySeatId(seatId).orElse(null);
    }
    public static List<Seat> copyHallSeats(Hall hall1) {
        EntityManager em = emf.createEntityManager();

        List<Seat> seatsTemp = new ArrayList<>();
        List<Seat> seats = new ArrayList<>(hall1.getSeats());
        em.getTransaction().begin();
        for (Seat seat : seats) {
            Seat seatNew = Seat.builder()
                    .isBooked(seat.getIsBooked())
                    .build();
            seatsTemp.add(seatNew);
            em.persist(seatNew);
        }
        em.getTransaction().commit();
        return seatsTemp;
    }
}
