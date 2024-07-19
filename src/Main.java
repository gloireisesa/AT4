import contact.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GestionContacts gestionnaire = new GestionContacts();
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            gestionnaire.chargerContacts("contacts.txt");
        } catch (IOException | ContactException e) {
            System.out.println("Erreur de chargement des contacts: " + e.getMessage());
        }

        while (true) {
            System.out.println("1. Ajouter Contact");
            System.out.println("2. Supprimer Contact");
            System.out.println("3. Modifier Contact");
            System.out.println("4. Rechercher Contact par Nom");
            System.out.println("5. Rechercher Contact par Téléphone");
            System.out.println("6. Lister Contacts par Initiale");
            System.out.println("7. Afficher Nombre Total de Contacts");
            System.out.println("8. Afficher Contacts par Type");
            System.out.println("9. Afficher Détails d'un Contact par ID");
            System.out.println("10. Afficher Contacts ayant un Anniversaire dans une Période Spécifique");
            System.out.println("11. Sauvegarder Contacts");
            System.out.println("12. Quitter");
            System.out.print("Choisissez une option: ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer le retour à la ligne

            switch (choix) {
                case 1:
                    System.out.print("ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Nom: ");
                    String nom = scanner.nextLine();
                    System.out.print("Numéro de téléphone: ");
                    String numero = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Date d'anniversaire (dd/MM/yyyy): ");
                    String dateAnnivStr = scanner.nextLine();
                    LocalDate dateAnniv = null;
                    if (!dateAnnivStr.isEmpty()) {
                        dateAnniv = LocalDate.parse(dateAnnivStr, formatter);
                    }
                    System.out.print("Contact Personnel (1) ou Professionnel (2)? ");
                    int typeContact = scanner.nextInt();
                    scanner.nextLine(); // Consommer le retour à la ligne

                    if (typeContact == 1) {
                        System.out.print("Adresse: ");
                        String adresse = scanner.nextLine();
                        ContactPersonnel contactPerso = new ContactPersonnel(id, nom, numero, email, dateAnniv, adresse);
                        gestionnaire.ajouterContact(contactPerso);
                    } else if (typeContact == 2) {
                        System.out.print("Nom de l'entreprise: ");
                        String entreprise = scanner.nextLine();
                        ContactProfessionnel contactPro = new ContactProfessionnel(id, nom, numero, email, dateAnniv, entreprise);
                        gestionnaire.ajouterContact(contactPro);
                    }
                    break;

                case 2:
                    System.out.print("ID du contact à supprimer: ");
                    String idSupp = scanner.nextLine();
                    gestionnaire.supprimerContact(idSupp);
                    break;

                case 3:
                    System.out.print("ID du contact à modifier: ");
                    String idMod = scanner.nextLine();
                    System.out.print("Nom: ");
                    String nomMod = scanner.nextLine();
                    System.out.print("Numéro de téléphone: ");
                    String numeroMod = scanner.nextLine();
                    System.out.print("Email: ");
                    String emailMod = scanner.nextLine();
                    System.out.print("Date d'anniversaire (dd/MM/yyyy): ");
                    String dateAnnivModStr = scanner.nextLine();
                    LocalDate dateAnnivMod = null;
                    if (!dateAnnivModStr.isEmpty()) {
                        dateAnnivMod = LocalDate.parse(dateAnnivModStr, formatter);
                    }
                    gestionnaire.modifierContact(idMod, nomMod, numeroMod, emailMod, dateAnnivMod);
                    break;

                case 4:
                    System.out.print("Nom du contact à rechercher: ");
                    String nomRech = scanner.nextLine();
                    Contact contactNom = gestionnaire.rechercherContactParNom(nomRech);
                    System.out.println(contactNom != null ? contactNom : "Contact non trouvé");
                    break;

                case 5:
                    System.out.print("Numéro de téléphone du contact à rechercher: ");
                    String numeroRech = scanner.nextLine();
                    Contact contactNumero = gestionnaire.rechercherContactParNumero(numeroRech);
                    System.out.println(contactNumero != null ? contactNumero : "Contact non trouvé");
                    break;

                case 6:
                    System.out.print("Initiale pour lister les contacts: ");
                    char initiale = scanner.nextLine().charAt(0);
                    List<Contact> contactsInitiale = gestionnaire.listerContactsParInitiale(initiale);
                    contactsInitiale.forEach(System.out::println);
                    break;

                case 7:
                    int totalContacts = gestionnaire.getNombreTotalContacts();
                    System.out.println("Nombre total de contacts: " + totalContacts);
                    break;

                case 8:
                    System.out.println("1. Liste des contacts personnels");
                    System.out.println("2. Liste des contacts professionnels");
                    int typeListe = scanner.nextInt();
                    List<Contact> contactsType = null;
                    if (typeListe == 1) {
                        contactsType = gestionnaire.listerContactsParType(ContactPersonnel.class);
                    } else if (typeListe == 2) {
                        contactsType = gestionnaire.listerContactsParType(ContactProfessionnel.class);
                    }
                    contactsType.forEach(System.out::println);
                    break;

                case 9:
                    System.out.print("ID du contact à afficher: ");
                    String idAff = scanner.nextLine();
                    Contact contactAff = gestionnaire.afficherDetailsContactParId(idAff);
                    System.out.println(contactAff != null ? contactAff : "Contact non trouvé");
                    break;

                case 10:
                    System.out.print("Date de début (dd/MM/yyyy): ");
                    LocalDate debut = LocalDate.parse(scanner.nextLine(), formatter);
                    System.out.print("Date de fin (dd/MM/yyyy): ");
                    LocalDate fin = LocalDate.parse(scanner.nextLine(), formatter);
                    List<Contact> contactsAnniv = gestionnaire.listerContactsAvecAnniversaireDansPeriode(debut, fin);
                    contactsAnniv.forEach(System.out::println);
                    break;

                case 11:
                    try {
                        gestionnaire.sauvegarderContacts("contacts.txt");
                        System.out.println("Contacts sauvegardés avec succès.");
                    } catch (IOException e) {
                        System.out.println("Erreur lors de la sauvegarde des contacts: " + e.getMessage());
                    }
                    break;

                case 12:
                    try {
                        gestionnaire.sauvegarderContacts("contacts.txt");
                    } catch (IOException e) {
                        System.out.println("Erreur lors de la sauvegarde des contacts: " + e.getMessage());
                    }
                    System.out.println("Quitter...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Option invalide, veuillez réessayer.");
            }
        }
    }
}