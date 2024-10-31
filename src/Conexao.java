import java.io.*;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

public class Conexao {
    private static Conexao conex;
    private final String con_banco;
    private final String usuario;
    private final String senha;
    private Connection conn;
    private Conexao() {
        conex = null;
        con_banco = "jdbc:mysql://127.0.0.1:3306/projetobd?useSSL=false";
        usuario = "root";
        senha = "root";


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection(con_banco, new String(usuario), new String(senha));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static Conexao getInstance(){
        if(conex == null){
            conex = new Conexao();
        }
        return conex;
    }
    public Connection getConexao(){
        return this.conn;
    }
}
