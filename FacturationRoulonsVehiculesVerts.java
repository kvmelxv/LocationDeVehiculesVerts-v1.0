import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Programme de gestion de location de véhicules verts (hybrides et électriques).
 * Ce système permet d'afficher l'inventaire des véhicules disponibles, de facturer
 * la location d'un véhicule, et d'afficher les statistiques des véhicules loués.
 * Il gère également les taxes, les assurances, et applique des rabais selon les conditions.
 *
 * @author Mustapha Kamel Cherif - CHEM14129808
 * @version v1.1 | 19 mars 2025
 * @cours INF1120 - Groupe 10
 * @titre Travail pratique 2 - Hiver 2025
 */
public class FacturationRoulonsVehiculesVerts {

    /** Constantes pour les prix de location par jour */
    public static final double PRIX_HYB_PETIT = 55.75;
    public static final double PRIX_HYB_INTER = 60.25;
    public static final double PRIX_HYB_GRAND = 65.50;
    public static final double PRIX_ELEC_PETIT = 45.50;
    public static final double PRIX_ELEC_INTER = 50.50;
    public static final double PRIX_ELEC_GRAND = 55.25;

    /** Constantes pour les prix d'assurance par jour */
    public static final double PRIX_ASSUR_HYB_PETIT = 13.50;
    public static final double PRIX_ASSUR_HYB_INTER = 14.50;
    public static final double PRIX_ASSUR_HYB_GRAND = 15.50;
    public static final double PRIX_ASSUR_ELEC_PETIT = 12.50;
    public static final double PRIX_ASSUR_ELEC_INTER = 12.75;
    public static final double PRIX_ASSUR_ELEC_GRAND = 13.25;

    /** Constantes pour les taxes */
    public static final double TAXE_TPS = 0.05;
    public static final double TAXE_TVQ = 0.09975;

    /** Constantes pour les rabais */
    public static final double RABAIS_20 = 0.20;

    /** Constantes pour les quantités de véhicules */
    public static final int NB_VOITURE_HYB_PETIT = 12;
    public static final int NB_VOITURE_HYB_INTER = 10;
    public static final int NB_VOITURE_HYB_GRAND = 3;
    public static final int NB_VOITURE_ELEC_PETIT = 11;
    public static final int NB_VOITURE_ELEC_INTER = 9;
    public static final int NB_VOITURE_ELEC_GRAND = 5;

    /** Constantes pour les informations de la compagnie */
    public static final String NOM_COMPAGNIE = "Roulons les Véhicules Verts (RVV)";
    public static final String ADRESSE_COMPAGNIE = "1500 rue Matata, Hakuna, Québec Y0Z 6Y7";
    public static final String TEL_COMPAGNIE = "438 222-1111";

    /**
     * Méthode pour afficher le message de bienvenue.
     */
    public static void affichageDuMessageDeBienvenue() {
        System.out.println("************************************************************************************");
        System.out.println("*   Bienvenue dans le système de facturation de Roulons les Véhicules Verts (RVV)  *");
        System.out.println("************************************************************************************\n");
    }

    /**
     * Méthode pour saisir et valider l'option choisie par l'utilisateur.
     * @return l'option choisie par l'utilisateur.
     */
    public static int saisirEtValiderOption() {
        int choix;
        boolean estValide;
        do {
            System.out.print("Entrez votre choix (1-4) : ");
            choix = Clavier.lireInt();
            estValide = choix >= 1 && choix <= 4;
            if (!estValide) {
                System.out.println("L’option choisie est invalide!");
            }
        } while (!estValide);
        return choix;
    }

    /**
     * Méthode pour saisir et valider le prénom du locataire.
     * @return Le prénom du locataire.
     */
    public static String saisirEtValiderPrenom() {
        String prenom;
        boolean estValide;
        do {
            System.out.print("Entrez le prénom du locataire : ");
            prenom = Clavier.lireString().trim();
            estValide = prenom.length() >= 2 && prenom.length() <= 30;
            if (!estValide) {
                System.out.println("Le prénom est invalide! Veuillez entrer un prénom entre 2 et 30 caractères.");
            }
        } while (!estValide);
        return prenom;
    }

    /**
     * Méthode pour saisir et valider le nom du locataire.
     * @return Le nom du locataire.
     */
    public static String saisirEtValiderNom() {
        String nom;
        boolean estValide;
        do {
            System.out.print("Entrez le nom du locataire : ");
            nom = Clavier.lireString().trim();
            estValide = nom.length() >= 2 && nom.length() <= 30;
            if (!estValide) {
                System.out.println("Le nom est invalide! Veuillez entrer un nom entre 2 et 30 caractères.");
            }
        } while (!estValide);
        return nom;
    }

    /**
     * Méthode pour saisir et valider le numéro de téléphone du locataire.
     * @return Le numéro de téléphone du locataire.
     */
    public static String saisirEtValiderNumeroTelephone() {
        String numTel;
        boolean estValide;
        do {
            System.out.print("Entrez le numéro de téléphone du locataire (format: (NNN) NNN-NNNN) : ");
            numTel = Clavier.lireString().trim();
            estValide = numTel.length() == 14 && numTel.matches("\\(\\d{3}\\)\\s\\d{3}-\\d{4}");
            if (!estValide) {
                System.out.println("Le numéro de téléphone est invalide!");
            }
        } while (!estValide);
        return numTel;
    }

