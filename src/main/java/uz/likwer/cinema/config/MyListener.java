package uz.likwer.cinema.config;

import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import uz.likwer.cinema.entity.*;
import uz.likwer.cinema.repo.SeatRepo;

import java.time.LocalDateTime;
import java.util.Comparator;

import static uz.likwer.cinema.config.DataLoader.em;
import static uz.likwer.cinema.config.DataLoader.emf;

@WebListener
public class MyListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        emf = Persistence.createEntityManagerFactory("default");
        em = emf.createEntityManager();
//        initData();
        ServletContextListener.super.contextInitialized(sce);
    }

    private void initData() {
        Movie movie1 = Movie.builder().title("Venom 1").build();
        Movie movie2 = Movie.builder().title("Venom 2").build();
        Movie movie3 = Movie.builder().title("Avatar 1").build();
        Movie movie4 = Movie.builder().title("Avatar 2").build();
        Movie movie5 = Movie.builder().title("Tanos").build();
        Movie movie6 = Movie.builder().title("Duno 2").build();
        Movie movie7 = Movie.builder().title("The Stranger Things").build();
        Movie movie8 = Movie.builder().title("Avengers").build();
        Movie movie9 = Movie.builder().title("Taxi 5").build();

        LocalDateTime startTime = LocalDateTime.of(2024, 4, 24, 0, 0);

        Hall hall1 = Hall.builder().seats(SeatRepo.generateSeats(30)).name("Big").build();
        Hall hall2 = Hall.builder().seats(SeatRepo.generateSeats(30)).name("Large").build();
        Hall hall3 = Hall.builder().seats(SeatRepo.generateSeats(30)).name("Small").build();

        em.getTransaction().begin();
        em.persist(hall1);
        em.persist(hall2);
        em.persist(hall3);
        em.getTransaction().commit();

        Session session1 = Session.builder().startTime(startTime.plusHours(17)).endTime(startTime.plusHours(19)).movie(movie1).hall(hall1).seats(SeatRepo.copyHallSeats(hall1)).build();
        Session session2 = Session.builder().startTime(startTime.plusHours(13)).endTime(startTime.plusHours(15)).movie(movie2).hall(hall2).seats(SeatRepo.copyHallSeats(hall2)).build();
        Session session3 = Session.builder().startTime(startTime.plusHours(12)).endTime(startTime.plusHours(14)).movie(movie5).hall(hall3).seats(SeatRepo.copyHallSeats(hall3)).build();
        Session session4 = Session.builder().startTime(startTime.plusHours(9)).endTime(startTime.plusHours(11)).movie(movie6).hall(hall1).seats(SeatRepo.copyHallSeats(hall1)).build();
        Session session5 = Session.builder().startTime(startTime.plusHours(20)).endTime(startTime.plusHours(22)).movie(movie2).hall(hall2).seats(SeatRepo.copyHallSeats(hall2)).build();
        Session session6 = Session.builder().startTime(startTime.plusHours(19)).endTime(startTime.plusHours(21)).movie(movie4).hall(hall3).seats(SeatRepo.copyHallSeats(hall3)).build();
        Session session7 = Session.builder().startTime(startTime.plusHours(17)).endTime(startTime.plusHours(19)).movie(movie6).hall(hall1).seats(SeatRepo.copyHallSeats(hall1)).build();
        Session session8 = Session.builder().startTime(startTime.plusHours(19)).endTime(startTime.plusHours(21)).movie(movie3).hall(hall2).seats(SeatRepo.copyHallSeats(hall2)).build();
        Session session9 = Session.builder().startTime(startTime.plusHours(16)).endTime(startTime.plusHours(18)).movie(movie6).hall(hall3).seats(SeatRepo.copyHallSeats(hall3)).build();

        session1.getSeats().sort(Comparator.comparing(Seat::getId));
        session2.getSeats().sort(Comparator.comparing(Seat::getId));
        session3.getSeats().sort(Comparator.comparing(Seat::getId));
        session4.getSeats().sort(Comparator.comparing(Seat::getId));
        session5.getSeats().sort(Comparator.comparing(Seat::getId));
        session6.getSeats().sort(Comparator.comparing(Seat::getId));
        session7.getSeats().sort(Comparator.comparing(Seat::getId));
        session8.getSeats().sort(Comparator.comparing(Seat::getId));
        session9.getSeats().sort(Comparator.comparing(Seat::getId));

        em.getTransaction().begin();
        for (int i = 1; i < 101; i++) {
            User user = User.builder()
                    .email("qwerty"+i+"@gmail.com")
                    .password("qwerty"+i)
                    .build();

            em.persist(user);
        }
        em.persist(session1);
        em.persist(session2);
        em.persist(session3);
        em.persist(session4);
        em.persist(session5);
        em.persist(session6);
        em.persist(session7);
        em.persist(session8);
        em.persist(session9);

        em.persist(movie1);
        em.persist(movie2);
        em.persist(movie3);
        em.persist(movie4);
        em.persist(movie5);
        em.persist(movie6);
        em.persist(movie7);
        em.persist(movie8);
        em.persist(movie9);
        em.getTransaction().commit();
    }
}
