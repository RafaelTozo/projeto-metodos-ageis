
import org.mindrot.jbcrypt.BCrypt;

import javax.crypto.SecretKey;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class TelaCadastro {
    public boolean funcionamentoTelaCadastro (String nome, String email, String senha, SecretKey chave) {
        String senhaDesc = Criptografia.descriptografa(senha,chave);

        Usuario usuario = new Usuario(nome, email, hashSenha(senhaDesc));
        FuncoesBD funcoesBD = new FuncoesBD();
        return funcoesBD.insereUsuario(usuario);

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

    public static boolean validarUsuario(String usuario){
        return usuario.length() >= 4;
    }

    public boolean verificaEmail(String email){
        return new FuncoesBD().verificaEmailExistente(email);
    }
}
