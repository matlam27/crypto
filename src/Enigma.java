/**
 * Cette classe permet de chiffrer et déchiffrer un message en utilisant la méthode de la machine Enigma
 */
public class Enigma {

  //initialisation des variables
  public static String rotor1 = "EKMFLGDQVZNTOWYHXUSPAIBRCJ";
  public static String rotor2 = "AJDKSIRUXBLHWTMCQGZNPYFVOE";
  public static String rotor3 = "BDFHJLCPRTXVZNYEIWGAKMUSQO";
  public static String reflecteur = "YRUHQSLDPXNGOKMIEBFZCWVJAT";
  public static String tableauConnexions = "AM FI NV PS TU WZ KB JC QD LE OG RH YX";

  /**
   * Cette fonction permet de chiffrer un message pris en paramètre et renvoie le message chiffré en utilisant la méthode d'Enigma
   * @param message
   * @return
   */
  public static String chiffrerEnigma(String message) {
    //mettre les rotors à 0 pour les réinitialiser
    int rotor1Position = 0;
    int rotor2Position = 0;
    int rotor3Position = 0;
    //création du stringbuilder pour ajouter les caractères dans le résultat
    StringBuilder resultat = new StringBuilder();
    //défini la taille du message
    int taille = message.length();

    for (int i = 0; i < taille; i++) {
      //récupération du caractère de message à l'indice i
      char lettre = message.charAt(i);

      //gestion des espaces pour les laisser tel quel
      if (lettre == ' '){
        resultat.append(' ');
        continue;
      }

      //vérification du caractère valide
      if(!Character.isLetter(lettre)) {
        continue;
      }

      //utiliser les tableaux de connexions pour trouver une paire avec la lettre
      lettre = passerDansTableauConnexions(lettre);

      //faire tourner les rotors vers la droite après chaque lettre (aller)
      lettre = passerDansRotor(lettre, rotor1, rotor1Position, true);
      lettre = passerDansRotor(lettre, rotor2, rotor2Position, true);
      lettre = passerDansRotor(lettre, rotor3, rotor3Position, true);

      //utiliser le réflécteur pour appliquer le miroir
      lettre = reflecteur.charAt(lettre - 'A');

      //faire tourner les rotors vers la gauche pour chaque lettre (retour)
      lettre = passerDansRotor(lettre, rotor3, rotor3Position, false);
      lettre = passerDansRotor(lettre, rotor2, rotor2Position, false);
      lettre = passerDansRotor(lettre, rotor1, rotor1Position, false);

      //réutiliser le tableau de connexions pour trouver une paire de lettres
      lettre = passerDansTableauConnexions(lettre);

      //ajouter la lettre chiffré au résultat
      resultat.append(lettre);

      //changer la position des rotors
      rotor1Position = (rotor1Position + 1) % 26;
      if (rotor1Position == 0) {
        rotor2Position = (rotor2Position + 1) % 26;
        if (rotor2Position == 0) {
          rotor3Position = (rotor3Position + 1) % 26;
        }
      }
    }

    //retourner le resultat avec toutes les lettres chiffrées
    return resultat.toString();
  }

  private static char passerDansTableauConnexions(char lettre) {
    //récupérer la position de la lettre dans le tableau de connexions
    for (String connexion : tableauConnexions.split(" ")) {
      //si la lettre est la première d'une paire on retourne la seconde
      if (connexion.charAt(0) == lettre) {
        return connexion.charAt(1);
        //si la lettre est la deuxième d'une paire on retourne la première
      } else if (connexion.charAt(1) == lettre) {
        return connexion.charAt(0);
      }
    }
    //si la lettre n'est pas dans le tableau de connexions on la retourne
    return lettre;
  }

  private static char passerDansRotor(char lettre, String rotor, int position, boolean sensAller) {
    //retourne l'index de la lettre dans le rotor pour le chemin aller
    if (sensAller) {
      int index = (lettre - 'A' + position) % 26;
      return rotor.charAt(index);
      //retourne le caractère en fonction de l'index et de la position en partant de a
    } else {
      int index = rotor.indexOf(lettre);
      return (char) ('A' + (index - position + 26) % 26);
    }
  }
}