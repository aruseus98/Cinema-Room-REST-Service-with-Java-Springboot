package cinema.controller;

import cinema.model.CinemaRoom;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Gestion affichage des sièges dans le cinéma
public class SeatsController {
    private final CinemaRoom cinemaRoom;
    public SeatsController(CinemaRoom cinemaRoom) {
        this.cinemaRoom = cinemaRoom;
    }

    @GetMapping("/seats")
    public CinemaRoom getCinemaRoom() {
        return cinemaRoom;
    }
}
