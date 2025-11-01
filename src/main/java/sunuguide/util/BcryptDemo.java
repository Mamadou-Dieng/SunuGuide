package sunuguide.util;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptDemo {

    public static void main(String[] args) {
        // 1. Instanciation de l'encodeur BCrypt
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String motDePasseClair = "passer";
        System.out.println("--- Démonstration de l'Encodage BCrypt ---");
        System.out.println("Mot de passe en clair : " + motDePasseClair);

        // 2. Hachage du mot de passe
        String hashStocke = encoder.encode(motDePasseClair);
        System.out.println("Hash stocké (DB)       : " + hashStocke);

        // 3. Re-hachage pour montrer que le sel change
        String nouveauHash = encoder.encode(motDePasseClair);
        System.out.println("Nouveau Hash généré    : " + nouveauHash);
        System.out.println("Note : Les deux hash sont différents, mais les deux correspondent au même mot de passe clair.");

        System.out.println("\n--- Démonstration de la Vérification (Matches) ---");

        // --- CAS 1 : Mot de passe Correct ---
        String tentativeCorrecte = "passer";
        boolean matchCorrect = encoder.matches(tentativeCorrecte, hashStocke);
        System.out.println("Tentative 1 : '" + tentativeCorrecte + "' vs Hash -> Résultat : " + (matchCorrect ? "✅ MATCH" : "❌ ERREUR"));

        // --- CAS 2 : Mot de passe Incorrect ---
        String tentativeIncorrecte = "MauvaisMotDePasse";
        boolean matchIncorrect = encoder.matches(tentativeIncorrecte, hashStocke);
        System.out.println("Tentative 2 : '" + tentativeIncorrecte + "' vs Hash -> Résultat : " + (matchIncorrect ? "✅ MATCH" : "❌ ERREUR"));
    }
}