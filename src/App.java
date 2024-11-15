import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        //initialisation du scanner pour pouvoir obtenir le message de l'utilisateur
        Scanner scanner = new Scanner(System.in);
        
       
            //affichage du guide pour l'utilisateur
            System.out.print("Entrez une commande : ");
            System.out.println("\nhelp  - Affiche ce menu d'aide");
            System.out.println("1   - Chiffrement");
            System.out.println("2   - Déchiffrement");
            System.out.println("3   - Générer un nombre aléatoire");
            System.out.println("q   - Quitte le programme");
            //utiliser le scanner pour obtenir la String que l'utilisateur entre en console
            String commande = scanner.nextLine().toLowerCase();
            
            switch (commande) {
                //si l'entrée est "help", on appelle la fonction du Menu d'aide
                case "help":
                    MenuAide();
                    break;
                // si l'entrée est "1", on appelle la fonction du Menu de chiffrement
                case "1":
                    MenuChiffrement();
                    break;
                    
                // si l'entrée est "2", on appelle la fonction du Menu de déchiffrement
                case "2":
                    MenuDechiffrement();
                    break;

                //si l'entrée est "q", on ferme le scanner et arrête l'app
                case "q":
                    scanner.close();
                    return;

                //si une commande n'existe pas, on envoie un message de non reconnaissance
                default:
                    System.out.println("Commande non reconnue. Tapez 'help' pour voir les commandes disponibles.");
                    main(null);
        }
    }
    
    //fonction du menu d'aide
    private static void MenuAide() {
        //affichage du texte d'aide
        System.out.println("\nMENU D'AIDE");
        System.out.println("\nCommandes disponibles :");
        System.out.println("\nhelp - Cette commande affiche ce menu d'aide");
        System.out.println("\nq    - Cette commande quitte le programme");
        System.out.println("\n1    - Cette commande permet de chiffrer une chaîne de caractères en utilisant un chiffrement entré par l'utilisateur, cela sécurise \n       n'importe quelle communication");
        System.out.println("\n     - Il vous suffit d'entrer une chaîne de caractères, puis un nombre pour le chiffrement");
        System.out.println("\n2    - Cette commande permet de déchiffrer une chaîne de caractères en utilisant un chiffrement entré par l'utilisateur");
        System.out.println("\n     - Il vous suffit d'entrer une chaîne de caractères, puis un nombre pour le chiffrement");
        System.out.println("\n3    - Cette commande permet de chiffrer et déchiffrer en utilisant le chiffre de Vigenère, cela permet une protection de messages");
        System.out.println("\n     - Il vous suffit d'entrer une chaîne de caractères et un un mot");
        System.out.println("\n4    - Cette commande permet de chiffrer et déchiffrer en utilisant un Carré de Polybe, cela permet de communiquer de façon sécurisée");
        System.out.println("\n     - Il vous suffit d'entrer une chaîne de caractères");
        System.out.println("\n5    - Cette commande permet de générer un nombre pseudo-aléatoire, cela renforce l'aléa des communications");
        System.out.println("\n     - Il vous suffit d'entrer une chaîne de caractères");
        System.out.println("\n6    - Cette commande permet de chiffrer et déchiffrer en utilisant la méthode Enigma, cela permet de communiquer de manière protégée");
        System.out.println("\n     - Il vous suffit d'entrer une chaîne de caractères");
        System.out.println("\n7    - Cette commande permet de chiffrer et déchiffrer en utilisant l'algorithme RC4, cela permet de sécuriser les communications");
        System.out.println("\n     - Il vous suffit d'entrer une chaîne de caractères");
        System.out.println("\n8    - Cette commande permet de hacher à l'aide de MD5, des messages, cela permet de vérifier l'intégrité des messages");
        System.out.println("\n     - Il vous suffit d'entrer une chaîne de caractères");
        System.out.println("\n9    - Cette commande permet de hacher à l'aide de SHA-256, des messages, cela permet de garantir l'intégrité des messages");
        System.out.println("\n     - Il vous suffit d'entrer une chaîne de caractères");
        System.out.println("\n10   - Cette commande permet à l'utilisateur de choisir l'algorithme de chiffrement ou de hachage qu'il préfère");
        System.out.println("\n     - Il vous suffit d'appuyer sur entrée sur un algorithme de la liste");
        System.out.println("\n11   - Cette commande permet à l'utilisateur d'utiliser plusieurs algorithmes successivement avec un même message");
        System.out.println("\n     - Il vous suffit d'entrer une chaîne de caractères et de choisir l'algorithme que vous voulez exécuter");
    }

    //fonction du menu de chiffrement
    private static void MenuChiffrement() {
        //affichage des options du menu
        System.out.println("\nChiffrement :");
        System.out.println("1   - Chiffrement de César");
        System.out.println("2   - Chiffrement de Vigenère");
        System.out.println("3   - Chiffrement avec le carré de Polybe");
        System.out.println("4   - Chiffrement avec la méthode Enigma");
        System.out.println("r   - Retour au menu principal");
        System.out.println("q   - Quitte le programme");

        //initialisation du scanner pour obtenir l'entrée de l'utilisateur
        Scanner scanner = new Scanner(System.in);
        String commande = scanner.nextLine().toLowerCase();
        String message = "";

        //switch pour les différentes options du menu
        switch (commande) {
            case "1":
                System.out.println("Entrez le message à chiffrer : ");
                message = scanner.nextLine();
                if (message.isEmpty()) {
                    System.out.println("Veuillez entrer un message à déchiffrer");
                    MenuChiffrement();
                }
                break;

            case "2":
                System.out.println("Entrez le message à chiffrer : ");
                message = scanner.nextLine();
                if (message.isEmpty()) {
                    System.out.println("Veuillez entrer un message à déchiffrer");
                    MenuChiffrement();
                }
                break;

            case "3":
                System.out.println("Entrez le message à chiffrer : ");
                message = scanner.nextLine();
                if (message.isEmpty()) {
                    System.out.println("Veuillez entrer un message à déchiffrer");
                    MenuChiffrement();
                }
                break;

            case "4":
                //on affiche ceci pour que l'utilisateur entre son message
                System.out.println("Entrez le message à chiffrer : ");
                message = scanner.nextLine();
                //si le message est vide on redemande une saisie puis on redirige sur le menu de chiffrement
                if(message.isEmpty()) {
                    System.out.println("Veuillez entre un message à chiffrer");
                    MenuChiffrement();
                }
                //vérification du caractère valide
                if (!message.matches("[A-Z ]*")) {
                    System.out.println("Veuillez n'entrer que des mots ou phrases écrit(e)s en majuscules.");
                    MenuChiffrement();
                }else {
                    //on appelle la class Enigma et la fonction de chiffrement et on affiche le message chiffré
                    String messageEnigma = Enigma.chiffrerEnigma(message);
                    System.out.println("Message chiffré avec la méthode Enigma :" + messageEnigma);
                }
                break;
            
            case "r":
                main(null);
                break;

            default:
                System.out.println("Commande non reconnue. Veuillez entrer une commande valide.");
                MenuChiffrement();
            }
    }

    //fonction du menu de déchiffrement
    private static void MenuDechiffrement() {
        //affichage des options du menu
        System.out.println("\nDéchiffrement :");
        System.out.println("1   - Déchiffrement de César");
        System.out.println("2   - Déchiffrement de Vigenère");
        System.out.println("3   - Déchiffrement avec le carré de Polybe");
        System.out.println("4   - Déchiffrement avec la méthode Enigma");
        System.out.println("r   - Retour au menu principal");
        System.out.println("q   - Quitte le programme");

        //initialisation du scanner pour obtenir l'entrée de l'utilisateur
        Scanner scanner = new Scanner(System.in);
        String commande = scanner.nextLine().toLowerCase();
        String message = "";

        //switch pour les différentes options du menu
        switch (commande) {    
            case "1":
                System.out.println("Entrez le message à déchiffrer : ");
                message = scanner.nextLine();
                if (message.isEmpty()) {
                    System.out.println("Veuillez entrer un message à déchiffrer");
                    MenuDechiffrement();
                }
                break;

            case "2":
                System.out.println("Entrez le message à déchiffrer : ");
                message = scanner.nextLine();
                if (message.isEmpty()) {
                    System.out.println("Veuillez entrer un message à déchiffrer");
                    
                }
                break;

            case "3":
                System.out.println("Entrez le message à chiffrer : ");
                message = scanner.nextLine();
                if (message.isEmpty()) {
                    System.out.println("Veuillez entrer un message à déchiffrer");
                    MenuChiffrement();
                }
                break;

            case "4":
                //messsage affiché
                System.out.println("Entrez le message à déchiffrer : ");
                message = scanner.nextLine();
                //si le message n'est pas vide on appelle la classe Enigma et la fonction chiffrerEnigma
                if (message.isEmpty()) {
                    System.out.println("Veuillez entrer un message à déchiffrer.");
                    MenuDechiffrement();
                } 
                //vérification du caractère valide
                if (!message.matches("[A-Z ]*")) {
                    System.out.println("Veuillez n'entrer que des mots ou phrases écrit(e)s en majuscules.");
                    MenuDechiffrement();
                }else {
                    String messageDechiffre = Enigma.chiffrerEnigma(message);
                    //on affiche le message déchiffré 
                    System.out.println("Message déchiffré avec la méthode Enigma : " + messageDechiffre);
                }
                break;
            

            case "r":
                main(null);
                break;
                
            default:
                System.out.println("Commande non reconnue. Veuillez entrer une commande valide.");
                MenuDechiffrement();
            }   
    }

    
}