import java.util.Scanner;

/**
 * L'utilisateur peut chiffrer et dechiffrer en utilisant le chiffre
 * de Vigenere
 **/

public class Vigenere {
    // Instructions
    private static final String INSTRUCTIONS = "\nInstructions pour le chiffrement de Vigenère :" +
            "\n1. Entrez votre message (lettres, espaces et apostrophes autorisés)" +
            "\n2. Entrez une clé de chiffrement (minimum 3 lettres, maximum 100 lettres, sans espaces)" +
            "\n3. Les espaces et apostrophes seront conservés tels quels" +
            "\n4. Seules les lettres seront chiffrées\n";

    // Constantes pour les gerer les messages d'erreur
    private static final String ERREUR_TEXTE_VIDE = "Le texte ne peut pas être vide.";
    private static final String ERREUR_CLE_VIDE = "La clé ne peut pas être vide.";
    private static final String ERREUR_CARACTERES_INVALIDES = "Seules les lettres sont autorisées.";

    private static final int LONGUEUR_MAX = 1000;
    private static final int TAILLE_MIN_CLE = 3;
    private static final int TAILLE_MAX_CLE = 100;

    public static void main(String[] args) throws Exception {
        // Objet Scanner pour lire les entrees utilisateur
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(INSTRUCTIONS);
            String messageAChiffrer = "";
            String cleDeChiffrement = "";
            do {
                // Texte a chiffrer
                System.out.println("Entrez le texte à chiffrer : ");
                messageAChiffrer = scanner.nextLine();
            } while (!validerTexte(messageAChiffrer));
            do {
                // Demande et lecture de la cle
                System.out.println("Entrez la clé de chiffrement : ");
                cleDeChiffrement = scanner.nextLine();
            } while (!validerCle(cleDeChiffrement));

            // Chiffrement et dechiffrement
            String messageChiffre = chiffrer(messageAChiffrer, cleDeChiffrement);
            String messageDechiffre = dechiffrer(messageChiffre, cleDeChiffrement);

            // Affichage des resultats
            afficherResultats(messageAChiffrer, messageChiffre, messageDechiffre);

        } catch (Exception e) {
            System.out.println("Une erreur s'est produite : " + e.getMessage());
        }
    }

    // Methode pour valider le texte
    private static boolean validerTexte(String texte) {
        // Verifie si le texte est trop long
        if (texte.length() > LONGUEUR_MAX) {
            System.out.println("Le texte est trop long. " + LONGUEUR_MAX + " caractères maximum.");
            return false;
        }
        // Verifie si le texte est vide
        if (texte.isEmpty()) {
            System.out.println(ERREUR_TEXTE_VIDE);
            return false;
        }
        // Verifie si le texte ne contient que des espaces
        if (texte.trim().isEmpty()) {
            System.out.println("Le texte ne peut pas contenir uniquement des espaces.");
            return false;
        }
        // Verifie si le texte ne contient que des lettres
        if (!texte.matches("[a-zA-Z' ]*")) {
            System.out.println("Seules les lettres, les apostrophes et les espaces sont autorisés");
            return false;
        }
        // Vérifier qu'il y a au moins une lettre à chiffrer
        if (!texte.matches(".*[a-zA-Z].*")) {
            System.out.println("Le texte doit contenir au moins une lettre à chiffrer.");
            return false;
        }
        return true;
    }

    // Methode pour valider la cle
    private static boolean validerCle(String cle) {
        // Verifie si la cle est vide
        if (cle.isEmpty()) {
            System.out.println(ERREUR_CLE_VIDE);
            return false;
        }
        // Verifie si la cle est trop courte
        if (cle.length() < TAILLE_MIN_CLE) {
            System.out.println("La clé est trop courte. Elle doit contenir au moins " + TAILLE_MIN_CLE + " caractères.");
            return false;
        }
        // Verifie si la cle est trop longue
        if (cle.length() > TAILLE_MAX_CLE) {
            System.out.println("La clé est trop longue. Elle doit contenir au maximum " + TAILLE_MAX_CLE + " caractères.");
            return false;
        }
        // Verifie si la cle ne contient que des lettres
        if (!cle.matches("[a-zA-Z]*")) {
            System.out.println(ERREUR_CARACTERES_INVALIDES);
            return false;
        }
        return true;
    }

    private static void afficherResultats(String messageAChiffrer, String messageChiffre, String messageDechiffre) {
        // Affichage des resultats
        System.out.println("\nRésultats :");
        System.out.println("Texte à chiffrer : " + messageAChiffrer);
        System.out.println("Texte chiffré : " + messageChiffre);
        System.out.println("Texte déchiffré : " + messageDechiffre);

        if (!messageDechiffre.equals(messageAChiffrer)) {
            System.out.println("\nLe texte déchiffré ne correspond pas au texte original.");
        }
    }

    public static String chiffrer(String messageAChiffrer, String cleDeChiffrement) {
        // Utilise StringBuilder pour manipuler plus facilement la chaine de caracteres
        StringBuilder messageChiffre = new StringBuilder();
        // Permet de recuperer la longueur du texte a chiffrer
        int longueurMessage = messageAChiffrer.length();
        // Permet de separer la cle
        int indexCle = 0;

        System.out.println("On chiffre le texte");

        // Boucle for : Parcourt chaque caractere du texte a chiffrer
        for (int i = 0; i < longueurMessage; i++) {
            // Recupere le caractere a chiffrer
            char caractereAChiffrer = messageAChiffrer.charAt(i);
            // Si ce n'est pas une lettre on le garde tel quel
            if (!Character.isLetter(caractereAChiffrer)) {
                messageChiffre.append(caractereAChiffrer);
                continue;
            }
            // Permet de recuperer le caractere de la cle (repetee si necessaire grace au modulo)
            char caractereCle = cleDeChiffrement.charAt(indexCle % cleDeChiffrement.length());
            indexCle++;
            boolean estMajuscule = Character.isUpperCase(caractereAChiffrer);
            // Convertir en minuscule pour le calcul
            caractereAChiffrer = Character.toLowerCase(caractereAChiffrer);
            caractereCle = Character.toLowerCase(caractereCle);
            // (lettre_a_chiffrer + lettre_cle) modulo 26
            // On utilise 'a' comme reference pour les caracteres ASCII
            char caractereChiffre = (char) (((caractereAChiffrer - 'a') + (caractereCle - 'a')) % 26 + 'a');
            // Restaurer la casse si necessaire
            if (estMajuscule) {
                caractereChiffre = Character.toUpperCase(caractereChiffre);
            }
            // Ajoute la lettre chiffree au resultat
            messageChiffre.append(caractereChiffre);
        }
        // Retourne le texte chiffre
        return messageChiffre.toString();
    }

    public static String dechiffrer(String messageChiffre, String cleDeChiffrement) {
        // Utilise StringBuilder pour manipuler plus facilement la chaine de caracteres
        StringBuilder messageDechiffre = new StringBuilder();
        // Permet de recuperer la longueur du texte à dechiffrer
        int longueurMessage = messageChiffre.length();
        // Permet de separer la cle
        int indexCle = 0;

        System.out.println("On déchiffre le texte");

        // Boucle for : Parcourt chaque caractere du texte à dechiffrer
        for (int i = 0; i < longueurMessage; i++) {
            // Recupere le caractere a dechiffrer
            char caractereChiffre = messageChiffre.charAt(i);
            // Si ce n'est pas une lettre, on le garde tel quel
            if (!Character.isLetter(caractereChiffre)) {
                messageDechiffre.append(caractereChiffre);
                continue;
            }
            // Permet de recuperer le caractere de la cle (ca se repete si necessaire grace au modulo)
            char caractereCle = cleDeChiffrement.charAt(indexCle % cleDeChiffrement.length());
            indexCle++;
            boolean estMajuscule = Character.isUpperCase(caractereChiffre);
            // Convertir en minuscule pour le calcul
            caractereChiffre = Character.toLowerCase(caractereChiffre);
            caractereCle = Character.toLowerCase(caractereCle);
            // (lettre_chiffree - lettre_cle + 26) modulo 26
            // On utilise 'a' comme reference pour les caracteres ASCII
            char caractereDechiffre = (char) (((caractereChiffre - 'a') - (caractereCle - 'a') + 26) % 26 + 'a');
            // Restaurer la casse si necessaire
            if (estMajuscule) {
                caractereDechiffre = Character.toUpperCase(caractereDechiffre);
            }
            // On ajoute la lettre déchiffree au resultat
            messageDechiffre.append(caractereDechiffre);
        }
        // Retourne le texte dechiffre
        return messageDechiffre.toString();
    }
}