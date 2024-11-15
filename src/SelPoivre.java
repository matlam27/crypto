import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

public class SelPoivre {
    private static final String POIVRE = "P0!vr3_S3cr3t@2024";
    private static final int TAILLE_SEL = 16;

    private final String sel;

    public SelPoivre() {
        this.sel = genererNouveauSel();
    }

    public SelPoivre(String sel) {
        this.sel = sel;
    }

    private String genererNouveauSel() {
        SecureRandom random = new SecureRandom();
        byte[] sel = new byte[TAILLE_SEL];
        random.nextBytes(sel);
        return Base64.getEncoder().encodeToString(sel);
    }

    public String getSel() {
        return sel;
    }


    public String hacher(String texte) {
        try {
            String aHacher = sel + texte + POIVRE;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(aHacher.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur lors du hachage", e);
        }
    }

    public boolean verifier(String texte, String hashAttendu) {
        String hashCalcule = hacher(texte);
        return hashCalcule.equals(hashAttendu);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Menu principal
        while (true) {
            System.out.println("\nMenu :");
            System.out.println("1. Générer un nouveau sel et hacher un mot");
            System.out.println("2. Utiliser un sel existant et hacher un mot");
            System.out.println("3. Vérifier un mot avec sel et hash existants");
            System.out.println("4. Quitter");
            System.out.print("Votre choix : ");

            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    // Nouveau sel automatique
                    System.out.print("Entrez le mot à hacher : ");
                    String mot1 = scanner.nextLine();
                    SelPoivre hasher1 = new SelPoivre();
                    String hash1 = hasher1.hacher(mot1);
                    System.out.println("\nRésultats :");
                    System.out.println("Sel généré : " + hasher1.getSel());
                    System.out.println("Hash       : " + hash1);
                    break;

                case "2":
                    // Sel fourni par l'utilisateur
                    System.out.print("Entrez votre sel : ");
                    String selUtilisateur = scanner.nextLine();
                    System.out.print("Entrez le mot à hacher : ");
                    String mot2 = scanner.nextLine();
                    SelPoivre hasher2 = new SelPoivre(selUtilisateur);
                    String hash2 = hasher2.hacher(mot2);
                    System.out.println("\nRésultats :");
                    System.out.println("Hash : " + hash2);
                    break;

                case "3":
                    // Vérification
                    System.out.print("Entrez le sel : ");
                    String selVerif = scanner.nextLine();
                    System.out.print("Entrez le hash à vérifier : ");
                    String hashVerif = scanner.nextLine();
                    System.out.print("Entrez le mot à vérifier : ");
                    String motVerif = scanner.nextLine();

                    SelPoivre verificateur = new SelPoivre(selVerif);
                    boolean estValide = verificateur.verifier(motVerif, hashVerif);

                    System.out.println("\nRésultat :");
                    System.out.println("Le mot est " + (estValide ? "valide" : "invalide"));
                    break;

                case "4":
                    System.out.println("Au revoir !");
                    scanner.close();
                    return;

                default:
                    System.out.println("Choix invalide, veuillez réessayer");
            }
        }
    }
}