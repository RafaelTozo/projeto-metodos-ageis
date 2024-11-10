import org.mindrot.jbcrypt.BCrypt;

import javax.crypto.SecretKey;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class TelaRecuperacao {
    public boolean funcionamentoTelaRecuperacao (String email, String senha, SecretKey chave) {

        FuncoesBD funcoesBD = new FuncoesBD();
        String senhaDesc = Criptografia.descriptografa(senha,chave);
        return funcoesBD.updateSenha(email,hashSenha(senhaDesc));

    }

    public boolean validarEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public boolean validarSenha(String senha) {
        return senha.length() >= 8;
    }

    public String hashSenha(String senha) {
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
        return new FuncoesMail().enviarEmail(email,"Código Recuperacao");
    }

    public boolean verificaEmail(String email){
        return new FuncoesBD().verificaEmailExistente(email);
    }
}