    /**
     * Méthode pour saisir et valider le numéro de permis de conduire.
     * @return Le numéro de permis de conduire.
     */
    public static String saisirEtValiderNumeroPermis() {
        String numPermis;
        boolean estValide;
        do {
            System.out.print("Entrez le numéro du permis de conduire du locataire (format: ANNNN-NNNNNN-NN) : ");
            numPermis = Clavier.lireString().trim();
            estValide = numPermis.length() == 15 && numPermis.matches("[A-Za-z]\\d{4}-\\d{6}-\\d{2}");
            if (!estValide) {
                System.out.println("Le numéro du permis de conduire est invalide!");
            }
        } while (!estValide);
        return numPermis;
    }

    /**
     * Méthode pour saisir et valider le type de véhicule.
     * @return Le type de véhicule.
     */
    public static char saisirEtValiderTypeVeh() {
        char type;
        boolean estValide;
        do {
            System.out.println("Entrez le type du véhicule (H/h pour Hybride, E/e pour Électrique) : ");
            type = Clavier.lireChar();
            Clavier.lireFinLigne();
            estValide = type == 'H' || type == 'h' || type == 'E' || type == 'e';
            if (!estValide) {
                System.out.println("Le type de véhicule est invalide!");
            }
        } while (!estValide);
        return type;
    }

    /**
     * Méthode pour saisir et valider la grandeur du véhicule.
     * @return La grandeur du véhicule.
     */
    public static char saisirEtValiderGrandeurVeh() {
        char grandeur;
        boolean estValide;
        do {
            System.out.println("Entrez la grandeur du véhicule (P/p pour Petit, I/i pour Intermédiaire, G/g pour Grand) : ");
            grandeur = Clavier.lireChar();
            Clavier.lireFinLigne();
            estValide = grandeur == 'P' || grandeur == 'p' || grandeur == 'I' || grandeur == 'i' || grandeur == 'G' || grandeur == 'g';
            if (!estValide) {
                System.out.println("La grandeur du véhicule est invalide!");
            }
        } while (!estValide);
        return grandeur;
    }

    /**
     * Méthode pour saisir et valider le nombre de jours de location.
     * @return Le nombre de jours de location.
     */
    public static int saisirEtValiderNbDeJoursDeLoc() {
        int nombreJours;
        boolean estValide;
        do {
            System.out.println("Entrez le nombre de jour de location (1-30): ");
            nombreJours = Clavier.lireInt();
            estValide = nombreJours >= 1 && nombreJours <= 30;
            if (!estValide) {
                System.out.println("Le nombre de jours est invalide!");
            }
        } while (!estValide);
        return nombreJours;
    }

    /**
     * Méthode pour saisir et valider le mode de paiement.
     * @return Le mode de paiement.
     */
    public static char saisirEtValiderModePaiement() {
        char modePaiement;
        boolean estValide;
        do {
            System.out.println("Entrez le mode de paiement D/d pour Debit et C/c pour Credit:");
            modePaiement = Clavier.lireChar();
            Clavier.lireFinLigne();
            estValide = modePaiement == 'D' || modePaiement == 'd' || modePaiement == 'C' || modePaiement == 'c';
            if (!estValide) {
                System.out.println("Le mode de paiement est invalide!");
            }
        } while (!estValide);
        return modePaiement;
    }

    /**
     * Méthode pour saisir et valider le type de carte de crédit.
     * @return Le type de carte de crédit.
     */
    public static char saisirEtValiderTypeCarteCredit() {
        char typeCredit;
        boolean estValide;
        do {
            System.out.println("Entrez le type de carte de credit (V/v pour Visa, M/m pour Mastercard):");
            typeCredit = Clavier.lireChar();
            Clavier.lireFinLigne();
            estValide = typeCredit == 'V' || typeCredit == 'v' || typeCredit == 'M' || typeCredit == 'm';
            if (!estValide) {
                System.out.println("Le type de carte de credit est invalide!");
            }
        } while (!estValide);
        return typeCredit;
    }

    /**
     * Méthode pour saisir et valider le numéro de carte de crédit.
     * @return Le numéro de carte de crédit.
     */
    public static String saisirEtValiderNumCarteCredit() {
        String num;
        boolean estValide;
        do {
            System.out.println("Entrez le numero de la carte de credit (format: NNNN NNNN NNNN NNNN) :");
            num = Clavier.lireString().trim();
            estValide = num.length() == 19 && num.matches("\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}");
            if (!estValide) {
                System.out.println("Le numero de la carte de credit est invalide!");
            }
        } while (!estValide);
        return num;
    }

