/**
 * Cette classe permet de chiffrer et déchiffrer un message en utilisant la méthode de la machine Enigma
 */
public class Enigma {

  //initialisation des variables 
  public static String rotor = "EKMFLGDQVZNTOWYHXUSPAIBRCJ";
  public static int rotorPosition = 0;
  public static String reflecteur = "YRUHQSLDPXNGOKMIEBFZCWVJAT";
  public static String tableauConnexions = "AM FI NV PS TU WZ AB JC KD LE MG OH PI QR ST UX YV";

  /**
   * Cette fonction permet de chiffrer un message pris en paramètre et renvoie le message chiffré en utilisant la méthode d'Enigma
   * @param message
   * @return
   */
  public static String chiffrerEnigma( String message){
    rotorPosition = 0;
    StringBuilder resultat = new StringBuilder();
    int taille = message.length();

    for (int i = 0; i <taille; i++){
      //récupération du caractère de message à l'indice i
      char lettre = message.charAt(i);

      //utiliser les tableaux de connexions
      for (String connexion : tableauConnexions.split(" ")) {
        if (connexion.charAt(0) == lettre) {
            lettre = connexion.charAt(1);
            break;
        } else if (connexion.charAt(1) == lettre) {
            lettre = connexion.charAt(0);
            break;
        }
      }

      //faire tourner le rotor après chaque lettre
      rotor = rotor.substring(1) + rotor.charAt(0);

      //faire tourner le rotor à droite
      int index = lettre - 'A';
      char lettreRotorDroite = rotor.charAt(index);

      //utiliser le réflécteur
      int indexReflecteur = reflecteur.indexOf(lettreRotorDroite);
      char lettreReflecteur = reflecteur.charAt(indexReflecteur);

      //réutiliser le rotor à gauche
      int indexRetourRotor = rotor.indexOf(lettreReflecteur);
      char lettreRotorGauche = (char) ('A' + indexRetourRotor);

      //ajouter la lettre chiffrée dans le resultat
      resultat.append(lettreRotorGauche);
    }

    //définir le message à partir du resultat
    message = resultat.toString();
 
    //renvoie le message
    return message;
  }
}
