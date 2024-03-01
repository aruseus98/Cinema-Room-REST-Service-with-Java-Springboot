package cinema.controller;

import cinema.model.CinemaRoom;
import cinema.model.Seat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController // Statistiques du cinema
class StatisticsController {

    private static final String SECRET_PASSWORD = "super_secret"; // Déclaration du mot de passe
    private final CinemaRoom cinemaRoom;

    public StatisticsController(CinemaRoom cinemaRoom) {
        this.cinemaRoom = cinemaRoom;
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats(@RequestParam Optional<String> password) {
        if (password.isEmpty()) {
            // Retourner une réponse d'erreur au format JSON indiquant que le paramètre password est manquant
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "The password is wrong!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        if (!SECRET_PASSWORD.equals(password.get())) {
            // Retourner une réponse d'erreur au format JSON
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "The password is wrong!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        int income = calculateIncome();
        int available = calculateAvailableSeats();
        int purchased = calculatePurchasedTickets();

        Map<String, Integer> statistics = new HashMap<>();
        statistics.put("income", income);
        statistics.put("available", available);
        statistics.put("purchased", purchased);

        return ResponseEntity.ok(statistics);
    }

    private int calculateIncome() {
        int totalIncome = 0; // Initialisation de l'income à 0
        for (Seat seat : cinemaRoom.getSeats()) {
            if ("purchased".equals(seat.getStatus())) {
                totalIncome += seat.getPrice();
            }
        }
        return totalIncome;
    }

    private int calculateAvailableSeats() {
        int totalSeatsAvailable = 0; // Initialisation de l'income à 0
        for (Seat seat : cinemaRoom.getSeats()) {
            if (!"purchased".equals(seat.getStatus())) {
                totalSeatsAvailable++;
            }
        }
        return totalSeatsAvailable;
    }

    private int calculatePurchasedTickets() {
        return (int) cinemaRoom.getSeats().stream()
                .filter(seat -> "purchased".equals(seat.getStatus()))
                .count();
    }

}