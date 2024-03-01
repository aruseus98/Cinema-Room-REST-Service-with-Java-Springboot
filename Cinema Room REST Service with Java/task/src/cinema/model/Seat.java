package cinema.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

// Class pour représenter un siège
public class Seat {
    private int row;
    private int column;
    private int price;
    private String status = "not purchased"; // Initialisé par défaut à "not purchased"
    private UUID token; // Ajout d'un champ token


    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.price = row <= 4 ? 10 : 8; // Calcul du prix basé sur la rangée
    }

    // Getters et setters pour row et column
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    // Le getter pour price ne doit pas avoir @JsonIgnore pour inclure price dans le JSON
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @JsonIgnore // Utilisez @JsonIgnore ici pour exclure status de la sérialisation JSON
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @JsonIgnore
    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public void clearToken() {
        this.token = null;
    }
}