    /**
     * Méthode pour saisir et valider la réponse de la question sur l'assurance.
     * @return La réponse de l'utilisateur.
     */
    public static char saisirEtValiderReponse() {
        char reponse;
        boolean estValide;
        do {
            System.out.println("Désirez-vous prendre l'assurance (O/o pour Oui, N/n pour Non) ?");
            reponse = Clavier.lireChar();
            Clavier.lireFinLigne();
            estValide = reponse == 'O' || reponse == 'o' || reponse == 'N' || reponse == 'n';
            if (!estValide) {
                System.out.println("La reponse est invalide!");
            }
        } while (!estValide);
        return reponse;
    }

    /**
     * Méthode pour obtenir le nombre de véhicules disponibles.
     * @param typeVehi Le type de véhicule.
     * @param grandeurVehi La grandeur du véhicule.
     * @param vehHybPetitLouee Nombre de véhicules hybrides petits loués.
     * @param vehHybInterLouee Nombre de véhicules hybrides intermédiaires loués.
     * @param vehHybGrandLouee Nombre de véhicules hybrides grands loués.
     * @param vehElecPetitLouee Nombre de véhicules électriques petits loués.
     * @param vehElecInterLouee Nombre de véhicules électriques intermédiaires loués.
     * @param vehElecGrandLouee Nombre de véhicules électriques grands loués.
     * @return Le nombre de véhicules disponibles.
     */
    public static int obtenirNombreVehiculesDisponibles(char typeVehi, char grandeurVehi, int vehHybPetitLouee, int vehHybInterLouee, int vehHybGrandLouee, int vehElecPetitLouee, 
                                                        int vehElecInterLouee, int vehElecGrandLouee) {
        boolean estHybride = typeVehi == 'h' || typeVehi == 'H';
        boolean estElectrique = typeVehi == 'e' || typeVehi == 'E';
        boolean estPetit = grandeurVehi == 'p' || grandeurVehi == 'P';
        boolean estIntermediaire = grandeurVehi == 'i' || grandeurVehi == 'I';
        boolean estGrand = grandeurVehi == 'g' || grandeurVehi == 'G';
        int voituresDispo = 0;

        if (estHybride) {
            if (estPetit) {
                voituresDispo = NB_VOITURE_HYB_PETIT - vehHybPetitLouee;
            } else if (estIntermediaire) {
                voituresDispo = NB_VOITURE_HYB_INTER - vehHybInterLouee;
            } else if (estGrand) {
                voituresDispo = NB_VOITURE_HYB_GRAND - vehHybGrandLouee;
            }
        } else if (estElectrique) {
            if (estPetit) {
                voituresDispo = NB_VOITURE_ELEC_PETIT - vehElecPetitLouee;
            } else if (estIntermediaire) {
                voituresDispo = NB_VOITURE_ELEC_INTER - vehElecInterLouee;
            } else if (estGrand) {
                voituresDispo = NB_VOITURE_ELEC_GRAND - vehElecGrandLouee;
            }
        }
        return voituresDispo;
    }

    /**
     * Méthode pour déterminer le prix de location par jour selon le type et la grandeur du véhicule.
     * @param typeVehi Le type de véhicule.
     * @param grandeurVehi La grandeur du véhicule.
     * @return Le prix de location par jour.
     */
    public static double determinerPrixLocationParJour(char typeVehi, char grandeurVehi) {
        boolean estHybride = typeVehi == 'H' || typeVehi == 'h';
        boolean estElectrique = typeVehi == 'E' || typeVehi == 'e';
        boolean estPetit = grandeurVehi == 'P' || grandeurVehi == 'p';
        boolean estIntermediaire = grandeurVehi == 'I' || grandeurVehi == 'i';
        boolean estGrand = grandeurVehi == 'G' || grandeurVehi == 'g';
        double prixParJour = 0;
        
        if (estHybride) {
            if (estPetit) {
                prixParJour = PRIX_HYB_PETIT;
            } else if (estIntermediaire) {
                prixParJour = PRIX_HYB_INTER;
            } else if (estGrand) {
                prixParJour = PRIX_HYB_GRAND;
            }
        } else if (estElectrique) {
            if (estPetit) {
                prixParJour = PRIX_ELEC_PETIT;
            } else if (estIntermediaire) {
                prixParJour = PRIX_ELEC_INTER;
            } else if (estGrand) {
                prixParJour = PRIX_ELEC_GRAND;
            }
        }
        return prixParJour;
    }

    /**
     * Méthode pour déterminer le prix de l'assurance par jour selon le type et la grandeur du véhicule.
     * @param typeVehi Le type de véhicule.
     * @param grandeurVehi La grandeur du véhicule.
     * @return Le prix de l'assurance par jour.
     */
    public static double determinerPrixAssuranceParJour(char typeVehi, char grandeurVehi) {
        boolean estHybride = typeVehi == 'H' || typeVehi == 'h';
        boolean estElectrique = typeVehi == 'E' || typeVehi == 'e';
        boolean estPetit = grandeurVehi == 'P' || grandeurVehi == 'p';
        boolean estIntermediaire = grandeurVehi == 'I' || grandeurVehi == 'i';
        boolean estGrand = grandeurVehi == 'G' || grandeurVehi == 'g';
        double prixParJour = 0;

        if (estHybride) {
            if (estPetit) {
                prixParJour = PRIX_ASSUR_HYB_PETIT;
            } else if (estIntermediaire) {
                prixParJour = PRIX_ASSUR_HYB_INTER;
            } else if (estGrand) {
                prixParJour = PRIX_ASSUR_HYB_GRAND;
            }
        } else if (estElectrique) {
            if (estPetit) {
                prixParJour = PRIX_ASSUR_ELEC_PETIT;
            } else if (estIntermediaire) {
                prixParJour = PRIX_ASSUR_ELEC_INTER;
            } else if (estGrand) {
                prixParJour = PRIX_ASSUR_ELEC_GRAND;
            }
        }
        return prixParJour;
    }

