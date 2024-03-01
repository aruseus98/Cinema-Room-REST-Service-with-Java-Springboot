package cinema.controller;

import cinema.model.CinemaRoom;
import cinema.model.ErrorResponse;
import cinema.model.Seat;
import cinema.model.Ticket;
import cinema.model.TokenRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
@RestController // Gestion du refund du ticket via un token
public class TicketController {
    private final CinemaRoom cinemaRoom;

    public TicketController(CinemaRoom cinemaRoom) {
        this.cinemaRoom = cinemaRoom;
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnTicket(@RequestBody TokenRequest tokenRequest) {
        UUID token = tokenRequest.getToken();
        Seat seat = cinemaRoom.findSeatByToken(token);

        if (seat == null) {
            ErrorResponse errorResponse = new ErrorResponse("Wrong token!");
            // ResponseEntity prend en charge la conversion en JSON
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        seat.setStatus("not purchased");
        seat.clearToken();
        // Construction d'une réponse anonyme avec uniquement le ticket
        return ResponseEntity.ok(new Object() {
            public final Ticket ticket = new Ticket(seat.getRow(), seat.getColumn(), seat.getPrice());
        });

    }
}