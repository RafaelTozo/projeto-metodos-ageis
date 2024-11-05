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
  
    public boolean insereUsuario(Usuario usuario, SecretKey chave) {
        query = "INSERT INTO Usuario (nome, email, senha) VALUES (?, ?, ?)";
        try {
            ps = conexao.getConexao().prepareStatement(query);
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    
    public String retornaNomeUsuario(int idUsuario) {
        String nome = null;
        query = "SELECT nome FROM Usuario WHERE idUsuario = ?";
        try {
            ps = conexao.getConexao().prepareStatement(query);
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();
            if (rs.next()) {
                nome = rs.getString("nome");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar nome do usuário: " + e.getMessage());
        }
        return nome;
    }

    
    public void criaGrupo(String nomeGrupo) {
        query = "INSERT INTO Grupo (nome) VALUES (?)";
        try {
            ps = conexao.getConexao().prepareStatement(query);
            ps.setString(1, nomeGrupo);
            ps.executeUpdate();
            System.out.println("Grupo criado com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao criar grupo: " + e.getMessage());
        }
    }

    public void insereSenha(int idUsuario, String nomeAplicacao, String emailLogin, String senha) {
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
        } catch (SQLException e) {
            System.err.println("Erro ao inserir senha: " + e.getMessage());
        }
    }

    public void adicionaUsuarioAoGrupo(int idUsuario, int idGrupo) {
        query = "INSERT INTO Grupo_Usuario (idGrupo, idUsuario) VALUES (?, ?)";
        try {
            ps = conexao.getConexao().prepareStatement(query);
            ps.setInt(1, idGrupo);
            ps.setInt(2, idUsuario);
            ps.executeUpdate();
            System.out.println("Usuário adicionado ao grupo com sucesso.");
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
            System.out.println("Senha associada ao usuário com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao associar senha ao usuário: " + e.getMessage());
        }
    }

    public void adicionaSenhaAoGrupo(int idGrupo, int idSenha) {
        query = "INSERT INTO Grupo_Senha (idGrupo, idSenha) VALUES (?, ?)";
        try {
            ps = conexao.getConexao().prepareStatement(query);
            ps.setInt(1, idGrupo);
            ps.setInt(2, idSenha);
            ps.executeUpdate();
            System.out.println("Senha associada ao grupo com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao associar senha ao grupo: " + e.getMessage());
        }
    }

}
