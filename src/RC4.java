public class RC4 {
  private byte[] S = new byte[256];

  /**
   * Constructeur RC4 pour initialiser la permutation S avec la clé
   * 
   * @param cle La clé de chiffrement/déchiffrement
   */
  public RC4(String cle) {
      // Conversion de la clé en tableau d'octets
      byte[] bitsCle = cle.getBytes();
      int logueurCle = bitsCle.length;

      // Initialisation du tableau S avec des valeurs de 0 à 255
      for (int i = 0; i < 256; i++) {
          S[i] = (byte) i;
      }

      // Mélange de S en fonction de la clé
      int j = 0;
      for (int i = 0; i < 256; i++) {
          // Calcul de l'index j basé sur la clé et le contenu de S
          j = (j + S[i] + bitsCle[i % logueurCle]) & 0xFF;
          // Échange des valeurs S[i] et S[j] pour le mélange
          byte temp = S[i];
          S[i] = S[j];
          S[j] = temp;
      }
  }

  /**
   * Génère un octet pseudo-aléatoire pour le chiffrement ou déchiffrement
   * Cette méthode est appelée à chaque fois qu'on a besoin d'un octet du flux de
   * clé
   * 
   * @return un octet pseudo-aléatoire basé sur l'état actuel de S
   */
  private byte genererUnOctet() {
      // Initialisation des indices i et j
      int i = 0;
      int j = 0;

      // Mélange de S pour générer un octet pseudo-aléatoire
      i = (i + 1) & 0xFF;
      j = (j + S[i]) & 0xFF;
      // Échange des valeurs S[i] et S[j] pour le mélange
      byte temp = S[i];
      S[i] = S[j];
      S[j] = temp;

      // Calcul de l'index k pour générer l'octet pseudo-aléatoire
      int k = S[(S[i] + S[j]) & 0xFF];

      return (byte) k;
  }

  /**
   * Chiffre ou déchiffre un message en appliquant RC4
   * 
   * @param message Tableau d'octets représentant le texte à chiffrer ou
   *                déchiffrer
   * @return Le texte chiffré ou déchiffré sous forme de tableau d'octets
   */
  public byte[] chiffrerDechiffrer(byte[] message) {
      // Créer un tableau pour stocker le résultat chiffré ou déchiffré
      byte[] sortie = new byte[message.length];

      // Parcourir chaque octet du message pour le chiffrer ou déchiffrer
      for (int k = 0; k < message.length; k++) {
          // XOR entre l'octet du message et l'octet généré par genererUnOctet()
          sortie[k] = (byte) (message[k] ^ genererUnOctet());
      }


      // Retourne le tableau d'octets chiffré ou déchiffré
      return sortie;
  }

  /**
   * Méthode main pour tester le chiffrement et le déchiffrement avec RC4
   */
  public static void main(String[] args) {
      // Clé secrète pour le chiffrement et le déchiffrement
      String key = "f240485ebe4d995194c220d353a2bdd883807ae1b3572670aea1df45256f0e55";
      // Message à chiffrer
      String message = "Bonjour, comment ça va ?";

      System.out.println("Message original : " + message);
      // Instanciation de RC4 avec la clé
      RC4 rc4 = new RC4(key);
      // Chiffrement du message
      byte[] encryptedMessage = rc4.chiffrerDechiffrer(message.getBytes());

      // Affichage du message chiffré
      System.out.println("Message chiffré : " + new String(encryptedMessage));

      // Déchiffrement (utilise la même clé et le même algorithme)
      RC4 rc4Decrypt = new RC4(key);
      byte[] decryptedMessage = rc4Decrypt.chiffrerDechiffrer(encryptedMessage);

      // Affichage du message déchiffré
      System.out.println("Message déchiffré : " + new String(decryptedMessage));
  }
}