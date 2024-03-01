package cinema.controller;

import cinema.model.CinemaRoom;
import cinema.model.ErrorResponse;
import cinema.model.PurchaseRequest;
import cinema.model.PurchaseResponse;
import cinema.model.Seat;
import cinema.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController // Gestion achat d'un siège
public class PurchaseController {
    private final CinemaRoom cinemaRoom;

    @Autowired // Injection automatique de dépendance
    public PurchaseController(CinemaRoom cinemaRoom) {
        this.cinemaRoom = cinemaRoom;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseSeats(@RequestBody PurchaseRequest purchaseRequest){
        // Vérifie si la rangée ou la colonne spécifiée dépasse les dimensions de la salle de cinéma
        if (purchaseRequest.getRow() > cinemaRoom.getRows() || purchaseRequest.getColumn() > cinemaRoom.getColumns() || purchaseRequest.getRow() < 1 || purchaseRequest.getColumn() < 1) {
            ErrorResponse errorResponse = new ErrorResponse("The number of a row or a column is out of bounds!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        Seat seatToPurchase = cinemaRoom.getSeats().stream()
                .filter(seat -> seat.getRow() == purchaseRequest.getRow() && seat.getColumn() == purchaseRequest.getColumn())
                .findFirst()
                .orElse(null);

        if (seatToPurchase != null && "not purchased".equals(seatToPurchase.getStatus())) {
            seatToPurchase.setStatus("purchased");
            UUID token = UUID.randomUUID(); // Génère un token unique
            seatToPurchase.setToken(token);

            Ticket ticket = new Ticket(seatToPurchase.getRow(), seatToPurchase.getColumn(), seatToPurchase.getPrice()); // Construction d'un objet nommé Ticket
            PurchaseResponse response = new PurchaseResponse(token, ticket); // On passe token et ticket à PurchaseResponse

            return ResponseEntity.ok(response);
        } else {
            // Gérer le cas où le siège est déjà acheté ou n'existe pas
            ErrorResponse errorResponse = new ErrorResponse("The ticket has been already purchased!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

    }
}