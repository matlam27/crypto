import java.util.*;

/**
 * L'utilisateur peut chiffrer et dechiffrer en utilisant le carré de Polybe
 **/
public class Polybe {
    // Instructions
    private static final String INSTRUCTIONS = """
        Guide du Carré de Polybe:
        - Une grille aleatoire de 5x5 contient l'alphabet
        - Chaque lettre est encodée par sa position (ligne,colonne) ou (colonne,ligne)
        - Exemple: avec ligne-colonne, 'A' en position (1,1) devient '11'
        - Les espaces sont préservés, les autres caractères spéciaux sont ignorés
        
        Etapes:
        1. Choisissez le mode: ligne-colonne (1) ou colonne-ligne (2)
        2. Entrez votre texte (max 1000 caractères)
        3. Le programme affiche le texte chiffré et déchiffré
        """;

    // Constantes tailles et longueur max
    private static final int TAILLE_GRILLE = 5;
    private static final int LONGUEUR_MAX = 1000;

    // Alphabet utilise pour la grille (sans W qui est gere separement)
    private static final List<Character> ALPHABET = Arrays.asList(
            'A','B','C','D','E',
            'F','G','H','I','J',
            'K','L','M','N','O',
            'P','Q','R','S','T',
            'U','V','X','Y','Z'
    );

    // Structure de donnees pour la grille
    private final char[][] grillePolybe = new char[TAILLE_GRILLE][TAILLE_GRILLE];
    private final boolean estModeLigneColonne;

    /**
     * Constructeur initialisant le mode de chiffrement et la grille
     * @param estModeLigneColonne true pour le mode ligne-colonne, false pour colonne-ligne
     **/
    public Polybe(boolean estModeLigneColonne) {
        this.estModeLigneColonne = estModeLigneColonne;
        genererGrilleAleatoire();
    }

    /**
     * Génère une grille aléatoire à partir de l'alphabet
     * Utilise Collections.shuffle pour mélanger les lettres
     **/
    private void genererGrilleAleatoire() {
        List<Character> lettresMelangees = new ArrayList<>(ALPHABET);
        Collections.shuffle(lettresMelangees);
        int index = 0;
        for (int i = 0; i < TAILLE_GRILLE; i++) {
            for (int j = 0; j < TAILLE_GRILLE; j++) {
                grillePolybe[i][j] = lettresMelangees.get(index++);
            }
        }
    }

    /**
     * Chiffre un texte en utilisant la methode de Polybe
     * @param texte Texte à chiffrer
     * @return texteChiffre avec les coordonnees groupees par mots
     * @throws IllegalArgumentException si le texte est invalide
     **/
    public String chiffrer(String texte) {
        // Verifie si le texte respecte les contraintes (longueur, caracteres autorises)
        if (!validerTexte(texte)) {
            throw new IllegalArgumentException("Texte invalide");
        }
        // Utilise StringBuilder pour manipuler plus facilement la chaine de caracteres
        StringBuilder texteChiffre = new StringBuilder();
        String texteNormalise = normaliserTexte(texte);
        boolean estPremierMot = true;
        StringBuilder motCourant = new StringBuilder();

        // Boucle for : Parcourt chaque caractere du texte a chiffrer
        for (char caractere : texteNormalise.toCharArray()) {
            // Si on rencontre un espace, on termine le mot en cours
            if (caractere == ' ') {
                if (!motCourant.isEmpty()) {
                    // On ajoute deux espaces entre les mots (sauf pour le premier)
                    if (!estPremierMot) {
                        texteChiffre.append("  ");
                    }
                    texteChiffre.append(motCourant);
                    motCourant = new StringBuilder();
                }
                estPremierMot = false;
                continue;
            }

            // Chiffrement du caractere : trouve sa position dans la grille et la convertit
            Position position = trouverPosition(caractere);
            motCourant.append(convertirCoordonnees(position));
        }

        // Ajout du dernier mot s'il existe
        if (!motCourant.isEmpty()) {
            if (!estPremierMot) {
                texteChiffre.append("  ");
            }
            texteChiffre.append(motCourant);
        }
        // Conversion finale en String et renvoi du résultat
        return texteChiffre.toString();
    }

