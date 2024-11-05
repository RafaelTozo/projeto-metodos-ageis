import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.mindrot.jbcrypt.BCrypt;

import javax.crypto.SecretKey;

public class TelaLogin {
    public boolean funcionamentoTelaLogin (String email, String senha, SecretKey chave) {

        FuncoesBD funcoesBD = new FuncoesBD();
        return funcoesBD.retornaUsuario(email, senha, chave);

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
}
