import java.util.List;

public class TelaSenha{
    public int inserirSenha(String email,Senha senha) {
        FuncoesBD funcoesBD = new FuncoesBD();
        int idSenha = funcoesBD.insereSenha(email, senha);
        if(idSenha > 0){
            return idSenha;
        }else{
            return 0;
        }

    }

    public static List<Integer> listarIds(String email){
        return new FuncoesBD().retornarIdsSenhas(email);
    }

    public static Senha retornaSenha(int idSenha){
        return new FuncoesBD().retornaSenha(idSenha);
    }
}
