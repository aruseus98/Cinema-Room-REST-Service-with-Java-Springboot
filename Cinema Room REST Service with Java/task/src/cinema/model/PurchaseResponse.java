package cinema.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.UUID;
@JsonPropertyOrder({"token", "ticket"}) //Permet de spécifier un tableau de noms de propriété dans l'ordre souhaité
public class PurchaseResponse {
    private Ticket ticket;
    private UUID token; // Ajout du champ token

    public PurchaseResponse(UUID token, Ticket ticket) {
        this.token = token;
        this.ticket = ticket;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public UUID getToken() {
        return token;
    }
}