    /**
     * Dechiffre un texte chiffre avec la methode de Polybe
     * @param texteChiffre Texte à dechiffrer
     * @return Texte dechiffre en minuscules
     **/
    // Utilise StringBuilder pour manipuler plus facilement la chaine de caracteres
    public String dechiffrer(String texteChiffre) {
        if (texteChiffre == null) {
            throw new IllegalArgumentException("Le texte chiffré ne peut pas être null");
        }
        if (!texteChiffre.matches("^[1-5]{2}(\\s*[1-5]{2})*$")) {
            throw new IllegalArgumentException("Format du texte chiffré invalide");
        }
        StringBuilder texteDechiffre = new StringBuilder();
        // Separation du texte en mots (separes par des doubles espaces)
        String[] mots = texteChiffre.split("  ");

        // Boucle for : Parcourt chaque caractere du texte a chiffrer
        for (int indexMot = 0; indexMot < mots.length; indexMot++) {
            if (mots[indexMot].isEmpty()) continue;

            // Parcours des caracteres du mot courant (par paires de chiffres)
            int indexCaractere = 0;
            while (indexCaractere < mots[indexMot].length()) {
                // Détection du motif VV (W)
                // Verifie si les 4 prochains chiffres forment deux coordonnees identiques
                if (indexCaractere + 3 < mots[indexMot].length() &&
                        mots[indexMot].substring(indexCaractere, indexCaractere + 2)
                                .equals(mots[indexMot].substring(indexCaractere + 2, indexCaractere + 4))) {
                    // Si on trouve un double V, on ajoute W
                    texteDechiffre.append('w');
                    // On avance de 4 positions (2 coordonnees)
                    indexCaractere += 4;
                } else {
                    Position position = convertirChiffreVersPosition(mots[indexMot].substring(indexCaractere, indexCaractere + 2));
                    texteDechiffre.append(Character.toLowerCase(grillePolybe[position.ligne][position.colonne]));
                    indexCaractere += 2;
                }
            }

            // Ajoute un espace entre les mots, sauf pour le dernier
            if (indexMot < mots.length - 1) {
                texteDechiffre.append(" ");
            }
        }

        // Renvoie le texte dechiffre final
        return texteDechiffre.toString();
    }

    /**
     * Normalise le texte pour le chiffrement
     * - Convertit en majuscules
     * - Remplace W par VV
     * - Ne garde que les lettres et espaces
     **/
    private String normaliserTexte(String texte) {
        // Utilise StringBuilder pour manipuler plus facilement la chaine de caracteres
        StringBuilder resultat = new StringBuilder();
        int longueurActuelle = 0;
        // Conversion en minuscules et parcours caractere par caractere
        for (char caractere : texte.toLowerCase().toCharArray()) {
            // Vérifier si l'ajout dépasserait la limite
            if (caractere == 'w' && longueurActuelle + 2 > LONGUEUR_MAX) {
                throw new IllegalArgumentException("Texte trop long après conversion W→VV");
            }
            if (longueurActuelle >= LONGUEUR_MAX) {
                throw new IllegalArgumentException("Texte trop long");
            }
            // Cas special : le 'w' est remplace par 'vv'
            if (caractere == 'w') {
                resultat.append("vv");
                // Ne garde que les lettres (a-z) et les espaces
            } else if (caractere == ' ' || (caractere >= 'a' && caractere <= 'z')) {
                resultat.append(caractere);
            }
        }
        // Conversion finale en majuscules avant de renvoyer le resultat
        return resultat.toString().toUpperCase();
    }

    /**
     * Valide le texte d'entrée
     * @return true si le texte est valide
     **/
    private boolean validerTexte(String texte) {
        if (texte == null) {
            throw new IllegalArgumentException("Le texte ne peut pas être null");
        }
        // Supprime tous les caracteres qui ne sont pas des lettres ou des espaces
        String texteNettoye = texte.replaceAll("[^A-Za-z ]", "");

        if (texteNettoye.trim().isEmpty()) {
            throw new IllegalArgumentException("Le texte doit contenir au moins une lettre");
        }
        if (texteNettoye.length() > LONGUEUR_MAX) {
            throw new IllegalArgumentException("Le texte est trop long (maximum " + LONGUEUR_MAX + " caractères)");
        }
        return true;
    }

