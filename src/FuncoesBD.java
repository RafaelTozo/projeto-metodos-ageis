import javax.crypto.SecretKey;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    public int retornaIdUsuario(String email){
        int idUsuario = -1;
        query = "SELECT idUsuario FROM Usuario WHERE email = ?";

        try {
            ps = conexao.getConexao().prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                idUsuario = rs.getInt("idUsuario");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idUsuario;
    }

    public int insereSenha(String email, Senha senha) {
        query = "INSERT INTO Senhas (nomeAplicacao, emailLogin, senha) VALUES (?, ?, ?)";

        int idSenha = -1;
        try {
            ps = conexao.getConexao().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, senha.getNomeAplicacao());
            ps.setString(2, senha.getEmailLogin());
            ps.setString(3, senha.getSenha());

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();

            if (generatedKeys.next()) {
                idSenha = generatedKeys.getInt(1);
                adicionaSenhaAoUsuario(retornaIdUsuario(email), idSenha);

            }
            return idSenha;
        } catch (SQLException e) {
            return 0;
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

    public Senha retornaSenha(int idSenha){
        Senha senha = null;
        query = "SELECT * FROM Senhas WHERE idSenha = ?";
        try {
            ps = conexao.getConexao().prepareStatement(query);
            ps.setInt(1, idSenha);
            rs = ps.executeQuery();
            if (rs.next()) {
                senha = new Senha(
                        rs.getString("nomeAplicacao"),
                        rs.getString("emailLogin"),
                        rs.getString("senha")
                );
                senha.setIdSenha(Integer.parseInt(rs.getString("idSenha")));
            }
        } catch (SQLException e) {
            return senha;
        }
        return senha;
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
            ps.setString(1, senha);
            ps.setString(2, email);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<Integer> retornarIdsSenhas(String email) {
        List<Integer> idsSenhas = new ArrayList<>();
        int idUsuario = retornaIdUsuario(email);

        if (idUsuario != -1) {
            query = "SELECT idSenha FROM Usuario_Senha WHERE idUsuario = ?";

            try {
                ps = conexao.getConexao().prepareStatement(query);
                ps.setInt(1, idUsuario);
                rs = ps.executeQuery();

                while (rs.next()) {
                    idsSenhas.add(rs.getInt("idSenha"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return idsSenhas;
    }


}
