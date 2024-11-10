import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import javax.crypto.SecretKey;

public class TelaLogin {
    private static final int MAX_TENTATIVAS = 3;
    private HashMap<String, Integer> tentativasFalhas = new HashMap<>();

    public boolean funcionamentoTelaLogin(String email, String senha, SecretKey chave) {
        // Verifica se o usuário está bloqueado por exceder o número máximo de tentativas
        if (tentativasFalhas.getOrDefault(email, 0) >= MAX_TENTATIVAS) {
            System.out.println("Conta bloqueada devido a tentativas falhas de login.");
            return false;
        }

        FuncoesBD funcoesBD = new FuncoesBD();
        String senhaDesc = Criptografia.descriptografa(senha, chave);
        
        boolean loginSucesso = funcoesBD.retornaUsuario(email, hashSenha(senhaDesc));

        if (loginSucesso) {
            // Login bem-sucedido: zera contagem de tentativas falhas para o usuário
            tentativasFalhas.put(email, 0);
        } else {
            // Incrementa a contagem de tentativas falhas
            int tentativas = tentativasFalhas.getOrDefault(email, 0) + 1;
            tentativasFalhas.put(email, tentativas);
            System.out.println("Tentativa de login falhou. Tentativa " + tentativas + " de " + MAX_TENTATIVAS);
        }

        return loginSucesso;
    }

    public static boolean validarEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public static boolean validarSenha(String senha) {
        return senha.length() >= 8;
    }

    public static String hashSenha(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(senha.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String enviaEmail(String email) {
        return new FuncoesMail().enviarEmail(email, "Código Login");
    }
}
