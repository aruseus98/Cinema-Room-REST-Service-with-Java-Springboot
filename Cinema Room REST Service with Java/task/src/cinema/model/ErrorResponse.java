package cinema.model;
// Class gestion erreur
public class ErrorResponse {
    private String error;

    public ErrorResponse(String error){
        this.error = error;
    }
    public String getError() {
        return error;
    }
}
