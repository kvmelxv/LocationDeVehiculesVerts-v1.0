import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * Programme de gestion de location de véhicules verts (hybrides et électriques).
 * Ce système permet d'afficher l'inventaire des véhicules disponibles, de facturer
 * la location d'un véhicule, et d'afficher les statistiques des véhicules loués.
 * Il gère également les taxes, les assurances, et applique des rabais selon les conditions.
 *
 * @author Mustapha Kamel Cherif - CHEM14129808
 * @version v1.0 | 19 fevrier 2025
 */
public class FacturationRoulonsVehiculesVerts
{
    public static void main (String[] params) {
        /** Informations de la compagnie */
        String nomCompagnie = "Roulons les Véhicules Verts (RVV)";
        String adressCompagnie = "1500 rue Matata, Hakuna, Québec Y0Z 6Y7";
        String telCompagnie = "438 222-1111";
        /** Temps */
        LocalDateTime dateCourante = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dateFormatee = dateCourante.format(formatter);
        /** Choix du user */
        int choix;
        /** Type et Grandeur du vehicule */
        char typeVehi;
        char grandeurVehi;
        /** Diponibilite du vehicule */
        boolean voitureDisponible = false;
        /** Nombre de jour de location */
        int nbDeJourDeLocation;
        /** Informations du locataire */
        String prenomLocataire;
        String nomLocataire;
        String numTelLocataire;
        String numPermLocataire;
        /** Mode de Paiement et Assurance */
        char modePaiement;
        char typeDeCarteCredit;
        char assurance;
        String numDeCarteCredit = "";
        /** Voiture Hybride */
        //Quantite
        int nbVoitureHybPetit = 12;
        int nbVoitureHybInter = 10;
        int nbVoitureHybGrand = 3;
        //Prix voiture/jour
        final double PRIX_HYB_PETIT = 55.75;
        final double PRIX_HYB_INTER = 60.25;
        final double PRIX_HYB_GRAND = 65.50;
        //Prix assurance/jour
        final double PRIX_ASSUR_HYB_PETIT = 13.50;
        final double PRIX_ASSUR_HYB_INTER = 14.50;
        final double PRIX_ASSUR_HYB_GRAND = 15.50;
        /** Voiture Electrique */
        //Quantite
        int nbVoitureElecPetit = 11;
        int nbVoitureElecInter = 9;
        int nbVoitureElecGrand = 5;
        //Prix voiture/jour
        final double PRIX_ELEC_PETIT = 45.50;
        final double PRIX_ELEC_INTER = 50.50;
        final double PRIX_ELEC_GRAND = 55.25;
        //Prix assurance/jour
        final double PRIX_ASSUR_ELEC_PETIT = 12.50;
        final double PRIX_ASSUR_ELEC_INTER = 12.75;
        final double PRIX_ASSUR_ELEC_GRAND = 13.25;
        /** Taxes */ 
        // TPS (5%)
        final double TAXE_TPS = 0.05;
        // TVQ (9.975%%)
        final double TAXE_TVQ = 0.09975;
        /** Rabais */
        // 20%
        final double RABAIS_20 = 0.8;
        boolean rabaisAss;
        /** Facture */
        int compteurFacture = 0;
        double prixLocationParJour = 0;
        double prixAssuranceParJour = 0;
        double montantLocation;
        double montantAssurance;
        double sousTotal;
        double sousTotalTps;
        double sousTotalTvq;
        double totalFacture;
        /** Vehicule Louees */
        int vehElecPetitLouee = 0;
        int vehElecInterLouee = 0;
        int vehElecGrandLouee = 0;
        int vehHybPetitLouee = 0;
        int vehHybInterLouee = 0;
        int vehHybGrandLouee = 0;
        
        // Affichage du message d'accueil.
        System.out.println("************************************************************************************");
        System.out.println("*   Bienvenue dans le système de facturation de "+ nomCompagnie + "  *");
        System.out.println("************************************************************************************\n"); 
        do {
            // Affichage du menu.
            System.out.println("***  Bienvenue dans le menu ! Faites votre choix : ***\n"); 
            System.out.println("------------------------------------------------------");
            System.out.println("   1 - Afficher l’inventaire des véhicules");
            System.out.println("   2 - Facturer la location d’un véhicule");
            System.out.println("   3 - Afficher le nombre de véhicules hybrides et électriques loués");
            System.out.println("   4 - Quitter le programme \n");
            System.out.print("Entrez votre choix : ");
            choix = Clavier.lireInt();
            switch (choix) {
                case 1:
                    System.out.println();// Saut d'une ligne
                    System.out.println("--------------------------------------------------------");// Saut d'une ligne
                    System.out.println("  " + nomCompagnie);
                    System.out.println("  Adresse       " + adressCompagnie);
                    System.out.println("  Telephone     " + telCompagnie);
                    System.out.println("  Date          " + dateFormatee);
                    System.out.println("--------------------------------------------------------\n");
                    System.out.println("  Nombre de véhicules disponibles dans l'inventaire");
                    System.out.println("  *************************************************\n");
                    System.out.println("  Grandeur          Hybride      Électrique");
                    System.out.println("  ******************************************\n");
                    System.out.println("  Petit               " + nbVoitureHybPetit + "            " + nbVoitureElecPetit);
                    System.out.println("  Intermediaire       " + nbVoitureHybInter + "            " + nbVoitureElecInter);
                    System.out.println("  Grand               " + nbVoitureHybGrand + "             " + nbVoitureElecGrand + "\n");
                    System.out.println("--------------------------------------------------------\n");
                    System.out.println();// Saut d'une ligne
                    System.out.println("Appuyez sur <ENTER> pour revenir au menu.");
                    Clavier.lireFinLigne();
                    break;
                case 2:
                    System.out.println();// Saut d'une ligne
                    System.out.println("facturation de la location d’un véhicule");
                    System.out.println("****************************************\n");
                    // 1 Saisie du type de véhicule
                    do {
                        System.out.println("Entrez le type du véhicule (H/h pour Hybride, E/e pour Électrique) : ");
                        typeVehi = Clavier.lireChar();
                        Clavier.lireFinLigne();
                    
                        if (typeVehi != 'H' && typeVehi != 'h' && typeVehi != 'E' && typeVehi != 'e') {
                            System.out.println("Entrée invalide !\n");
                        }
                    } while (typeVehi != 'H' && typeVehi != 'h' && typeVehi != 'E' && typeVehi != 'e');
                    // 2 Saisie de la grandeur du véhicule
                    do {
                        System.out.println("Entrez la grandeur du véhicule (P/p pour Petit, I/i pour Intermédiaire, G/g pour Grand) : ");
                        grandeurVehi = Clavier.lireChar();
                        Clavier.lireFinLigne();
                        
                        if (grandeurVehi != 'P' && grandeurVehi != 'p' && grandeurVehi != 'I' && grandeurVehi != 'i' && grandeurVehi != 'G' && grandeurVehi != 'g') {
                            System.out.println("Entrée invalide !\n");
                        }
                    } while (grandeurVehi != 'P' && grandeurVehi != 'p' && grandeurVehi != 'I' && grandeurVehi != 'i' && grandeurVehi != 'G' && grandeurVehi != 'g');
                    // 3 Validation des disponibilites des vehicules
                    if ((typeVehi == 'h' || typeVehi == 'H') && (grandeurVehi == 'p' || grandeurVehi == 'P')) {
                        if (nbVoitureHybPetit > 0){
                            voitureDisponible = true;
                        }
                    } else if ((typeVehi == 'h' || typeVehi == 'H') && (grandeurVehi == 'i' || grandeurVehi == 'I')){
                        if (nbVoitureHybInter > 0){
                           voitureDisponible = true; 
                        }
                    } else if ((typeVehi == 'h' || typeVehi == 'H') && (grandeurVehi == 'g' || grandeurVehi == 'G')){
                        if (nbVoitureHybGrand > 0){
                           voitureDisponible = true; 
                           vehElecGrandLouee++;
                        }
                    } else if ((typeVehi == 'e' || typeVehi == 'E') && (grandeurVehi == 'p' || grandeurVehi == 'P')){
                        if (nbVoitureElecPetit > 0){
                           voitureDisponible = true; 
                        }
                    } else if ((typeVehi == 'e' || typeVehi == 'E') && (grandeurVehi == 'i' || grandeurVehi == 'I')){
                        if (nbVoitureElecInter > 0){
                           voitureDisponible = true; 
                        }
                    }else if ((typeVehi == 'e' || typeVehi == 'E') && (grandeurVehi == 'g' || grandeurVehi == 'G')){
                        if (nbVoitureElecGrand > 0){
                           voitureDisponible = true; 
                        }
                    }
                    // Vehicules Disponibles
                    if (voitureDisponible) {
                        System.out.println("");
                        System.out.println("Vehicule Disponible !\n");
                        // Saisie du nombre de jour de location et Validation
                        do {
                            System.out.println("Entrez le nombre de jour de location: ");
                            nbDeJourDeLocation = Clavier.lireInt();
                            if (nbDeJourDeLocation < 1 || nbDeJourDeLocation > 30) {
                                System.out.println("Entree invalide !\n");
                            }
                        } while (nbDeJourDeLocation < 1 || nbDeJourDeLocation > 30);
                        // Saisie des information du locataire.
                        // Saisie du prenom et validation.
                        do {
                            System.out.println("Entrez le prenom du locataire:");
                            prenomLocataire = Clavier.lireString();
                            if (prenomLocataire == "") {
                              System.out.println("Entrez Invalide !\n");  
                            }
                        } while (prenomLocataire == "");
                        // Saisie du nom et validation.
                        do {
                            System.out.println("Entrez le nom du locataire:");
                            nomLocataire = Clavier.lireString();
                            if (nomLocataire == "") {
                              System.out.println("Entrez Invalide !\n");  
                            }
                        } while (nomLocataire == "");
                        // Saisie du numero de telephone et validation.
                        do {
                            System.out.println("Entrez le numero de telephone du locataire:");
                            numTelLocataire = Clavier.lireString();
                            if (numTelLocataire == "") {
                              System.out.println("Entrez Invalide !\n");  
                            }
                        } while (numTelLocataire == "");
                        // Saisie du numero de permis et validation.
                        do {
                            System.out.println("Entrez le numero de permis du locataire:");
                            numPermLocataire = Clavier.lireString();
                            if (numPermLocataire == "") {
                              System.out.println("Entrez Invalide !\n");  
                            }
                        } while (numPermLocataire == "");
                        // Saisie du mode de paiement et Validation.
                        do {
                            System.out.println("Entrez le mode de paiement D/d pour Debit et C/c pour Credit:");
                            modePaiement = Clavier.lireChar();
                            Clavier.lireFinLigne();
                            if (modePaiement != 'D' && modePaiement != 'd' && modePaiement != 'C' && modePaiement != 'c'){
                                System.out.println("Entree Invalide !\n");
                            }
                        } while (modePaiement != 'D' && modePaiement != 'd' && modePaiement != 'C' && modePaiement != 'c');
                        // Mode de paiement est par Carte Credit.
                        if (modePaiement == 'C' || modePaiement == 'c') {
                            // Saisie du type de carte de crédit et Validation.
                            do {
                                System.out.println("Entrez le type de carte de credit (V/v pour Visa, M/m pour Mastercard):");
                                typeDeCarteCredit = Clavier.lireChar();
                                Clavier.lireFinLigne();
                                if (typeDeCarteCredit != 'V' && typeDeCarteCredit != 'v' && typeDeCarteCredit != 'M' && typeDeCarteCredit != 'm') {
                                  System.out.println("Entree Invalide !\n");  
                                }
                            } while (typeDeCarteCredit != 'V' && typeDeCarteCredit != 'v' && typeDeCarteCredit != 'M' && typeDeCarteCredit != 'm');
                            // Saisie du numero de Carte et Validation
                            do {
                                System.out.println("Entrez le numero de la carte de credit:");
                                numDeCarteCredit = Clavier.lireString();
                                if (numDeCarteCredit == "") {
                                    System.out.println("Entree Invalide !\n");
                                }
                            } while (numDeCarteCredit == "");
                            // Saisie pour assurance et Validation.
                            do {
                                System.out.println("Désirez-vous prendre l'assurance (O ou o pour Oui, N ou n pour Non) ?");
                                assurance = Clavier.lireChar();
                                Clavier.lireFinLigne();
                                if (assurance != 'O' && assurance != 'o' && assurance != 'N' && assurance != 'n') {
                                  System.out.println("Entree Invalide !\n");  
                                }
                            } while (assurance != 'O' && assurance != 'o' && assurance != 'N' && assurance != 'n');
                        } else {
                          // Saisie pour assurance et Validation.
                          do {
                                System.out.println("Désirez-vous prendre l'assurance (O ou o pour Oui, N ou n pour Non) ?");
                                assurance = Clavier.lireChar();
                                Clavier.lireFinLigne();
                                if (assurance != 'O' && assurance != 'o' && assurance != 'N' && assurance != 'n') {
                                  System.out.println("Entree Invalide !\n");  
                                }
                          } while (assurance != 'O' && assurance != 'o' && assurance != 'N' && assurance != 'n');
                        }
                        
                        
                        if ((typeVehi == 'H' || typeVehi == 'h') && (grandeurVehi == 'P' || grandeurVehi == 'p')) {
                            prixLocationParJour = PRIX_HYB_PETIT;
                            prixAssuranceParJour = PRIX_ASSUR_HYB_PETIT;
                        } else if ((typeVehi == 'H' || typeVehi == 'h') && (grandeurVehi == 'I' || grandeurVehi == 'i')) {
                            prixLocationParJour = PRIX_HYB_INTER;
                            prixAssuranceParJour = PRIX_ASSUR_HYB_INTER;
                        } else if ((typeVehi == 'H' || typeVehi == 'h') && (grandeurVehi == 'G' || grandeurVehi == 'g')) {
                            prixLocationParJour = PRIX_HYB_GRAND;
                            prixAssuranceParJour = PRIX_ASSUR_HYB_GRAND;
                        } else if ((typeVehi == 'E' || typeVehi == 'e') && (grandeurVehi == 'P' || grandeurVehi == 'p')) {
                            prixLocationParJour = PRIX_ELEC_PETIT;
                            prixAssuranceParJour = PRIX_ASSUR_ELEC_PETIT;
                        } else if ((typeVehi == 'E' || typeVehi == 'e') && (grandeurVehi == 'I' || grandeurVehi == 'i')) {
                            prixLocationParJour = PRIX_ELEC_INTER;
                            prixAssuranceParJour = PRIX_ASSUR_ELEC_INTER;
                        } else if ((typeVehi == 'E' || typeVehi == 'e') && (grandeurVehi == 'G' || grandeurVehi == 'g')) {
                            prixLocationParJour = PRIX_ELEC_GRAND;
                            prixAssuranceParJour = PRIX_ASSUR_ELEC_GRAND;
                        }
                        // Vérifier si le rabais de 20% s'applique
                        rabaisAss = false;
                        if (nbDeJourDeLocation > 15 && (typeVehi == 'E' || typeVehi == 'e') &&
                            (grandeurVehi == 'P' || grandeurVehi == 'p' || grandeurVehi == 'I' || grandeurVehi == 'i')) {
                            prixLocationParJour *= RABAIS_20; // Appliquer le rabais de 20%
                            rabaisAss = true;
                        }
                        // Calculs Location et Assurance.
                        montantLocation = nbDeJourDeLocation * prixLocationParJour;
                        montantAssurance = (assurance == 'O' || assurance == 'o') ? nbDeJourDeLocation * prixAssuranceParJour : 0;
                        sousTotal = montantLocation + montantAssurance;
                        // Calculs des Taxes TPS&TVQ.
                        sousTotalTps = sousTotal * TAXE_TPS;
                        sousTotalTvq = sousTotal * TAXE_TVQ;
                        // calcul du total de la facture
                        totalFacture = sousTotal + sousTotalTps + sousTotalTvq;
                        // Ajustement de l'inventaire après location
                        if (typeVehi == 'H' || typeVehi == 'h') {
                            if (grandeurVehi == 'P' || grandeurVehi == 'p') {
                                nbVoitureHybPetit--;
                                vehHybPetitLouee++;
                            } else if (grandeurVehi == 'I' || grandeurVehi == 'i') {
                                nbVoitureHybInter--;
                                vehHybInterLouee++;
                            } else if (grandeurVehi == 'G' || grandeurVehi == 'g') {
                                nbVoitureHybGrand--;
                                vehHybGrandLouee++;
                            }
                        } else if (typeVehi == 'E' || typeVehi == 'e') {
                            if (grandeurVehi == 'P' || grandeurVehi == 'p') {
                                nbVoitureElecPetit--;
                                vehElecPetitLouee++;
                            } else if (grandeurVehi == 'I' || grandeurVehi == 'i') {
                                nbVoitureElecInter--;
                                vehElecInterLouee++;
                            } else if (grandeurVehi == 'G' || grandeurVehi == 'g') {
                                nbVoitureElecGrand--;
                                vehElecGrandLouee++;
                            }
                        }
                        // Mide a jour des dates.
                        LocalDateTime dateDebutLocation = dateCourante.plusHours(3);
                        LocalDateTime dateRetourLocation = dateDebutLocation.plusDays(nbDeJourDeLocation);
                        String dateDebutLocationFormatee = dateDebutLocation.format(formatter);
                        String dateRetourLocationFormatee = dateRetourLocation.format(formatter);
                        // Gestion de l'incrementation de la facture.
                        compteurFacture++;
                        // Affichage de la facture.
                        System.out.println();// Saut d'une ligne
                        System.out.println("--------------------------------------------------------");// Saut d'une ligne
                        System.out.println("  " + nomCompagnie);
                        System.out.println("  Adresse        " + adressCompagnie);
                        System.out.println("  Telephone      " + telCompagnie);
                        System.out.println("  Date           " + dateFormatee);
                        System.out.println("  Facture No     " + compteurFacture);
                        System.out.println("--------------------------------------------------------\n");
                        System.out.println("  Prenom et Nom: " + prenomLocataire + " " + nomLocataire);
                        System.out.println("  Telephone: " + numTelLocataire);
                        System.out.println("  Permis de Conduire: " + numPermLocataire + "\n");
                        if (typeVehi == 'H' || typeVehi == 'h') {
                            System.out.println("  Type de vehicule: Hybride");
                        } else {
                            System.out.println("  Type de vehicule: Electrique");
                        }
                        if (grandeurVehi == 'P' || grandeurVehi == 'p') {
                            System.out.println("  Grandeur du vehicule: Petit\n");
                        } else if (grandeurVehi == 'I' || grandeurVehi == 'i') {
                            System.out.println("  Grandeur du vehicule: Intermediaire\n");
                        } else if (grandeurVehi == 'G' || grandeurVehi == 'g') {
                            System.out.println("  Grandeur du vehicule: Grand\n");
                        }
                        System.out.println("  Nombre de jours de location: " + nbDeJourDeLocation);
                        System.out.println("  Date de location: " + dateDebutLocationFormatee);
                        System.out.println("  Date de retour :  " + dateRetourLocationFormatee + "\n");
                        System.out.println("  Prix de la location par jour     " + String.format("%.2f", prixLocationParJour) + " $");
                        System.out.println("  Prix de l'assurance par jour     " + String.format("%.2f", prixAssuranceParJour) + " $\n");
                        if (rabaisAss) {
                            System.out.println("  Rabais de 20% applique\n");
                        }
                        System.out.println("  Montant de la location           " + String.format("%.2f", montantLocation) + " $");
                        System.out.println("  Montant de l'assurance           " + String.format("%.2f", montantAssurance) + " $\n");
                        System.out.println("  Sous-total                       " + String.format("%.2f", sousTotal) + " $");
                        System.out.println("  Montant TPS                      " + String.format("%.2f", sousTotalTps) + " $");
                        System.out.println("  Montant TVQ                      " + String.format("%.2f", sousTotalTvq) + " $");
                        System.out.println("  Montant Total                    " + String.format("%.2f", totalFacture) + " $\n");
                        System.out.println("--------------------------------------------------------\n");
                        System.out.println("  Merci pour votre confiance !");
                    } else {
                        System.out.println("Malheureusement, Ce modele n'est pas disponible !");
                    }
                    System.out.println();// Saut d'une ligne
                    System.out.println("Appuyez sur <ENTER> pour revenir au menu.");
                    Clavier.lireFinLigne();
                    break;
                case 3:
                    System.out.println();// Saut d'une ligne
                    System.out.println("--------------------------------------------------------");// Saut d'une ligne
                    System.out.println("  " + nomCompagnie);
                    System.out.println("  Adresse       " + adressCompagnie);
                    System.out.println("  Telephone     " + telCompagnie);
                    System.out.println("  Date          " + dateFormatee);
                    System.out.println("--------------------------------------------------------\n");
                    System.out.println("  Nombre de véhicules loués par type et par catégorie");
                    System.out.println("  ***************************************************\n");
                    System.out.println("  Grandeur          Hybride      Électrique");
                    System.out.println("  ******************************************\n");
                    System.out.println("  Petit               " + vehHybPetitLouee + "             " + vehElecPetitLouee);
                    System.out.println("  Intermediaire       " + vehHybInterLouee + "             " + vehElecInterLouee);
                    System.out.println("  Grand               " + vehHybGrandLouee + "             " + vehElecGrandLouee + "\n");
                    System.out.println("--------------------------------------------------------\n");
                    System.out.println();// Saut d'une ligne
                    System.out.println("Appuyez sur <ENTER> pour revenir au menu.");
                    Clavier.lireFinLigne();
                    break;
                case 4:
                    System.out.println();// Saut d'une ligne
                    System.out.println("Merci et à la prochaine !\n");
                    System.out.println("Fermeture du programme ...");
                    break;
                default:
                    System.out.println();// Saut d'une ligne
                    System.out.println("Choix invalide, veuillez réessayer.\n");
            }
        }   while (choix != 4);
    }
}