    /**
     * Méthode pour déterminer le rabais de 20% sur l'assurance.
     * @param typeVehi Le type de véhicule.
     * @param grandeurVehi La grandeur du véhicule.
     * @param prixLocationParJour Le prix de location par jour.
     * @param nbDeJourDeLocation Le nombre de jours de location.
     * @return Le montant du rabais.
     */
    public static double determinerRabais(char typeVehi, char grandeurVehi, double prixLocationParJour, int nbDeJourDeLocation) {
        boolean estLongueLocation = nbDeJourDeLocation > 15;
        boolean estElectrique = typeVehi == 'E' || typeVehi == 'e';
        boolean estPetitOuIntermediaire = grandeurVehi == 'P' || grandeurVehi == 'p' || grandeurVehi == 'I' || grandeurVehi == 'i';
        
        if (estLongueLocation && estElectrique && estPetitOuIntermediaire) {
            return prixLocationParJour * RABAIS_20;
        } else {
            return 0;
        }
    }

    /**
     * Méthode pour calculer le montant de la location du véhicule.
     * @param nbDeJourDeLocation Le nombre de jours de location.
     * @param prixLocationParJour Le prix de location par jour.
     * @param rabais Le montant du rabais.
     * @return Le montant de la location.
     */
    public static double calculerMontantLocation(int nbDeJourDeLocation, double prixLocationParJour, double rabais) {
        double montantTotalAvantRabais = nbDeJourDeLocation * prixLocationParJour;
        double montantTotalApresRabais = montantTotalAvantRabais - rabais;
        return montantTotalApresRabais;
    }

    /**
     * Méthode pour calculer le montant de l'assurance du véhicule.
     * @param nbDeJourDeLocation Le nombre de jours de location.
     * @param prixAssuranceParJour Le prix de l'assurance par jour.
     * @return Le montant de l'assurance.
     */
    public static double calculerMontantAssurance(int nbDeJourDeLocation, double prixAssuranceParJour) {
        return nbDeJourDeLocation * prixAssuranceParJour;
    }

    /**
     * Méthode pour calculer le sous-total de la facture.
     * @param montantLocation Le montant de la location.
     * @param montantAssurance Le montant de l'assurance.
     * @return Le sous-total de la facture.
     */
    public static double calculerSousTotalFacture(double montantLocation, double montantAssurance) {
        return montantLocation + montantAssurance;
    }

    /**
     * Méthode pour calculer le montant de la taxe TPS.
     * @param sousTotalFacture Le sous-total de la facture.
     * @return Le montant de la taxe TPS.
     */
    public static double calculerMontantTPS(double sousTotalFacture) {
        return sousTotalFacture * TAXE_TPS;
    }

    /**
     * Méthode pour calculer le montant de la taxe TVQ.
     * @param sousTotalFacture Le sous-total de la facture.
     * @return Le montant de la taxe TVQ.
     */
    public static double calculerMontantTVQ(double sousTotalFacture) {
        return sousTotalFacture * TAXE_TVQ;
    }

    /**
     * Méthode pour calculer le montant total de la facture.
     * @param sousTotalFacture Le sous-total de la facture.
     * @param montantTPS Le montant de la taxe TPS.
     * @param montantTVQ Le montant de la taxe TVQ.
     * @return Le montant total de la facture.
     */
    public static double calculerMontantTotalFacture(double sousTotalFacture, double montantTPS, double montantTVQ) {
        return sousTotalFacture + montantTPS + montantTVQ;
    }

    /**
     * Méthode pour incrémenter le numéro de facture.
     * @param numFacture Le numéro de facture actuel.
     * @return Le nouveau numéro de facture.
     */
    public static int incrementerNumeroFacture(int numFacture) {
        return numFacture + 1;
    }

    /**
     * Méthode pour calculer la date et l'heure de début de location.
     * @param dateFacture La date et l'heure de la facture.
     * @return La date et l'heure de début de location.
     */
    private static LocalDateTime calculerDateHeureLocation(LocalDateTime dateFacture) {
        return dateFacture.plusHours(3);
    }

    /**
     * Méthode pour calculer la date et l'heure de retour du véhicule.
     * @param dateDebutLocation La date et l'heure de début de location.
     * @param nbJoursLocation Le nombre de jours de location.
     * @return La date et l'heure de retour du véhicule.
     */
    private static LocalDateTime calculerDateHeureRetour(LocalDateTime dateDebutLocation, int nbJoursLocation) {
        return dateDebutLocation.plusDays(nbJoursLocation);
    }

