import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Cette classe permet de hacher un mot de passe en utilisant MD5
 */
public class MD5 {

    /**
     * Cette fonction prend en paramètre un mot de passe et renvoie son hash au format héxadécimal
     * @param password
     * @return
     */
    public static String hash(String password){
      try {
      //utiliser Messagedigest pour instancier le hash MD5
      MessageDigest md = MessageDigest.getInstance("MD5");

      //utilisation du messagedigest pour hacher le message
      byte[] hashBytes = md.digest(password.getBytes());

      //créer un stringbuilder pour ajouter le format héxadécimal du mot de passe
      StringBuilder hexString = new StringBuilder();

      //convertir le résultat au format héxadécimal
      for (byte b : hashBytes) {

        //ajouter l'héxadécimal au stringbuilder
        hexString.append(String.format("%02x", b));
      }
      //retourner le résultat à l'utilisateur
      return hexString.toString();
    }
    catch (NoSuchAlgorithmException e) {
      return "Erreur de cryptage";
    }
  }
}
