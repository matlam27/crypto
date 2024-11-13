import java.util.Scanner;

/**
 * L'utilisateur peut chiffrer et dechiffrer en utilisant le chiffre
 * de Vigenere
 **/

public class Vigenere {
    public static void main(String[] args) throws Exception {
            // Objet Scanner pour lire les entrees utilisateur
            Scanner scanner = new Scanner(System.in);

            // Texte a chiffrer
            System.out.println("Entrez le texte à chiffrer : ");
            String texteAChiffrer = scanner.nextLine();

            // Demande et lecture de la cle
            System.out.println("Entrez la clé de chiffrement : ");
            String cle = scanner.nextLine();

            // Chiffrement et dechiffrement
            String texteADechiffrer = chiffrer(texteAChiffrer, cle);
            String texteDechiffre = dechiffrer(texteADechiffrer, cle);

            // Affichage des resultats
            System.out.println("\nRésultats :");
            System.out.println("Texte à chiffrer : " + texteAChiffrer);
            System.out.println("Texte chiffré : " + texteADechiffrer);
            System.out.println("Texte déchiffré : " + texteDechiffre);

            // Ferme le scanner
            scanner.close();
        }

        public static String chiffrer(String texteAChiffrer, String cle) {
        // Utilise StringBuilder pour manipuler plus facilement la chaine de caracteres
        StringBuilder texteADechiffrer = new StringBuilder();
        // Permet de recuperer la longueur du texte a chiffrer
        int size = texteAChiffrer.length();

        System.out.println("On chiffre le texte");

        // Boucle for : Parcourt chaque caractere du texte a chiffrer
        for (int i = 0; i < size; i++) {
            // Recupere le caractere a chiffrer
            char lettreAChiffrer = texteAChiffrer.charAt(i);
            // Permet de recuperer le caractere de la cle (repetee si necessaire grace au modulo)
            char lettreCle = cle.charAt(i % cle.length());
            // (lettre_a_chiffrer + lettre_cle) modulo 26
            // "a" = 97 en ASCII, donc on soustrait 96, puis on rajoute 96 pour revenir aux caracteres
            char lettreChiffree = (char) (((lettreAChiffrer - 96) + (lettreCle - 96)) % 26 + 96);
            // Ajoute la lettre chiffree au resultat
            texteADechiffrer.append(lettreChiffree);
        }

        // Retourne le texte chiffre
        return texteADechiffrer.toString();
    }

    public static String dechiffrer(String texteADechiffrer, String cle) {
        // Utilise StringBuilder pour manipuler plus facilement la chaine de caracteres
        StringBuilder texteDechiffre = new StringBuilder();
        // Permet de recuperer la longueur du texte à dechiffrer
        int size = texteADechiffrer.length();

        System.out.println("On déchiffre le texte");

        // Boucle for : Parcourt chaque caractere du texte à dechiffrer
        for (int i = 0; i < size; i++) {
            // Utilise StringBuilder pour manipuler plus facilement la chaine de caractères
            char lettreChiffree = texteADechiffrer.charAt(i);
            // Permet de recuperer le caractere de la cle (ca se repete si necessaire grace au modulo)
            char lettreCle = cle.charAt(i % cle.length());
            // (lettre_chiffree - lettre_cle + 26) modulo 26
            // On ajoute 26 pour eviter les nombres negatifs
            // "a" = 97 en ASCII, donc on soustrait 96 , puis on rajoute 96 pour revenir aux caracteres
            char lettreDechiffree = (char) (((lettreChiffree - 96) - (lettreCle - 96) + 26) % 26 + 96);
            // On ajoute la lettre déchiffree au resultat
            texteDechiffre.append(lettreDechiffree);
        }

        // Retourne le texte dechiffre
        return texteDechiffre.toString();
    }
}