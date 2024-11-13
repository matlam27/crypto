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
        System.out.println("\nAide du programme :");
        System.out.println("Commandes disponibles :");
        System.out.println("help    - Affiche ce menu d'aide");
        System.out.println("q   - Quitte le programme");
        System.out.println("1   - Cette commande permet de chiffrer une chaîne de caractères en utilisant un chiffrement entré par l'utilisateur, cela sécurise n'importe quelle communication");
        System.out.println("2   - Cette commande permet de déchiffrer une chaîne de caractères en utilisant un chiffrement entré par l'utilisateur");
        System.out.println("3   - ");
    }

    //fonction du menu de chiffrement
    private static void MenuChiffrement() {
        //affichage des options du menu
        System.out.println("\nChiffrement :");
        System.out.println("1   - Chiffrement de César");
        System.out.println("2   - Chiffrement de Vigenère");
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

            case "r":
                main(null);
                break;
                
            default:
                System.out.println("Commande non reconnue. Veuillez entrer une commande valide.");
                MenuDechiffrement();
            }   
    }

    
}