package contact;

import java.time.LocalDate;

public class ContactProfessionnel extends Contact {
    private String nomEntreprise;

    public ContactProfessionnel(String id, String nom, String numeroTelephone, String email, LocalDate dateAnniversaire, String nomEntreprise) {
        super(id, nom, numeroTelephone, email, dateAnniversaire);
        this.nomEntreprise = nomEntreprise;
    }

    public ContactProfessionnel(String fileString) {
        super(fileString.split(";")[0] + ";" + fileString.split(";")[1] + ";" + fileString.split(";")[2] + ";" + fileString.split(";")[3] + ";" + fileString.split(";")[4]);
        this.nomEntreprise = fileString.split(";")[5];
    }

    @Override
    public String toFileString() {
        return super.toFileString() + ";" + nomEntreprise;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    @Override
    public String toString() {
        return super.toString() + ", Entreprise: " + nomEntreprise;
    }
}
