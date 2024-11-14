import java.util.Base64;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEntrez une commande : ");
        System.out.println("help  - Affiche ce menu d'aide");
        System.out.println("1   - Chiffrement");
        System.out.println("2   - Déchiffrement");
        System.out.println("3   - Hacher un mot de passe");
        System.out.println("4   - Générer un nombre aléatoire");
        System.out.println("q   - Quitte le programme");

        String commande = scanner.nextLine().toLowerCase();

        switch (commande) {
            case "help":
                MenuAide();
                break;

            case "1": // Chiffrement
                MenuChiffrement();
                break;

            case "2": // Déchiffrement
                MenuDechiffrement();
                break;

            case "3": // Hacher un mot de passe
                MenuHash();

            case "4": // Générer un nombre aléatoire
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

            case "q": // Quitter le programme
                scanner.close();
                return;

            default: // Commande non reconnue
                System.out.println("Commande non reconnue. Tapez 'help' pour voir les commandes disponibles.");
                main(null);
        }
    }

    // Affiche le menu d'aide
    private static void MenuAide() {
        System.out.println("\nAide du programme :");
        System.out.println("Commandes disponibles :");
        System.out.println("help    - Affiche ce menu d'aide");
        System.out.println("q   - Quitte le programme");
        System.out.println("1   - Chiffrement");
        System.out.println("2   - Déchiffrement");
        System.out.println("3   - Hacher un mot de passe");
        System.out.println("4   - Générer un nombre aléatoire");
        main(null);
    }

    // Affiche le menu de chiffrement
    private static void MenuChiffrement() {
        System.out.println("\nChiffrement :");
        System.out.println("1   - Chiffrement de César");
        System.out.println("2   - Chiffrement de Vigenère");
        System.out.println("3   - Chiffrement avec protocole RC4");
        System.out.println("r   - Retour au menu principal");
        System.out.println("q   - Quitte le programme");

        Scanner scanner = new Scanner(System.in);
        String commande = scanner.nextLine().toLowerCase();
        String message;

        switch (commande) {
            case "1": // Chiffrement de César
                System.out.println("Entrez le message à chiffrer : ");
                message = scanner.nextLine();
                if (message.isEmpty()) {
                    System.out.println("Veuillez entrer un message à chiffrer.");
                }
                // Ici, vous pouvez ajouter l'algorithme de chiffrement de César
                break;

            case "2": // Chiffrement de Vigenère
                System.out.println("Entrez le message à chiffrer : ");
                message = scanner.nextLine();
                if (message.isEmpty()) {
                    System.out.println("Veuillez entrer un message à chiffrer.");
                }
                // Ajouter ici le chiffrement de Vigenère
                break;

            case "3": // Chiffrement avec protocole RC4

                // On demande à l'utilisateur s'il veut utiliser sa propre clé
                System.out.println("Voulez-vous utiliser votre propre clé ? (o/n)");
                String reponse = scanner.nextLine().toLowerCase();

                while (true) {
                    switch (reponse) {
                        case "o": // Utiliser une clé personnalisée

                            System.out.println("Entrez votre clé : ");
                            String cleCustom = scanner.nextLine();

                            // Vérifier si la clé est vide
                            while (cleCustom.isEmpty()) {
                                System.out.println("Veuillez entrer une clé.");
                                cleCustom = scanner.nextLine();
                            }

                            // Sauvegarder la clé personnalisée dans un fichier
                            try (FileWriter writer = new FileWriter("cleRC4.txt")) {
                                writer.write(cleCustom);
                            } catch (IOException e) {
                                System.out.println("Une erreur s'est produite : " + e.getMessage());
                            }
                            System.out.println("Votre clé a été sauvegardée.");

                            // Demander à l'utilisateur d'entrer le message à chiffrer
                            System.out.println("Entrez le message à chiffrer : ");
                            message = scanner.nextLine();

                            // Vérifier si le message est vide
                            if (message.isEmpty()) {
                                System.out.println("Veuillez entrer un message à chiffrer.");
                            }

                            // Chiffrer le message avec la clé personnalisée
                            RC4 rc4 = new RC4(cleCustom);
                            byte[] messageChiffre = rc4.chiffrerDechiffrer(message.getBytes());

                            // Encodage du message chiffré en Base64
                            String messageEncode = Base64.getEncoder().encodeToString(messageChiffre);
                            System.out.println("Message chiffré encodé en Base64 : " + messageEncode);
                            main(null);
                            break;

                        case "n": // Utiliser la clé par défaut

                            // Vérifier si le fichier contenant la clé existe
                            File fichierCle = new File("cleRC4.txt");
                            String cle;
                            if (fichierCle.exists() && fichierCle.length() > 0) {
                                try (Scanner fileScanner = new Scanner(fichierCle)) {
                                    cle = fileScanner.nextLine();
                                } catch (FileNotFoundException e) {
                                    System.out.println("Une erreur s'est produite : " + e.getMessage());
                                    return;
                                }
                            }

                            // Utiliser la clé par défaut si le fichier n'existe pas ou est vide
                            else {
                                cle = "f240485ebe4d995194c220d353a2bdd883807ae1b3572670aea1df45256f0e55"; // clé par
                                                                                                          // défaut
                                try (FileWriter writer = new FileWriter(fichierCle)) {
                                    writer.write(cle);
                                } catch (IOException e) {
                                    System.out.println("Une erreur s'est produite : " + e.getMessage());
                                    return;
                                }
                            }

                            // Demander à l'utilisateur d'entrer le message à chiffrer
                            System.out.println("Entrez le message à chiffrer : ");
                            message = scanner.nextLine();
                            if (message.isEmpty()) {
                                System.out.println("Veuillez entrer un message à chiffrer.");
                            }

                            // Chiffrer le message avec la clé par défaut
                            RC4 rc4CleCustom = new RC4(cle);
                            byte[] messageChiffreCleCustom = rc4CleCustom.chiffrerDechiffrer(message.getBytes());

                            // Encodage du message chiffré en Base64
                            String messageEncodeCleCustom = Base64.getEncoder().encodeToString(messageChiffreCleCustom);
                            System.out.println("Message chiffré encodé en Base64 : " + messageEncodeCleCustom);
                            main(null);
                            break;

                        default:
                            System.out.println("Commande non reconnue. Voulez-vous utiliser votre propre clé ? (o/n)");
                            reponse = scanner.nextLine().toLowerCase();
                            break;
                    }
                }

            case "r":
                main(null);
                break;

            case "q":
                scanner.close();
                return;

            default:
                System.out.println("Commande non reconnue.");
                MenuChiffrement();
                break;
        }
    }

    // Affiche le menu de déchiffrement
    private static void MenuDechiffrement() {
        System.out.println("\nDéchiffrement :");
        System.out.println("1   - Déchiffrement de César");
        System.out.println("2   - Déchiffrement de Vigenère");
        System.out.println("3   - Déchiffrement avec protocole RC4");
        System.out.println("r   - Retour au menu principal");
        System.out.println("q   - Quitte le programme");

        Scanner scanner = new Scanner(System.in);
        String commande = scanner.nextLine().toLowerCase();
        String message;

        switch (commande) {
            case "1": // Déchiffrement de César
                System.out.println("Entrez le message à déchiffrer : ");
                message = scanner.nextLine();
                // Ajouter le déchiffrement de César ici
                break;

            case "2": // Déchiffrement de Vigenère
                System.out.println("Entrez le message à déchiffrer : ");
                message = scanner.nextLine();
                // Ajouter le déchiffrement de Vigenère ici
                break;

            case "3": // Déchiffrement avec protocole RC4

                // On demande à l'utilisateur s'il veut utiliser sa propre clé
                System.out.println("Voulez-vous utiliser votre propre clé ? (o/n)");
                String reponse = scanner.nextLine().toLowerCase();

                while (true) {
                    switch (reponse) {
                        case "o": // Utiliser une clé personnalisée

                            System.out.println("Entrez votre clé : ");
                            String cleCustom = scanner.nextLine();

                            // Vérifier si la clé est vide
                            while (cleCustom.isEmpty()) {
                                System.out.println("Veuillez entrer une clé.");
                                cleCustom = scanner.nextLine();
                            }

                            // Sauvegarder la clé personnalisée dans un fichier
                            try (FileWriter writer = new FileWriter("cleRC4.txt")) {
                                writer.write(cleCustom);
                            } catch (IOException e) {
                                System.out.println("Une erreur s'est produite : " + e.getMessage());
                            }
                            System.out.println("Votre clé a été sauvegardée.");

                            // Demander à l'utilisateur d'entrer le message à déchiffrer
                            System.out.println("Entrez le message à déchiffrer : ");
                            message = scanner.nextLine();

                            // Pour déchiffrer en décodant d'abord en Base64
                            byte[] messageChiffreCleCustomBytes = Base64.getDecoder().decode(message);

                            // Déchiffrer le message avec la clé personnalisée
                            RC4 rc4DechiffrerCleCustom = new RC4(cleCustom);
                            byte[] messageDechiffreCleCustom = rc4DechiffrerCleCustom
                                    .chiffrerDechiffrer(messageChiffreCleCustomBytes);
                            System.out.println("Message déchiffré : " + new String(messageDechiffreCleCustom));
                            main(null);
                            break;

                        case "n":
                            File fichierCle = new File("cleRC4.txt");
                            String cle;
                            if (fichierCle.exists() && fichierCle.length() > 0) {
                                try (Scanner fileScanner = new Scanner(fichierCle)) {
                                    cle = fileScanner.nextLine();
                                } catch (FileNotFoundException e) {
                                    System.out.println("Une erreur s'est produite : " + e.getMessage());
                                    return;
                                }
                            } else {
                                cle = "f240485ebe4d995194c220d353a2bdd883807ae1b3572670aea1df45256f0e55";
                                try (FileWriter writer = new FileWriter(fichierCle)) {
                                    writer.write(cle);
                                } catch (IOException e) {
                                    System.out.println("Une erreur s'est produite : " + e.getMessage());
                                    return;
                                }
                            }
                            System.out.println("Entrez le message à déchiffrer : ");
                            message = scanner.nextLine();

                            // Pour déchiffrer en décodant d'abord en Base64
                            byte[] messageChiffreBytes = Base64.getDecoder().decode(message);

                            // Déchiffrer le message avec la clé personnalisée
                            RC4 rc4Dechiffrer = new RC4(cle);
                            byte[] messageDechiffre = rc4Dechiffrer.chiffrerDechiffrer(messageChiffreBytes);
                            System.out.println("Message déchiffré : " + new String(messageDechiffre));
                            main(null);
                            break;

                        default:
                            System.out.println("Commande non reconnue. Voulez-vous utiliser votre propre clé ? (o/n)");
                            reponse = scanner.nextLine().toLowerCase();
                            break;
                    }
                }
            case "r":
                main(null);
                break;

            case "q":
                scanner.close();
                return;

            default:
                System.out.println("Commande non reconnue.");
                MenuDechiffrement();
                break;
        }
    }

    // Affiche le menu de hachage
    private static void MenuHash() {
        System.out.println("\nHachage :");
        System.out.println("1   - Hacher un mot de passe avec MD5");
        System.out.println("2   - Hacher un mot de passe avec SHA-256");
        System.out.println("r   - Retour au menu principal");
        System.out.println("q   - Quitte le programme");

        Scanner scanner = new Scanner(System.in);
        String commande = scanner.nextLine().toLowerCase();
        String motDePasse;

        switch (commande) {
            case "2": // Hacher un mot de passe avec SHA-256
            // On demande à l'utilisateur d'entrer le mot de passe à hacher    
            System.out.println("Entrez le mot de passe à hacher : ");
                motDePasse = scanner.nextLine();
                // Vérifier si le mot de passe est vide
                while (motDePasse.isEmpty()) {
                    System.out.println("Veuillez entrer un mot de passe à hacher.");
                    motDePasse = scanner.nextLine();
                }
                try {
                    // Créer une instance de MessageDigest avec l'algorithme SHA-256
                    MessageDigest msg = MessageDigest.getInstance("SHA-256");
                    // on récupère les bits du hash
                    byte[] hash = msg.digest(motDePasse.getBytes(StandardCharsets.UTF_8));
                    // convertir bytes en hexadécimal
                    StringBuilder s = new StringBuilder();
                    for (byte b : hash) {
                        s.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
                    }
                    motDePasse = s.toString();
                } catch (NoSuchAlgorithmException e) {
                    System.out.println("Une erreur s'est produite : " + e.getMessage());
                }
                System.out.println("Le mot de passe a été haché avec succès.");
                System.out.println("Voici le mot de passe haché : " + motDePasse);
                main(null);
                break;

            case "r":
                main(null);
                break;

            case "q":
                scanner.close();
                return;

            default:
                System.out.println("Commande non reconnue.");
                MenuHash();
                break;
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
        String chiffresMilieu = stringAuCarre.substring(debut, fin);
        ;

        return Integer.parseInt(chiffresMilieu);
    }
}
