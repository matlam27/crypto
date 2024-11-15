import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

public class App {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\u001B[34m"
                + "  _____              __       ___           \n"
                + " / ___/_____ _____  / /____  / _ | ___  ___ \n"
                + "/ /__/ __/ // / _ \\/ __/ _ \\/ __ |/ _ \\/ _ \\\n"
                + "\\___/_/  \\_, / .__/\\__/\\___/_/ |_/ .__/ .__/\n"
                + "        /___/_/                 /_/  /_/    \n"
                + "                                                             \n"
                + "MENU PRINCIPAL\u001B[0m");
        System.out.println("=====================================");
        System.out.println("help  - Affiche ce menu d'aide");
        System.out.println("1     - Chiffrement");
        System.out.println("2     - Déchiffrement");
        System.out.println("3     - Hacher un mot de passe");
        System.out.println("4     - Générer un nombre aléatoire");
        System.out.println("q     - Quitte le programme");
        System.out.println("=====================================");
        System.out.print("Entrez une commande : ");

        String commande = scanner.nextLine().toLowerCase();

        switch (commande) {
            case "help":
                MenuAide(scanner);
                break;

            case "1": // Chiffrement
                MenuChiffrement(scanner);
                break;

            case "2": // Déchiffrement
                MenuDechiffrement(scanner);
                break;

            case "3": // Hacher un mot de passe
                MenuHash(scanner);

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
                System.out.println("=====================================");
                System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
                while (!scanner.nextLine().isEmpty()) {
                    System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
                }
                main(null);
                break;

            case "assaisonnement":
            System.out.println("Entrez le mot de passe à assaisonner : ");
            String motDePasse = scanner.nextLine();
            while (motDePasse.isEmpty()) {
                System.out.println("Veuillez entrer un mot de passe.");
                motDePasse = scanner.nextLine();
            }
            SelPoivre hasher = new SelPoivre();
            String hash = hasher.hacher(motDePasse);
            System.out.println("\nRésultats :");
            System.out.println("Sel généré : " + hasher.getSel());
            System.out.println("Hash       : " + hash);
            System.out.println("=====================================");
            System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
            while (!scanner.nextLine().isEmpty()) {
                System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
            }
            main(null);
            break;


            case "q": // Quitter le programme
                System.exit(0); // Force l'arrêt complet du programme
                break;

            default: // Commande non reconnue
                System.out.println("Commande non reconnue. Tapez 'help' pour voir les commandes disponibles.");
                main(null);
        }
    }

    // Affiche le menu d'aide
    /**
     * @param scanner
     * @throws Exception
     */
    private static void MenuAide(Scanner scanner) throws Exception {
        System.out.println("\n\u001B[33m"
                + "   ___   _    __   \n"
                + "  / _ | (_)__/ /__ \n"
                + " / __ |/ / _  / -_)\n"
                + "/_/ |_/_/\\_,_/\\__/ \n"
                + "                                                         \n"
                + "MENU D'AIDE\u001B[0m");
        System.out.println("=====================================");
        System.out.println("Commandes disponibles :");
        System.out.println("help    - Affiche ce menu d'aide");
        System.out.println("r  - Retour au menu principal");
        System.out.println("q   - Quitte le programme");
        System.out.println("=====================================");
        System.out.print("Menu de Chiffrement : ");

        System.out.println("\n1    - Cette commande permet de chiffrer une chaîne de caractères à l'aide du chiffrement par rotation,  entrée par l'utilisateur, cela sécurise \n       n'importe quelle communication");
        System.out.println("     - Il vous suffit d'entrer une chaîne de caractères et un chiffre pour la rotation");

        System.out.println("\n2    - Cette commande permet de chiffrer une chaîne de caractères en utilisant le chiffre de Vigenère, cela permet une protection de messages");
        System.out.println("     - Il vous suffit d'entrer une chaîne de caractères et une clé");

        System.out.println("\n3    - Cette commande permet de chiffrer une chaîne de caractères en utilisant un Carré de Polybe, cela permet de communiquer de façon sécurisée");
        System.out.println("     - Il vous suffit d'entrer une chaîne de caractères et de choisir l'ordre des lettres du carré de Polybe");

        System.out.println("\n4    - Cette commande permet de chiffrer une chaîne de caractères en utilisant la méthode Enigma, cela permet de communiquer de manière protégée");
        System.out.println("     - Il vous suffit d'entrer une chaîne de caractères");

        System.out.println( "\n5    - Cette commande permet de chiffrer en utilisant l'algorithme RC4, cela permet de sécuriser les communications");
        System.out.println("     - Il vous suffit d'entrer une chaîne de caractères");

        System.out.println("=====================================");

        System.out.println("Menu de Déchiffrement : ");

        System.out.println("\n1    - Cette commande permet de déchiffrer une chaîne de caractères à l'aide du chiffrement par rotation, entrée par l'utilisateur");
        System.out.println("     - Il vous suffit d'entrer une chaîne de caractères, puis un nombre pour le déchiffrement");

        System.out.println("\n2    - Cette commande permet de déchiffrer une chaîne de caractères en utilisant le chiffre de Vigenère, cela permet une protection de messages");
        System.out.println("     - Il vous suffit d'entrer une chaîne de caractères et une clé");
        
        System.out.println("\n3    - Cette commande permet de déchiffrer une chaîne de caractères en utilisant un Carré de Polybe, cela permet de communiquer de façon sécurisée");
        System.out.println("     - Il vous suffit d'entrer une chaîne de caractères et de choisir l'ordre des lettres du carré de Polybe");

        System.out.println("\n4    - Cette commande permet de déchiffrer une chaîne de caractères en utilisant la méthode Enigma, cela permet de communiquer de manière protégée");
        System.out.println("     - Il vous suffit d'entrer une chaîne de caractères");

        System.out.println( "\n5    - Cette commande permet de déchiffrer en utilisant l'algorithme RC4, cela permet de sécuriser les communications");
        System.out.println("     - Il vous suffit d'entrer une chaîne de caractères");

        System.out.println("=====================================");

        System.out.println("Menu de Hachage : ");

        System.out.println("\n1    - Cette commande permet de hacher à l'aide de MD5, des messages, cela permet de vérifier l'intégrité des messages");
        System.out.println("     - Il vous suffit d'entrer une chaîne de caractères");

        System.out.println("\n2    - Cette commande permet de hacher à l'aide de SHA-256, des messages, cela permet de garantir l'intégrité des messages");
        System.out.println("     - Il vous suffit d'entrer une chaîne de caractères");

        System.out.println("=====================================");
        
        System.out.println("\n4    - Cette commande permet de générer un nombre pseudo-aléatoire, cela renforce l'aléa des communications");
        System.out.println("     - Il vous suffit d'entrer une chaîne de caractères");
        
        System.out.println("=====================================");

        String commande = scanner.nextLine().toLowerCase();
        if (commande.equals("r")) {
            main(null);
        } else if (commande.equals("q")) {
            System.exit(0); // Force l'arrêt complet du programme
            return;
        } else {
            System.out.println("Commande non reconnue.");
            MenuAide(scanner);
        }
    }

    // Affiche le menu de chiffrement
    /**
     * @param scanner
     * @throws Exception
     */
    private static void MenuChiffrement(Scanner scanner) throws Exception {
        System.out.println("\n\u001B[36m\n"
                + "  _______   _ ______                       __ \r\n"
                + //
                " / ___/ /  (_) _/ _/______ __ _  ___ ___  / /_\r\n"
                + //
                "/ /__/ _ \\/ / _/ _/ __/ -_)  ' \\/ -_) _ \\/ __/\r\n"
                + //
                "\\___/_//_/_/_//_//_/  \\__/_/_/_/\\__/_//_/\\__/ \n"
                + "                                                         \n"
                + "CHIFFREMENT\u001B[0m"); // Cyan
        System.out.println("=====================================");
        System.out.println("1   - Chiffrement avec rotation");
        System.out.println("2   - Chiffrement de Vigenère");
        System.out.println("3   - Chiffrement avec le carré de Polybe");
        System.out.println("4   - Chiffrement avec la méthode Enigma");
        System.out.println("5   - Chiffrement avec le protocole RC4");
        System.out.println("r   - Retour au menu principal");
        System.out.println("q   - Quitte le programme");
        System.out.println("=====================================");

        String commande = scanner.nextLine().toLowerCase();
        String message;

        switch (commande) {
            case "1": // Chiffrement avec rotation
                System.out.println("Entrez le message à chiffrer : ");
                message = scanner.nextLine();
                while (message.isEmpty()) {
                    System.out.println("Veuillez entrer un message à chiffrer.");
                    message = scanner.nextLine();
                }
                //vérifier le décalage
                int decalage = 0;
                boolean decalageValide = false;
                while (!decalageValide) {
                    System.out.println("Entrez le décalage pour le chiffrement (nombre entier positif) : ");
                    String decalageStr = scanner.nextLine();
                    //faire en sorte de prendre que les nombres entiers positifs
                    if (decalageStr.matches("\\d+")) {
                        decalage = Integer.parseInt(decalageStr);
                        //choisir un décalage entre 1 et 25
                        if (decalage > 0 && decalage <= 25) {
                            decalageValide = true;
                        } else {
                            System.out.println("Le décalage doit être compris entre 1 et 25.");
                        }
                    } else {
                        System.out.println("Veuillez entrer un nombre entier positif.");
                    }
                }
                String messageChiffreRotation = ChiffrementRot.chiffrer(message, decalage);
                System.out.println("Message chiffré : " + messageChiffreRotation);
                System.out.println("=====================================");
                System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
                while (!scanner.nextLine().isEmpty()) {
                    System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
                }
                main(null);
                break;

            case "2": // Chiffrement de Vigenère
                System.out.println("Entrez le message à chiffrer : ");
                message = scanner.nextLine();
                while (message.isEmpty()) {
                    System.out.println("Veuillez entrer un message à chiffrer.");
                    message = scanner.nextLine();
                }
                System.out.println("Entrez la clé de chiffrement : ");
                String cleVigenere = scanner.nextLine();
                while (cleVigenere.isEmpty()) {
                    System.out.println("Veuillez entrer une clé de chiffrement.");
                    cleVigenere = scanner.nextLine();
                }
                String messageChiffreVigenere = Vigenere.chiffrer(message, cleVigenere);
                System.out.println("Message chiffré : " + messageChiffreVigenere);
                System.out.println("=====================================");
                System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
                while (!scanner.nextLine().isEmpty()) {
                    System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
                }
                main(null);
                break;

            case "3": // Chiffrement avec le carré de Polybe
            final String messageChiffrePolybe = Polybe.main(null);
            System.out.println("Message chiffré : " + messageChiffrePolybe);
            System.out.println("=====================================");
            main(null);
            break;


            case "4": // chiffrement avec Enigma
                //on affiche ceci pour que l'utilisateur entre son message
                System.out.println("Entrez le message à chiffrer : ");
                message = scanner.nextLine();
                //si le message est vide on redemande une saisie puis on redirige sur le menu de chiffrement
                while (message.isEmpty()) {
                    System.out.println("Veuillez entre un message à chiffrer");
                    message = scanner.nextLine();
                }
                //vérification du caractère valide
                while (!message.matches("[A-Z ]*")) {
                    System.out.println("Veuillez n'entrer que des mots ou phrases écrit(e)s en majuscules.");
                    message = scanner.nextLine();
                }
                //on appelle la class Enigma et la fonction de chiffrement et on affiche le message chiffré
                String messageEnigma = Enigma.chiffrerEnigma(message);
                System.out.println("Message chiffré avec la méthode Enigma :" + messageEnigma);
                System.out.println("=====================================");
                System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
                while (!scanner.nextLine().isEmpty()) {
                    System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
                }
                main(null);
                break;

            case "5": // Chiffrement avec protocole RC4

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
                            while (message.isEmpty()) {
                                System.out.println("Veuillez entrer un message à chiffrer.");
                                message = scanner.nextLine();
                            }
                            // vérifier si le message fait au moins 2 caractères
                            while (message.length() < 2) {
                                System.out.println("Le message doit contenir au moins 2 caractères.");
                                message = scanner.nextLine();
                            }

                            // Chiffrer le message avec la clé personnalisée
                            RC4 rc4 = new RC4(cleCustom);
                            byte[] messageChiffre = rc4.chiffrerDechiffrer(message.getBytes());

                            // Encodage du message chiffré en Base64
                            String messageEncode = Base64.getEncoder().encodeToString(messageChiffre);
                            System.out.println("Message chiffré encodé en Base64 : " + messageEncode);
                            System.out.println("=====================================");
                            System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
                            while (!scanner.nextLine().isEmpty()) {
                                System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
                            }
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
                            } // Utiliser la clé par défaut si le fichier n'existe pas ou est vide
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
                            while (message.isEmpty()) {
                                System.out.println("Veuillez entrer un message à chiffrer.");
                                message = scanner.nextLine();
                            }
                            while (message.length() < 2) {
                                System.out.println("Le message doit contenir au moins 2 caractères.");
                                message = scanner.nextLine();
                            }

                            // Chiffrer le message avec la clé par défaut
                            RC4 rc4CleCustom = new RC4(cle);
                            byte[] messageChiffreCleCustom = rc4CleCustom.chiffrerDechiffrer(message.getBytes());

                            // Encodage du message chiffré en Base64
                            String messageEncodeCleCustom = Base64.getEncoder().encodeToString(messageChiffreCleCustom);
                            System.out.println("Message chiffré encodé en Base64 : " + messageEncodeCleCustom);
                            System.out.println("=====================================");
                            System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
                            while (!scanner.nextLine().isEmpty()) {
                                System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
                            }
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
                System.exit(0); // Force l'arrêt complet du programme
                return;

            default:
                System.out.println("Commande non reconnue.");
                MenuChiffrement(scanner);
                break;
        }
    }

    // Affiche le menu de déchiffrement
    /**
     * @param scanner
     * @throws Exception
     */
    private static void MenuDechiffrement(Scanner scanner) throws Exception {
        System.out.println("\n\u001B[35m\n"
                + "   ___   __     __   _ ______                       __ \n"
                + "  / _ \\_/_/____/ /  (_) _/ _/______ __ _  ___ ___  / /_\n"
                + " / // / -_) __/ _ \\/ / _/ _/ __/ -_)  ' \\/ -_) _ \\/ __/\n"
                + "/____/\\__/\\__/_//_/_/_//_//_/  \\__/_/_/_/\\__/_//_/\\__/ \n"
                + "                                                         \n"
                + "DECHIFFREMENT\u001B[0m"); // Magenta
        System.out.println("=====================================");
        System.out.println("1   - Déchiffrement avec rotation");
        System.out.println("2   - Déchiffrement de Vigenère");
        System.out.println("3   - Déchiffrement avec la méthode Enigma");
        System.out.println("4   - Déchiffrement avec le protocole RC4");
        System.out.println("r   - Retour au menu principal");
        System.out.println("q   - Quitte le programme");
        System.out.println("=====================================");

        String commande = scanner.nextLine().toLowerCase();
        String message;

        switch (commande) {
            case "1": // Déchiffrement avec rotation
                System.out.println("Entrez le message à déchiffrer : ");
                message = scanner.nextLine();
                while (message.isEmpty()) {
                    System.out.println("Veuillez entrer un message à déchiffrer.");
                    message = scanner.nextLine();
                }
                //vérifier le décalage
                int decalage = 0;
                boolean decalageValide = false;
                while (!decalageValide) {
                    System.out.println("Entrez le décalage pour le chiffrement (nombre entier positif) : ");
                    String decalageStr = scanner.nextLine();
                    //faire en sorte de prendre que les nombres entiers positifs
                    if (decalageStr.matches("\\d+")) {
                        decalage = Integer.parseInt(decalageStr);
                        //choisir un décalage entre 1 et 25
                        if (decalage > 0 && decalage <= 25) {
                            decalageValide = true;
                        } else {
                            System.out.println("Le décalage doit être compris entre 1 et 25.");
                        }
                    } else {
                        System.out.println("Veuillez entrer un nombre entier positif.");
                    }
                }
                String messageDechiffreRotation = ChiffrementRot.dechiffrer(message, decalage);
                System.out.println("Message déchiffré : " + messageDechiffreRotation);
                System.out.println("=====================================");
                System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
                while (!scanner.nextLine().isEmpty()) {
                    System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
                }
                main(null);
                break;

            case "2": // Déchiffrement de Vigenère
                System.out.println("Entrez le message à déchiffrer : ");
                message = scanner.nextLine();
                while (message.isEmpty()) {
                    System.out.println("Veuillez entrer un message à déchiffrer.");
                    message = scanner.nextLine();
                }
                System.out.println("Entrez la clé de déchiffrement : ");
                String cleVigenere = scanner.nextLine();
                while (cleVigenere.isEmpty()) {
                    System.out.println("Veuillez entrer une clé de déchiffrement.");
                    cleVigenere = scanner.nextLine();
                }
                String messageDechiffreVigenere = Vigenere.dechiffrer(message, cleVigenere);
                System.out.println("Message déchiffré : " + messageDechiffreVigenere);
                System.out.println("=====================================");
                System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
                while (!scanner.nextLine().isEmpty()) {
                    System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
                }
                main(null);
                break;

            case "3": // déchiffrement avec Enigma
                //messsage affiché
                System.out.println("Entrez le message à déchiffrer : ");
                message = scanner.nextLine();
                //si le message n'est pas vide on appelle la classe Enigma et la fonction chiffrerEnigma
                while (message.isEmpty()) {
                    System.out.println("Veuillez entre un message à chiffrer");
                    message = scanner.nextLine();
                }
                //vérification du caractère valide
                while (!message.matches("[A-Z ]*")) {
                    System.out.println("Veuillez n'entrer que des mots ou phrases écrit(e)s en majuscules.");
                    message = scanner.nextLine();
                }
                String messageDechiffre = Enigma.chiffrerEnigma(message);
                //on affiche le message déchiffré 
                System.out.println("Message déchiffré avec la méthode Enigma : " + messageDechiffre);
                System.out.println("=====================================");
                System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
                while (!scanner.nextLine().isEmpty()) {
                    System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
                }
                main(null);
                break;

            case "4": // Déchiffrement avec protocole RC4

                // On demande à l'utilisateur s'il veut utiliser sa propre clé
                System.out.println("Voulez-vous utiliser votre propre clé ? (o/n)");
                String reponse = scanner.nextLine().toLowerCase();

                while (true) {
                    switch (reponse) {
                        case "o" -> {
                            // Utiliser une clé personnalisée
                            
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

                            while (message.isEmpty()) {
                                System.out.println("Veuillez entrer un message à déchiffrer.");
                                message = scanner.nextLine();
                            }
                            while (message.length() < 2) {
                                System.out.println("Le message doit contenir au moins 2 caractères.");
                                message = scanner.nextLine();
                            }
                            // Pour déchiffrer en décodant d'abord en Base64
                            byte[] messageChiffreCleCustomBytes = Base64.getDecoder().decode(message);

                            // Déchiffrer le message avec la clé personnalisée
                            RC4 rc4DechiffrerCleCustom = new RC4(cleCustom);
                            byte[] messageDechiffreCleCustom = rc4DechiffrerCleCustom
                                    .chiffrerDechiffrer(messageChiffreCleCustomBytes);
                            System.out.println("Message déchiffré : " + new String(messageDechiffreCleCustom));
                            System.out.println("=====================================");
                            System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
                            while (!scanner.nextLine().isEmpty()) {
                                System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
                            }
                            main(null);
                }

                        case "n" -> {
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

                            while (message.isEmpty()) {
                                System.out.println("Veuillez entrer un message à déchiffrer.");
                                message = scanner.nextLine();
                            }
                            while (message.length() < 2) {
                                System.out.println("Le message doit contenir au moins 2 caractères.");
                                message = scanner.nextLine();
                            }
                            // Pour déchiffrer en décodant d'abord en Base64
                            byte[] messageChiffreBytes = Base64.getDecoder().decode(message);

                            // Déchiffrer le message avec la clé
                            RC4 rc4Dechiffrer = new RC4(cle);
                            byte[] messageDechiffreRC4 = rc4Dechiffrer.chiffrerDechiffrer(messageChiffreBytes);
                            System.out.println("Message déchiffré : " + new String(messageDechiffreRC4));
                            System.out.println("=====================================");
                            System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
                            while (!scanner.nextLine().isEmpty()) {
                                System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
                            }
                            main(null);
                }

                        default -> {
                            System.out.println("Commande non reconnue. Voulez-vous utiliser votre propre clé ? (o/n)");
                            reponse = scanner.nextLine().toLowerCase();
                }
                    }
                }
            case "r":
                main(null);
                break;

            case "q":
                System.exit(0); // Force l'arrêt complet du programme
                return;

            default:
                System.out.println("Commande non reconnue.");
                MenuDechiffrement(scanner);
                break;
        }
    }

    // Affiche le menu de hachage
    /**
     * @param scanner
     * @throws Exception
     */
    private static void MenuHash(Scanner scanner) throws Exception {
        System.out.println("\n\u001B[32m\n"
                + "   __ __         __                \n"
                + "  / // /__ _____/ /  ___ ____ ____ \n"
                + " / _  / _ `/ __/ _ \\/ _ `/ _ `/ -_)\n"
                + "/_//_/\\_,_/\\__/_//_/\\_,_/\\_, /\\__/ \n"
                + "                        /___/      \n"
                + "HACHAGE\u001B[0m"); // Vert
        System.out.println("=====================================");
        System.out.println("1   - Hacher un mot de passe avec MD5");
        System.out.println("2   - Hacher un mot de passe avec SHA-256");
        System.out.println("r   - Retour au menu principal");
        System.out.println("q   - Quitte le programme");
        System.out.println("=====================================");

        String commande = scanner.nextLine().toLowerCase();
        String motDePasse;

        switch (commande) {
            case "1": // Hacher un mot de passe avec MD5
                System.out.println("Entrez le mot de passe à hacher :");
                motDePasse = scanner.nextLine();
                motDePasse = MD5.hash(motDePasse);
                System.out.println("Mot de passe haché avec MD5 : " + motDePasse);
                System.out.println("=====================================");
                System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
                while (!scanner.nextLine().isEmpty()) {
                    System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
                }
                main(null);

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
                System.out.println("=====================================");
                System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
                while (!scanner.nextLine().isEmpty()) {
                    System.out.println("Appuyez sur 'ENTER' pour revenir au menu principal.");
                }
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
                MenuHash(scanner);

                break;
        }
    }

    // Génère un nombre pseudo-aléatoire
    /**
     * @param graine
     * @return
     */
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

        return Integer.parseInt(chiffresMilieu);
    }
}