    /**
     * Méthode pour incrémenter le nombre de véhicules loués si le type et la grandeur correspondent.
     * @param typeVehiSaisi Le type de véhicule saisi (H/h pour Hybride, E/e pour Électrique).
     * @param typeVehiCible Le type de véhicule ciblé (H/h pour Hybride, E/e pour Électrique).
     * @param grandeurVehiSaisi La grandeur de véhicule saisie (P/p pour Petit, I/i pour Intermédiaire, G/g pour Grand).
     * @param grandeurVehiCible La grandeur de véhicule ciblée (P/p pour Petit, I/i pour Intermédiaire, G/g pour Grand).
     * @param nombreVehiculesLoues Le nombre de véhicules loués à incrémenter.
     * @return Le nombre de véhicules loués après incrémentation (ou sans changement si les types/grandeurs ne correspondent pas).
     */
    public static int incrementerNombreVehiculesLoues(char typeVehiSaisi, char typeVehiCible, char grandeurVehiSaisi, char grandeurVehiCible, int nombreVehiculesLoues) {
        // Convertir en minuscules.
        typeVehiSaisi = Character.toLowerCase(typeVehiSaisi);
        typeVehiCible = Character.toLowerCase(typeVehiCible);
        grandeurVehiSaisi = Character.toLowerCase(grandeurVehiSaisi);
        grandeurVehiCible = Character.toLowerCase(grandeurVehiCible);
        
        if (typeVehiSaisi == typeVehiCible && grandeurVehiSaisi == grandeurVehiCible) {
            nombreVehiculesLoues++;
        }
        return nombreVehiculesLoues;
    }
    
    /**
     * Méthode pour afficher les en-têtes de la compagnie.
     * @param dateFormatee La date formatée à afficher.
     */
    public static void afficherEntetes(String dateCouranteFormatee) {
        System.out.println("--------------------------------------------------------");
        System.out.println("  " + NOM_COMPAGNIE);
        System.out.println("  Adresse       " + ADRESSE_COMPAGNIE);
        System.out.println("  Telephone     " + TEL_COMPAGNIE);
        System.out.println("  Date          " + dateCouranteFormatee);
        System.out.println("--------------------------------------------------------\n");
    }
    
    /**
     * Méthode pour afficher le menu principal.
     */
    public static void afficherMenu() {
        System.out.println("***  Bienvenue dans le menu ! Faites votre choix : ***\n");
        System.out.println("------------------------------------------------------");
        System.out.println("   1 - Afficher l’inventaire des véhicules");
        System.out.println("   2 - Facturer la location d’un véhicule");
        System.out.println("   3 - Afficher le nombre de véhicules hybrides et électriques loués");
        System.out.println("   4 - Quitter le programme \n");
    }
    
    /**
     * Méthode pour afficher le titre de l'option 2.
     */
    public static void afficherTitreOption2() {
        System.out.println();
        System.out.println("Facturation de la location d’un véhicule");
        System.out.println("****************************************\n");
    }
        
    /**
     * Méthode pour obtenir la description du type de véhicule.
     * @param typeVehi Le type de véhicule.
     * @return La description du type de véhicule.
     */
    private static String obtenirDescriptionTypeVehicule(char typeVehi) {
        boolean estHybride = typeVehi == 'H' || typeVehi == 'h';
        if (estHybride) {
            return "Hybride";
        } else {
            return "Électrique";
        }
    }

    /**
     * Méthode pour obtenir la description de la grandeur du véhicule.
     * @param grandeurVehi La grandeur du véhicule.
     * @return La description de la grandeur du véhicule.
     */
    private static String obtenirDescriptionGrandeurVehicule(char grandeurVehi) {
        boolean estPetit = grandeurVehi == 'P' || grandeurVehi == 'p';
        boolean estIntermediaire = grandeurVehi == 'I' || grandeurVehi == 'i';
        if (estPetit) {
            return "Petit";
        } else if (estIntermediaire) {
            return "Intermédiaire";
        } else {
            return "Grand";
        }
    }

