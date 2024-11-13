public class ChiffrementRot {

  public static String chiffrer(String message, int decalage) {

    StringBuilder resultat = new StringBuilder();
    int taille = message.length();

    for (int i = 0; i < taille; i++) {
      //on récupère le caractère du message à l'indice i
      char caractere = message.charAt(i);
      //permet de s'assurer que le caractère est bien une lettre
      if (Character.isLetter(caractere)) {
          //permet de savoir si la lettre est une majuscule ou une minuscule
          char base = Character.isUpperCase(caractere) ? 'A' : 'a';
          //on fait le décalage en appliquant le modulo et faisant en sorte que la lettre soit dans l'alphabet et la base convertit le nombre en caractère ascii, donc une majuscule ou une miniscule
          int lettre = ((caractere - base + decalage) % 26 + 26) % 26 + base;
          //on ajoute le caractère dans le résultat
          resultat.append((char) lettre);
      } else if (Character.isDigit(caractere)) {
          //on récupère l'entier depuis le caractère si s'en est un
          int chiffre = Character.getNumericValue(caractere);
          //on fait le décalage en appliquant le modulo de 0 à 10
          int nouveauChiffre = (chiffre + decalage) % 10;
          if (nouveauChiffre < 0) nouveauChiffre += 10;
          //on ajoute le caractère dans le résultat
          resultat.append(nouveauChiffre);
      } else {
          resultat.append(caractere);
      }
    }
    //faire en sorte de mettre chiffre en string et de renvoyer le texte chiffré
    message = resultat.toString();
    //afficher le texte chiffré en console
    return (message);
  }

  public static String dechiffrer(String message, int decalage) {
    
    //on applique le chiffrement pour déchiffrer en mettant le décalage en négatif
    return chiffrer(message, -decalage);
  }
  
}