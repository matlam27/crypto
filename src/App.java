import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrez une commande : ");
        System.out.println("\nhelp  - Affiche ce menu d'aide");
        System.out.println("1   - Chiffrement");
        System.out.println("2   - Déchiffrement");
        System.out.println("3   - Générer un nombre aléatoire");
        System.out.println("q   - Quitte le programme");

        String commande = scanner.nextLine().toLowerCase();
        
        switch (commande) {
            case "help":
                MenuAide();
                break;

            case "1":
                MenuChiffrement();
                break;

            case "2":
                MenuDechiffrement();
                break;

            case "3":
                try {
                    File fichierGraine = new File("graine.txt");
                    int graine;

                    if (fichierGraine.exists() && fichierGraine.length() > 0) {
                        Scanner fileScanner = new Scanner(fichierGraine);
                        graine = Integer.parseInt(fileScanner.nextLine());
                        fileScanner.close();
                    } else {
                        graine = 1904860153; // graine par défaut
                        try (FileWriter writer = new FileWriter(fichierGraine)) {
                            writer.write(String.valueOf(graine));
                        }
                    }

                    int randomNumber = getRandomNumber(graine);
                    System.out.println("Le nombre aléatoire généré est : " + randomNumber);
                    try (FileWriter writer = new FileWriter(fichierGraine)) {
                        writer.write(String.valueOf(randomNumber));
                    }
                } catch (IOException | NumberFormatException e) {
                    System.out.println("Une erreur s'est produite : " + e.getMessage());
                }
                main(null);
                break;

            case "q":
                scanner.close();
                return;

            default:
                System.out.println("Commande non reconnue. Tapez 'help' pour voir les commandes disponibles.");
                main(null);
        }
    }

    private static void MenuAide() {
        System.out.println("\nAide du programme :");
        System.out.println("Commandes disponibles :");
        System.out.println("help    - Affiche ce menu d'aide");
        System.out.println("q   - Quitte le programme");
        System.out.println("1   - Chiffrement");
        System.out.println("2   - Déchiffrement");
        System.out.println("3   - Générer un nombre aléatoire");
    }

    private static void MenuChiffrement() {
        System.out.println("\nChiffrement :");
        System.out.println("1   - Chiffrement de César");
        System.out.println("2   - Chiffrement de Vigenère");
        System.out.println("r   - Retour au menu principal");
        System.out.println("q   - Quitte le programme");

        Scanner scanner = new Scanner(System.in);
        String commande = scanner.nextLine().toLowerCase();

        if ("1".equals(commande) || "2".equals(commande)) {
            System.out.println("Entrez le message à chiffrer : ");
            String message = scanner.nextLine();
            if (message.isEmpty()) {
                System.out.println("Veuillez entrer un message à chiffrer.");
            }
        } else if ("r".equals(commande)) {
            main(null);
        } else {
            System.out.println("Commande non reconnue.");
        }
    }

    private static void MenuDechiffrement() {
        System.out.println("\nDéchiffrement :");
        System.out.println("1   - Déchiffrement de César");
        System.out.println("2   - Déchiffrement de Vigenère");
        System.out.println("r   - Retour au menu principal");
        System.out.println("q   - Quitte le programme");

        Scanner scanner = new Scanner(System.in);
        String commande = scanner.nextLine().toLowerCase();

        if ("1".equals(commande) || "2".equals(commande)) {
            System.out.println("Entrez le message à déchiffrer : ");
            String message = scanner.nextLine();
            if (message.isEmpty()) {
                System.out.println("Veuillez entrer un message à déchiffrer.");
            }
        } else if ("r".equals(commande)) {
            main(null);
        } else {
            System.out.println("Commande non reconnue.");
        }
    }

    private static int getRandomNumber(int graine) {
        // Calculer le carré du graine
        int graineAuCarre = graine * graine;


        // on élève le résultat au carré une nouvelle fois
        graineAuCarre *= graineAuCarre;


        // Convertir le carré en chaîne de caractères
        String stringAuCarre = String.valueOf(graineAuCarre);


        // Extraire les chiffres du milieu
        int longueur = stringAuCarre.length();
        int debut = longueur / 4;
        int fin = debut + longueur / 2;
        if (fin > longueur) {
            fin = longueur;
        }
        String chiffresMilieu = stringAuCarre.substring(debut, fin);;

        return Integer.parseInt(chiffresMilieu);
    }
}
