package cinema.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CinemaRoom {
    private int rows;
    private int columns;
    private List<Seat> seats;

    // Constructor, getters et setters
    public CinemaRoom(int rows, int column) {
        this.rows = rows;
        this.columns = column;
        this.seats = new ArrayList<>();
        if (columns > 0){
            for (int i = 1; i <= rows; i++) {
                for (int j = 1; j <= columns; j++) {
                    seats.add(new Seat(i, j));
                }
            }
        }
    }

    public Seat findSeatByToken(UUID token) {
        return seats.stream()
                .filter(seat -> token.equals(seat.getToken()) && seat.getToken() != null)
                .findFirst()
                .orElse(null);
    }
    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
