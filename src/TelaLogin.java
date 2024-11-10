import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.mindrot.jbcrypt.BCrypt;

import javax.crypto.SecretKey;

public class TelaLogin {
    public boolean funcionamentoTelaLogin (String email, String senha, SecretKey chave) {

        FuncoesBD funcoesBD = new FuncoesBD();
        String senhaDesc = Criptografia.descriptografa(senha,chave);

        return funcoesBD.retornaUsuario(email, hashSenha(senhaDesc));

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

    public String enviaEmail(String email){
        return new FuncoesMail().enviarEmail(email,"Código Login");
    }
}