    /**
     * Méthode pour afficher les véhicules disponibles.
     * @param vehHybPetitLouee Nombre de véhicules hybrides petits loués.
     * @param vehHybInterLouee Nombre de véhicules hybrides intermédiaires loués.
     * @param vehHybGrandLouee Nombre de véhicules hybrides grands loués.
     * @param vehElecPetitLouee Nombre de véhicules électriques petits loués.
     * @param vehElecInterLouee Nombre de véhicules électriques intermédiaires loués.
     * @param vehElecGrandLouee Nombre de véhicules électriques grands loués.
     */
    public static void afficherVehiculesDisponibles(int vehHybPetitLouee, int vehHybInterLouee, int vehHybGrandLouee, int vehElecPetitLouee, int vehElecInterLouee, int vehElecGrandLouee) {
        int hybPetitDispo = NB_VOITURE_HYB_PETIT - vehHybPetitLouee;
        int hybInterDispo = NB_VOITURE_HYB_INTER - vehHybInterLouee;
        int hybGrandDispo = NB_VOITURE_HYB_GRAND - vehHybGrandLouee;
        int elecPetitDispo = NB_VOITURE_ELEC_PETIT - vehElecPetitLouee;
        int elecInterDispo = NB_VOITURE_ELEC_INTER - vehElecInterLouee;
        int elecGrandDispo = NB_VOITURE_ELEC_GRAND - vehElecGrandLouee;

        System.out.println("--------------------------------------------------------");
        System.out.println("  Nombre de véhicules disponibles dans l'inventaire");
        System.out.println("  *************************************************\n");
        System.out.println("  Grandeur          Hybride      Électrique");
        System.out.println("  ******************************************\n");
        System.out.println("  Petit               " + hybPetitDispo + "            " + elecPetitDispo);
        System.out.println("  Intermediaire       " + hybInterDispo + "            " + elecInterDispo);
        System.out.println("  Grand               " + hybGrandDispo + "             " + elecGrandDispo + "\n");
        System.out.println("--------------------------------------------------------\n");
    }

    /**
     * Méthode pour afficher la facture.
     * @param numFacture Le numéro de facture.
     * @param prenomLocataire Le prénom du locataire.
     * @param nomLocataire Le nom du locataire.
     * @param numTelLocataire Le numéro de téléphone du locataire.
     * @param numPermLocataire Le numéro de permis du locataire.
     * @param typeVehi Le type de véhicule.
     * @param grandeurVehi La grandeur du véhicule.
     * @param modePaiement Le mode de paiement.
     * @param typeDeCarteCredit Le type de carte de crédit.
     * @param numDeCarteCredit Le numéro de carte de crédit.
     * @param nbDeJourDeLocation Le nombre de jours de location.
     * @param prixLocationParJour Le prix de location par jour.
     * @param prixAssuranceParJour Le prix de l'assurance par jour.
     * @param rabais Le montant du rabais.
     * @param montantLocation Le montant de la location.
     * @param montantAssurance Le montant de l'assurance.
     * @param sousTotal Le sous-total de la facture.
     * @param montantTps Le montant de la taxe TPS.
     * @param montantTvq Le montant de la taxe TVQ.
     * @param totalFacture Le montant total de la facture.
     * @param dateCourante La date et l'heure courantes.
     */
    public static void afficherFacture(int numFacture, String prenomLocataire, String nomLocataire, String numTelLocataire,
                                      String numPermLocataire, char typeVehi, char grandeurVehi, char modePaiement,
                                      char typeDeCarteCredit, String numDeCarteCredit, int nbDeJourDeLocation,
                                      double prixLocationParJour, double prixAssuranceParJour, double rabais,
                                      double montantLocation, double montantAssurance, double sousTotal,
                                      double montantTps, double montantTvq, double totalFacture, LocalDateTime dateCourante) {

        LocalDateTime dateDebutLocation = calculerDateHeureLocation(dateCourante);
        LocalDateTime dateRetourLocation = calculerDateHeureRetour(dateDebutLocation, nbDeJourDeLocation);
        
        DateTimeFormatter DateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        
        String dateDebutLocationFormatee = dateDebutLocation.format(DateFormatter);
        String dateRetourLocationFormatee = dateRetourLocation.format(DateFormatter);
        String dateCouranteFormatee = dateCourante.format(DateFormatter);

        String descriptionTypeVehicule = obtenirDescriptionTypeVehicule(typeVehi);
        String descriptionGrandeurVehicule = obtenirDescriptionGrandeurVehicule(grandeurVehi);
        
        boolean rabaisApplique = rabais > 0;
        boolean modePaiementParCredit = modePaiement == 'C' || modePaiement == 'c';

        System.out.println("--------------------------------------------------------");
        System.out.println("  " + NOM_COMPAGNIE);
        System.out.println("  Adresse       " + ADRESSE_COMPAGNIE);
        System.out.println("  Telephone     " + TEL_COMPAGNIE);
        System.out.println("  Date          " + dateCouranteFormatee);
        System.out.println("  Facture No : " + numFacture);
        System.out.println("--------------------------------------------------------\n");

        System.out.println("  Prénom et Nom : " + prenomLocataire + " " + nomLocataire);
        System.out.println("  Téléphone : " + numTelLocataire);
        System.out.println("  Permis de Conduire : " + numPermLocataire + "\n");

        System.out.println("  Type de véhicule : " + descriptionTypeVehicule);
        System.out.println("  Grandeur du véhicule : " + descriptionGrandeurVehicule + "\n");

        System.out.println("  Date de début de location : " + dateDebutLocationFormatee);
        System.out.println("  Date de retour prévue : " + dateRetourLocationFormatee + "\n");
        System.out.println("  Nombre de jours de location : " + nbDeJourDeLocation + "\n");
        System.out.println("  Prix de la location par jour : " + String.format("%.2f", prixLocationParJour) + " $");
        System.out.println("  Prix de l'assurance par jour : " + String.format("%.2f", prixAssuranceParJour) + " $\n");

        if (rabaisApplique) {
            System.out.println("  Rabais appliqué : " + String.format("%.2f", rabais) + " $\n");
        }

        System.out.println("  Montant de la location : " + String.format("%.2f", montantLocation) + " $");
        System.out.println("  Montant de l'assurance : " + String.format("%.2f", montantAssurance) + " $\n");
        System.out.println("  Sous-total : " + String.format("%.2f", sousTotal) + " $");
        System.out.println("  Montant TPS : " + String.format("%.2f", montantTps) + " $");
        System.out.println("  Montant TVQ : " + String.format("%.2f", montantTvq) + " $");
        System.out.println("  Montant Total : " + String.format("%.2f", totalFacture) + " $\n");

        if (modePaiementParCredit) {
            System.out.println("  Mode de paiement : Carte de crédit");
            System.out.println("  Type de carte : " + (typeDeCarteCredit == 'V' || typeDeCarteCredit == 'v' ? "Visa" : "Mastercard"));
            System.out.println("  Numéro de carte : " + numDeCarteCredit + "\n");
        } else {
            System.out.println("  Mode de paiement : Débit\n");
        }

        System.out.println("--------------------------------------------------------\n");
        System.out.println("  Merci pour votre confiance !");
        System.out.println("--------------------------------------------------------\n");
    }