    /**
     * Trouve la position d'un caractère dans la grille
     * @return Position (ligne, colonne) du caractère
     **/
    private Position trouverPosition(char caractere) {
        caractere = Character.toUpperCase(caractere);

        // Parcours de chaque cellule de la grille
        for (int i = 0; i < TAILLE_GRILLE; i++) {
            for (int j = 0; j < TAILLE_GRILLE; j++) {
                // 1. Le caractere correspond exactement
                // 2. Le caractere est V et on trouve W dans la grille
                // 3. Le caractere est W et on trouve V dans la grille
                if (grillePolybe[i][j] == caractere ||
                        (caractere == 'V' && grillePolybe[i][j] == 'W') ||
                        (caractere == 'W' && grillePolybe[i][j] == 'V')) {
                    return new Position(i, j);
                }
            }
        }
        // Si le caractere n'est pas trouve, lance une exception avec un message explicatif
        throw new IllegalArgumentException("Caractère non trouvé: " + caractere);
    }

    /**
     * Convertit une position en coordonnées chiffrées
     **/
    private String convertirCoordonnees(Position position) {
        // Retourne les coordonnées chiffrées en fonction du mode
        return estModeLigneColonne ?
                String.format("%d%d", position.ligne + 1, position.colonne + 1) :
                String.format("%d%d", position.colonne + 1, position.ligne + 1);
    }

    /**
     * Convertit des coordonnées chiffrées en position
     **/
    private Position convertirChiffreVersPosition(String coordonnees) {
        // Vérifier la longueur des coordonnées
        if (coordonnees.length() != 2) {
            throw new IllegalArgumentException("Format de coordonnées invalide");
        }

        // Vérifier que ce sont des chiffres valides (1-5)
        int premier = Character.getNumericValue(coordonnees.charAt(0));
        int second = Character.getNumericValue(coordonnees.charAt(1));

        if (premier < 1 || premier > 5 || second < 1 || second > 5) {
            throw new IllegalArgumentException("Coordonnées hors limites");
        }

        premier -= 1;
        second -= 1;

        return estModeLigneColonne ?
                new Position(premier, second) :
                new Position(second, premier);
    }

    /**
     * Classe record pour représenter une position dans la grille
     **/
    private record Position(int ligne, int colonne) {}

    /**
     * Affiche la grille de Polybe formatée
     **/
    public void afficherGrille() {
        // Affichage de la grille
        System.out.println("\nGrille de Polybe:");
        System.out.println("   1  2  3  4  5");
        // Parcours de chaque cellule de la grille
        for (int i = 0; i < TAILLE_GRILLE; i++) {
            System.out.printf("%d  ", i + 1);
            for (int j = 0; j < TAILLE_GRILLE; j++) {
                System.out.printf("%c  ", grillePolybe[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * Point d'entrée du programme
     **/
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(INSTRUCTIONS);

            // Choix du mode de chiffrement
            boolean modeLigneColonne;
            while (true) {
                System.out.print("Mode (1: ligne-colonne, 2: colonne-ligne): ");
                String choix = scanner.nextLine().trim();
                if (choix.equals("1")) {
                    modeLigneColonne = true;
                    break;
                } else if (choix.equals("2")) {
                    modeLigneColonne = false;
                    break;
                }
                System.out.println("Erreur: Veuillez entrer 1 ou 2");
            }

            // Création et affichage de la grille
            Polybe polybe = new Polybe(modeLigneColonne);
            polybe.afficherGrille();

            // Boucle de saisie du texte jusqu'à ce qu'il soit valide
            String texte = null;
            String chiffre = null;
            String dechiffre = null;

            while (chiffre == null) {
                try {
                    System.out.print("\nTexte à chiffrer: ");
                    texte = scanner.nextLine();
                    chiffre = polybe.chiffrer(texte);
                    dechiffre = polybe.dechiffrer(chiffre);
                } catch (IllegalArgumentException e) {
                    System.out.println("Erreur: " + e.getMessage());
                }
            }

            // Affichage des résultats
            System.out.println("\nRésultats:");
            System.out.println("Original : " + texte);
            System.out.println("Chiffré  : " + chiffre);
            System.out.println("Déchiffré: " + dechiffre);
        }
    }
}