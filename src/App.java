import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        //initialisation du scanner pour pouvoir obtenir le message de l'utilisateur
        Scanner scanner = new Scanner(System.in);
        
        //boucle qui s'exécute lors du lancement de l'app
        while (true) {
            //affichage du guide pour l'utilisateur
            System.out.print("Entrez une commande : ");
            System.out.println("\nhelp    - Affiche ce menu d'aide");
            System.out.println("q    - Quitte le programme");
            //utiliser le scanner pour obtenir la String que l'utilisateur entre en console
            String commande = scanner.nextLine().toLowerCase();
            
            switch (commande) {
                //si l'entrée est "help", on appelle la fonction du Menu d'aide
                case "help":
                    MenuAide();
                    break;
                //si l'entrée est "q", on ferme le scanner et arrête l'app
                case "q":
                    scanner.close();
                    return;
                //si une commande n'existe pas, on envoie un message de non reconnaissance
                default:
                    System.out.println("Commande non reconnue. Tapez 'help' pour voir les commandes disponibles.");
            }
        }
    }
    
    //fonction du menu d'aide
    private static void MenuAide() {
        //affichage du texte d'aide
        System.out.println("\nAide du programme :");
        System.out.println("Commandes disponibles :");
        System.out.println("help    - Affiche ce menu d'aide");
        System.out.println("q    - Quitte le programme");
        System.out.println("1    - Cette commande permet de chiffrer une chaîne de caractères en utilisant un chiffrement entré par l'utilisateur, cela sécurise n'importe quelle communication");
        System.out.println("2    - Cette commande permet de déchiffrer une chaîne de caractères en utilisant un chiffrement entré par l'utilisateur");
        System.out.println("3    - ");
    }
}