    /**
     * Méthode pour afficher le nombre de véhicules loués par type et par grandeur.
     * @param vehHybPetitLouee Nombre de véhicules hybrides petits loués.
     * @param vehHybInterLouee Nombre de véhicules hybrides intermédiaires loués.
     * @param vehHybGrandLouee Nombre de véhicules hybrides grands loués.
     * @param vehElecPetitLouee Nombre de véhicules électriques petits loués.
     * @param vehElecInterLouee Nombre de véhicules électriques intermédiaires loués.
     * @param vehElecGrandLouee Nombre de véhicules électriques grands loués.
     */
    public static void afficherVehiculesLoues(int vehHybPetitLouee, int vehHybInterLouee, int vehHybGrandLouee,
                                             int vehElecPetitLouee, int vehElecInterLouee, int vehElecGrandLouee) {
        System.out.println("--------------------------------------------------------");
        System.out.println("  Nombre de véhicules loués par type et par grandeur");
        System.out.println("  ***************************************************\n");
        System.out.println("  Grandeur          Hybride      Électrique");
        System.out.println("  ******************************************\n");
        System.out.println("  Petit               " + vehHybPetitLouee + "             " + vehElecPetitLouee);
        System.out.println("  Intermediaire       " + vehHybInterLouee + "             " + vehElecInterLouee);
        System.out.println("  Grand               " + vehHybGrandLouee + "             " + vehElecGrandLouee + "\n");
        System.out.println("--------------------------------------------------------\n");
    }

