import java.util.*;

/**
 * L'utilisateur peut chiffrer et dechiffrer en utilisant le carré de Polybe
 **/
public class Polybe {
    // Instructions
    private static final String INSTRUCTIONS = """
            Guide du Carré de Polybe:
                    - Une grille 5x5 contient l'alphabet (sans W qui est remplacé par VV)
                    - Chaque lettre est encodée par 2 chiffres représentant sa position
                    - Mode 1 (ligne-colonne): lettre en (2,3) devient '23'
                    - Mode 2 (colonne-ligne): lettre en (2,3) devient '32'
                    - Les espaces et la ponctuation sont conservés
                    - Les accents sont ignorés
                   \s
                    Utilisation:
                    1. Choisissez le mode de chiffrement:
                       - Tapez 1 pour le mode ligne-colonne
                       - Tapez 2 pour le mode colonne-ligne
                    2. Entrez votre texte (maximum 1000 lettres)
                    3. Le programme affichera:
                       - La grille de chiffrement
                       - Votre texte original
                       - Le texte chiffré
                       - Le texte déchiffré
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

    // Stockage du texte original avec caracteres speciaux pour le dechiffrement
    private String texteSauvegarde;

    /**
     * Constructeur initialisant le mode de chiffrement et la grille
     * @param estModeLigneColonne true pour le mode ligne-colonne, false pour colonne-ligne
     **/
    public Polybe(boolean estModeLigneColonne) {
        // Initialisation de la grille
        this.estModeLigneColonne = estModeLigneColonne;
        genererGrilleAleatoire();
    }


    /**
     * Génère une grille aléatoire à partir de l'alphabet
     * Utilise Collections.shuffle pour mélanger les lettres
     **/
    private void genererGrilleAleatoire() {
        // Mélange aleatoire des lettres
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
        StringBuilder texteChiffre = new StringBuilder(texte.length() * 2);
        String texteNormalise = normaliserTexte(texte);

        // Boucle for : Parcourt chaque caractere du texte a chiffrer
        for (char caractere : texteNormalise.toCharArray()) {
            if (Character.isLetter(caractere)) {
                // Chiffrement du caractere : trouve sa position dans la grille et la convertit
                Position position = trouverPosition(caractere);
                texteChiffre.append(convertirCoordonnees(position));
            } else {
                // Ajout des caracteres speciaux tels quels
                texteChiffre.append(caractere);
            }
        }
        // Conversion finale en String et renvoi du resultat
        return texteChiffre.toString();
    }

    /**
     * Dechiffre un texte chiffre avec la methode de Polybe
     * @param texteChiffre Texte à dechiffrer
     * @return Texte dechiffre en minuscules
     **/
    public String dechiffrer(String texteChiffre) {
        if (texteChiffre == null || texteSauvegarde == null) {
            throw new IllegalArgumentException("Le texte chiffré ne peut pas être null");
        }

        // Utilise StringBuilder pour manipuler plus facilement la chaine de caracteres
        StringBuilder texteDechiffre = new StringBuilder(texteChiffre.length());
        int indexSauvegarde = 0;

        for (int i = 0; i < texteChiffre.length(); i++) {
            char caractere = texteChiffre.charAt(i);

            if (Character.isDigit(caractere)) {
                if (i + 1 >= texteChiffre.length() || !Character.isDigit(texteChiffre.charAt(i + 1))) {
                    throw new IllegalArgumentException("Format du texte chiffré invalide");
                }

                char caractereOriginal = Character.toLowerCase(texteSauvegarde.charAt(indexSauvegarde));

                // Verifier si c'est un W (represente par VV)
                if (caractereOriginal == 'w') {
                    texteDechiffre.append('w');
                    // Sauter les 4 chiffres (VV)
                    i += 3;
                } else {
                    // Pour tous les autres caracteres
                    texteDechiffre.append(caractereOriginal);
                    // Sauter les 2 chiffres
                    i++;
                }
                indexSauvegarde++;
            } else {
                texteDechiffre.append(caractere);
                indexSauvegarde++;
            }
        }

        return texteDechiffre.toString();
    }

    /**
     * Normalise le texte pour le chiffrement
     * - Convertit en majuscules
     * - Remplace W par VV
     * - Ne garde que les lettres et espaces
     **/
    private String normaliserTexte(String texte) {
        // Sauvegarde du texte original pour le dechiffrement
        this.texteSauvegarde = texte;
        // Utilise StringBuilder pour manipuler plus facilement la chaine de caracteres
        StringBuilder resultat = new StringBuilder(texte.length() * 2);
        int longueurActuelle = 0;

        char[] chars = texte.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (longueurActuelle >= LONGUEUR_MAX) {
                throw new IllegalArgumentException("Texte trop long");
            }

            char caractereMin = Character.toLowerCase(chars[i]);

            // Traitement special pour W uniquement
            if (caractereMin == 'w') {
                if (longueurActuelle + 2 > LONGUEUR_MAX) {
                    throw new IllegalArgumentException("Texte trop long après conversion W→VV");
                }
                resultat.append("VV");
                longueurActuelle += 2;
            } else if (Character.isLetter(chars[i])) {
                // Toutes les autres lettres sont simplement converties en majuscules
                resultat.append(Character.toUpperCase(chars[i]));
                longueurActuelle++;
            } else {
                // Caracteres speciaux gardes tels quels
                resultat.append(chars[i]);
            }
        }

        return resultat.toString();
    }

    /**
     * Valide le texte d'entrée
     * @return true si le texte est valide
     **/
    private boolean validerTexte(String texte) {
        // Verification null
        if (texte == null) {
            throw new IllegalArgumentException("Le texte ne peut pas être null");
        }
        // Verification de la longueur maximale potentielle (cas ou tous les caracteres sont des W)
        if (texte.length() * 2 > LONGUEUR_MAX) {
            throw new IllegalArgumentException("Texte potentiellement trop long après conversion des W en VV");
        }
        // Verification des caracteres autorisés
        if (!texte.matches("[A-Za-z\\s\\p{Punct}]+")) {
            throw new IllegalArgumentException("Le texte ne peut contenir que des lettres, des espaces et de la ponctuation");
        }
        // Verification du nombre minimum de lettres
        String texteNettoye = texte.replaceAll("[^A-Za-z]", "");
        if (texteNettoye.isEmpty()) {
            throw new IllegalArgumentException("Le texte doit contenir au moins une lettre");
        }
        // Verification de la longueur après nettoyage
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
        // Retourne les coordonnees chiffrees en fonction du mode
        return estModeLigneColonne ?
                String.format("%d%d", position.ligne + 1, position.colonne + 1) :
                String.format("%d%d", position.colonne + 1, position.ligne + 1);
    }

    /**
     * Convertit des coordonnées chiffrées en position
     **/
    private Position convertirChiffreVersPosition(String coordonnees) {
        // Verifier la longueur des coordonnees
        if (coordonnees.length() != 2) {
            throw new IllegalArgumentException("Format de coordonnées invalide");
        }

        // Verifier que ce sont des chiffres valides (1-5)
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
        // Affichage de la grille avec bordures
        System.out.println("\nGrille de Polybe :");
        System.out.println("┌───┬───┬───┬───┬───┬───┐");
        System.out.println("│   │ 1 │ 2 │ 3 │ 4 │ 5 │");
        System.out.println("├───┼───┼───┼───┼───┼───┤");

        // Parcours de chaque cellule de la grille
        for (int i = 0; i < TAILLE_GRILLE; i++) {
            System.out.printf("│ %d │", i + 1);
            for (int j = 0; j < TAILLE_GRILLE; j++) {
                System.out.printf(" %c │", grillePolybe[i][j]);
            }
            if (i < TAILLE_GRILLE - 1) {
                System.out.println("\n├───┼───┼───┼───┼───┼───┤");
            } else {
                System.out.println("\n└───┴───┴───┴───┴───┴───┘");
            }
        }
        System.out.println();
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

            // Boucle de saisie du texte jusqu'a ce qu'il soit valide
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

            // Affichage des resultats
            System.out.println("\nRésultats:");
            System.out.println("Original : " + texte);
            System.out.println("Chiffré  : " + chiffre);
            System.out.println("Déchiffré: " + dechiffre);
        }
    }
}