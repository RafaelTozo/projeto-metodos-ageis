public class Senha {
    private String nomeAplicacao;
    private String emailLogin;
    private String senha;

    public Senha(String nomeAplicacao, String emailLogin, String senha) {
        this.nomeAplicacao = nomeAplicacao;
        this.emailLogin = emailLogin;
        this.senha = senha;
    }

    public String getNomeAplicacao() {
        return nomeAplicacao;
    }

    public void setNomeAplicacao(String nomeAplicacao) {
        this.nomeAplicacao = nomeAplicacao;
    }

    public String getEmailLogin() {
        return emailLogin;
    }

    public void setEmailLogin(String emailLogin) {
        this.emailLogin = emailLogin;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
