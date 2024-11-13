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
}