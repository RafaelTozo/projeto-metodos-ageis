public class TelaSenha{
    public TelaSenha(int idUsuario,String nomeAplicacao, String email, String senha) {
        FuncoesBD funcoesBD = new FuncoesBD();
        funcoesBD.insereSenha(idUsuario, nomeAplicacao,email, senha);


    }
}
