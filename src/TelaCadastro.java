import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.mindrot.jbcrypt.BCrypt;

public class TelaCadastro {
    public void funcionamentoTelaCadastro () {
        System.out.println("Tela de Cadastro:");
        Scanner sc = new Scanner(System.in);

        System.out.println("Qual o nome do usuário? ");
        String nome = sc.nextLine();

        System.out.println("Qual o e-mail? ");
        String email = sc.nextLine();

        if (!validarEmail(email)) {
            System.out.println("E-mail inválido!");
            sc.close();
            return;
        }

        System.out.println("Qual a senha? ");
        String senha = sc.nextLine();

        if (!validarSenha(senha)) {
            System.out.println("Senha inválida!");
            sc.close();
            return;
        }

        String senhaHash = hashSenha(senha);

        // Connection connection = conexao
        // PreparedStatement statement = connection.prepareStatement(sql);
        // statement.setString(1, nome);
        // statement.setString(2, email);
        // statement.setString(3, senhaHash);
        // statement.executeUpdate();

        sc.close();
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
