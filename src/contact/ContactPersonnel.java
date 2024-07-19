package contact;

import java.time.LocalDate;

public class ContactPersonnel extends Contact {
    private String adresse;

    public ContactPersonnel(String id, String nom, String numeroTelephone, String email, LocalDate dateAnniversaire, String adresse) {
        super(id, nom, numeroTelephone, email, dateAnniversaire);
        this.adresse = adresse;
    }

    public ContactPersonnel(String fileString) {
        super(fileString.split(";")[0] + ";" + fileString.split(";")[1] + ";" + fileString.split(";")[2] + ";" + fileString.split(";")[3] + ";" + fileString.split(";")[4]);
        this.adresse = fileString.split(";")[5];
    }

    @Override
    public String toFileString() {
        return super.toFileString() + ";" + adresse;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return super.toString() + ", Adresse: " + adresse;
    }
}