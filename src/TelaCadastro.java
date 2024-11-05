
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.mindrot.jbcrypt.BCrypt;

import javax.crypto.SecretKey;

public class TelaCadastro {
    public boolean funcionamentoTelaCadastro (String nome, String email, String senha, SecretKey chave) {

        Usuario usuario = new Usuario(nome, email, senha);
        FuncoesBD funcoesBD = new FuncoesBD();
        return funcoesBD.insereUsuario(usuario, chave);

    }

    public static boolean validarEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public static boolean validarSenha(String senha) {
        return senha.length() >= 8;
    }

    public static String hashSenha(String senha) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(senha, salt);
    }

    public static boolean validarUsuario(String usuario){
        return usuario.length() >= 4;
    }
}