    /**
     * Méthode principale.
     */
    public static void main(String[] args) {
        // Variables locales
        LocalDateTime dateCourante = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dateCouranteFormatee = dateCourante.format(formatter);

        int choix;
        char typeVehi, grandeurVehi;
        int voitureDisponible = 0;
        int nbDeJourDeLocation;
        String prenomLocataire, nomLocataire, numTelLocataire, numPermLocataire;
        char modePaiement, typeDeCarteCredit = ' ', assurance;
        String numDeCarteCredit = "";
        double rabais = 0;
        int numFacture = 0;
        double prixLocationParJour = 0, prixAssuranceParJour = 0;
        double montantLocation, montantAssurance, sousTotal, montantTps, montantTvq, totalFacture;
        int vehElecPetitLouee = 0, vehElecInterLouee = 0, vehElecGrandLouee = 0;
        int vehHybPetitLouee = 0, vehHybInterLouee = 0, vehHybGrandLouee = 0;

        // Affichage du message de bienvenue.
        affichageDuMessageDeBienvenue();

        do {
            // Affichage du menu.
            afficherMenu();

            // Saisie et validation de l'option.
            choix = saisirEtValiderOption();

            switch (choix) {
                case 1:
                    // Affichage du tableau d'inventaire des véhicules disponibles..
                    afficherEntetes(dateCouranteFormatee);
                    afficherVehiculesDisponibles(vehHybPetitLouee, vehHybInterLouee, vehHybGrandLouee, vehElecPetitLouee, vehElecInterLouee, vehElecGrandLouee);
                    System.out.println();
                    System.out.println("Appuyez sur <ENTER> pour revenir au menu.");
                    Clavier.lireFinLigne();
                    break;
                case 2:
                    // Facturer la location d'un véhicule.
                    afficherTitreOption2();
                    // Saisie et validation du type de véhicule.
                    typeVehi = saisirEtValiderTypeVeh();
                    // Saisie et validation de la grandeur du véhicule.
                    grandeurVehi = saisirEtValiderGrandeurVeh();
                    // Vérification de la disponibilité des véhicules.
                    voitureDisponible = obtenirNombreVehiculesDisponibles(typeVehi, grandeurVehi, vehHybPetitLouee, vehHybInterLouee, 
                                                                          vehHybGrandLouee, vehElecPetitLouee, vehElecInterLouee, 
                                                                          vehElecGrandLouee);
                    if (voitureDisponible > 0) {
                        // Affichage du nombre des vehicules disponibles.
                        System.out.println();
                        System.out.println("Véhicule disponible : " + voitureDisponible + "\n");
                        // Saisie et validation du nombre de jours de location.
                        nbDeJourDeLocation = saisirEtValiderNbDeJoursDeLoc();
                        // Saisie et validation du prénom du locataire.
                        prenomLocataire = saisirEtValiderPrenom();
                        // Saisie et validation du nom du locataire.
                        nomLocataire = saisirEtValiderNom();
                        // Saisie et validation du numéro de téléphone.
                        numTelLocataire = saisirEtValiderNumeroTelephone();
                        // Saisie et validation du numéro de permis.
                        numPermLocataire = saisirEtValiderNumeroPermis();
                        // Saisie et validation du mode de paiement.
                        modePaiement = saisirEtValiderModePaiement();

                        if (modePaiement == 'C' || modePaiement == 'c') {
                            // Saisie et validation du type de carte de crédit.
                            typeDeCarteCredit = saisirEtValiderTypeCarteCredit();
                            // Saisie et validation du numéro de carte de crédit.
                            numDeCarteCredit = saisirEtValiderNumCarteCredit();
                        }

                        // Saisie et validation de l'assurance.
                        assurance = saisirEtValiderReponse();

                        // Section Calculs.
                        prixLocationParJour = determinerPrixLocationParJour(typeVehi, grandeurVehi);
                        prixAssuranceParJour = determinerPrixAssuranceParJour(typeVehi, grandeurVehi);
                        rabais = determinerRabais(typeVehi, grandeurVehi, prixLocationParJour, nbDeJourDeLocation);
                        montantLocation = calculerMontantLocation(nbDeJourDeLocation, prixLocationParJour, rabais);
                        montantAssurance = (assurance == 'O' || assurance == 'o') ? calculerMontantAssurance(nbDeJourDeLocation, prixAssuranceParJour) : 0;
                        sousTotal = calculerSousTotalFacture(montantLocation, montantAssurance);
                        montantTps = calculerMontantTPS(sousTotal);
                        montantTvq = calculerMontantTVQ(sousTotal);
                        totalFacture = calculerMontantTotalFacture(sousTotal, montantTps, montantTvq);

                        // Incrémentation du numéro de facture.
                        numFacture = incrementerNumeroFacture(numFacture);

                        // Ajustement de l'inventaire après location.
                        if (typeVehi == 'H' || typeVehi == 'h') {
                            vehHybPetitLouee = incrementerNombreVehiculesLoues(typeVehi, 'H', grandeurVehi, 'P', vehHybPetitLouee);
                            vehHybInterLouee = incrementerNombreVehiculesLoues(typeVehi, 'H', grandeurVehi, 'I', vehHybInterLouee);
                            vehHybGrandLouee = incrementerNombreVehiculesLoues(typeVehi, 'H', grandeurVehi, 'G', vehHybGrandLouee);
                        } else if (typeVehi == 'E' || typeVehi == 'e') {
                            vehElecPetitLouee = incrementerNombreVehiculesLoues(typeVehi, 'E', grandeurVehi, 'P', vehElecPetitLouee);
                            vehElecInterLouee = incrementerNombreVehiculesLoues(typeVehi, 'E', grandeurVehi, 'I', vehElecInterLouee);
                            vehElecGrandLouee = incrementerNombreVehiculesLoues(typeVehi, 'E', grandeurVehi, 'G', vehElecGrandLouee);
                        }

                        // Affichage de la facture.
                        afficherFacture(numFacture, prenomLocataire, nomLocataire, numTelLocataire, numPermLocataire, typeVehi, grandeurVehi, 
                                        modePaiement, typeDeCarteCredit, numDeCarteCredit, nbDeJourDeLocation, prixLocationParJour, prixAssuranceParJour, 
                                        rabais, montantLocation, montantAssurance, sousTotal, montantTps, montantTvq, totalFacture, dateCourante);
                    } else {
                        System.out.println("Malheureusement, ce modèle n'est pas disponible !");
                    }
                    System.out.println();
                    System.out.println("Appuyez sur <ENTER> pour revenir au menu.");
                    Clavier.lireFinLigne();
                    break;
                case 3:
                    // Afficher le Tableau avec le nombre de vehicules loues.
                    afficherEntetes(dateCouranteFormatee);
                    afficherVehiculesLoues(vehHybPetitLouee, vehHybInterLouee, vehHybGrandLouee, vehElecPetitLouee, vehElecInterLouee, vehElecGrandLouee);
                    System.out.println();
                    System.out.println("Appuyez sur <ENTER> pour revenir au menu.");
                    Clavier.lireFinLigne();
                    break;
                case 4:
                    // Quitter le programme.
                    System.out.println();
                    System.out.println("Merci et à la prochaine !\n");
                    System.out.println("Fermeture du programme ...");
                    break;
                default:
                    System.out.println();
                    System.out.println("Choix invalide, veuillez réessayer.\n");
            }
        } while (choix != 4);
    }
}