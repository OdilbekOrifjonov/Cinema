package uz.likwer.cinema.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.likwer.cinema.repo.SessionRepo;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Seat seat;
}