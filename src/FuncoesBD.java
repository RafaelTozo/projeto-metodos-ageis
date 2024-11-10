import javax.crypto.SecretKey;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FuncoesBD {
    private Conexao conexao;
    private String query;
    private PreparedStatement ps;
    private ResultSet rs;
    public FuncoesBD(){
        conexao = conexao.getInstance();
    }
  
    public boolean insereUsuario(Usuario usuario) {

        query = "INSERT INTO Usuario (nome, email, senha) VALUES (?, ?, ?)";
        try {
            ps = conexao.getConexao().prepareStatement(query);
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    
    public boolean retornaUsuario(String email, String senha) {

        query = "SELECT * FROM Usuario WHERE email = ? AND senha = ?";
        try {
            ps = conexao.getConexao().prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, senha);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }

    }

    
    public void criaGrupo(String nomeGrupo) {
        query = "INSERT INTO Grupo (nome) VALUES (?)";
        try {
            ps = conexao.getConexao().prepareStatement(query);
            ps.setString(1, nomeGrupo);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao criar grupo: " + e.getMessage());
        }
    }

    public boolean insereSenha(int idUsuario, String nomeAplicacao, String emailLogin, String senha) {
        query = "INSERT INTO Senhas (nomeAplicacao, emailLogin, senha) VALUES (?, ?, ?)";
        int idSenha = -1;
        try {
            ps = conexao.getConexao().prepareStatement(query);
            ps.setString(1, nomeAplicacao);
            ps.setString(2, emailLogin);
            ps.setString(3, senha);
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                idSenha = generatedKeys.getInt(1);
                adicionaSenhaAoUsuario(idUsuario, idSenha);
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir senha: " + e.getMessage());
            return false;
        }
    }

    public void adicionaUsuarioAoGrupo(int idUsuario, int idGrupo) {
        query = "INSERT INTO Grupo_Usuario (idGrupo, idUsuario) VALUES (?, ?)";
        try {
            ps = conexao.getConexao().prepareStatement(query);
            ps.setInt(1, idGrupo);
            ps.setInt(2, idUsuario);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar usuário ao grupo: " + e.getMessage());
        }
    }

    public void adicionaSenhaAoUsuario(int idUsuario, int idSenha) {
        query = "INSERT INTO Usuario_Senha (idUsuario, idSenha) VALUES (?, ?)";
        try {
            ps = conexao.getConexao().prepareStatement(query);
            ps.setInt(1, idUsuario);
            ps.setInt(2, idSenha);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao associar senha ao usuário: " + e.getMessage());
        }
    }

    public boolean verificaEmailExistente(String email){
        query = "SELECT * FROM Usuario WHERE email = ?";
        try {
            ps = conexao.getConexao().prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            return true;
        }
    }

    public void adicionaSenhaAoGrupo(int idGrupo, int idSenha) {
        query = "INSERT INTO Grupo_Senha (idGrupo, idSenha) VALUES (?, ?)";
        try {
            ps = conexao.getConexao().prepareStatement(query);
            ps.setInt(1, idGrupo);
            ps.setInt(2, idSenha);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao associar senha ao grupo: " + e.getMessage());
        }
    }

    public boolean updateSenha(String email, String senha){
        query = "UPDATE Usuario SET senha = ? WHERE email = ?";
        try {
            ps = conexao.getConexao().prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, senha);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            return false;
        }
    }

}
