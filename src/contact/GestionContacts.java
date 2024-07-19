package contact;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class GestionContacts {
    private HashMap<String, Contact> contacts;

    public GestionContacts() {
        contacts = new HashMap<>();
    }

    public void ajouterContact(Contact contact) {
        contacts.put(contact.getId(), contact);
    }

    public void supprimerContact(String id) {
        contacts.remove(id);
    }

    public void modifierContact(String id, String nom, String numeroTelephone, String email, LocalDate dateAnniversaire) {
        Contact contact = contacts.get(id);
        if (contact != null) {
            contact.setNom(nom);
            contact.setNumeroTelephone(numeroTelephone);
            contact.setEmail(email);
            contact.setDateAnniversaire(dateAnniversaire);
        }
    }

    public Contact rechercherContactParNom(String nom) {
        for (Contact contact : contacts.values()) {
            if (contact.getNom().equalsIgnoreCase(nom)) {
                return contact;
            }
        }
        return null;
    }

    public Contact rechercherContactParNumero(String numero) {
        for (Contact contact : contacts.values()) {
            if (contact.getNumeroTelephone().equals(numero)) {
                return contact;
            }
        }
        return null;
    }

    public List<Contact> listerContactsParInitiale(char initiale) {
        return contacts.values().stream()
                .filter(contact -> contact.getNom().toUpperCase().charAt(0) == Character.toUpperCase(initiale))
                .collect(Collectors.toList());
    }

    public int getNombreTotalContacts() {
        return contacts.size();
    }

    public List<Contact> listerContactsParType(Class<?> type) {
        return contacts.values().stream()
                .filter(contact -> type.isInstance(contact))
                .collect(Collectors.toList());
    }

    public Contact afficherDetailsContactParId(String id) {
        return contacts.get(id);
    }

    public List<Contact> listerContactsAvecAnniversaireDansPeriode(LocalDate debut, LocalDate fin) {
        return contacts.values().stream()
                .filter(contact -> contact.getDateAnniversaire() != null &&
                        !contact.getDateAnniversaire().isBefore(debut) &&
                        !contact.getDateAnniversaire().isAfter(fin))
                .collect(Collectors.toList());
    }

    public void sauvegarderContacts(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Contact contact : contacts.values()) {
                writer.write(contact.toFileString());
                writer.newLine();
            }
        }
    }

    public void chargerContacts(String filename) throws IOException, ContactException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 6 && parts[5].length() > 0) {
                    ajouterContact(new ContactPersonnel(line));
                } else if (parts.length == 6 && parts[5].length() > 0) {
                    ajouterContact(new ContactProfessionnel(line));
                } else {
                    throw new ContactException("Format de contact invalide dans le fichier");
                }
            }
        }
    }
}
