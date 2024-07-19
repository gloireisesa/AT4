package contact;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class Contact {
    private String id;
    private String nom;
    private String numeroTelephone;
    private String email;
    private LocalDate dateAnniversaire;
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Contact(String id, String nom, String numeroTelephone, String email, LocalDate dateAnniversaire) {
        this.id = id;
        this.nom = nom;
        this.numeroTelephone = numeroTelephone;
        this.email = email;
        this.dateAnniversaire = dateAnniversaire;
    }

    public Contact(String fileString) {
        String[] parts = fileString.split(";");
        this.id = parts[0];
        this.nom = parts[1];
        this.numeroTelephone = parts[2];
        this.email = parts[3];
        try {
            this.dateAnniversaire = LocalDate.parse(parts[4], FORMATTER);
        } catch (DateTimeParseException e) {
            this.dateAnniversaire = null;
        }
    }

    public String toFileString() {
        return id + ";" + nom + ";" + numeroTelephone + ";" + email + ";" + (dateAnniversaire != null ? dateAnniversaire.format(FORMATTER) : "");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateAnniversaire() {
        return dateAnniversaire;
    }

    public void setDateAnniversaire(LocalDate dateAnniversaire) {
        this.dateAnniversaire = dateAnniversaire;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nom: " + nom + ", Téléphone: " + numeroTelephone + ", Email: " + email + ", Anniversaire: " + (dateAnniversaire != null ? dateAnniversaire : "N/A");
    }
}