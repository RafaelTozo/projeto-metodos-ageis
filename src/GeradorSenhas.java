import java.security.SecureRandom;


public class GeradorSenhas {
    private static final String letras_min = "abcdefghijklmnopqrstuvwxyz";
    private static final String letras_mai = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String digitos = "0123456789";
    private static final String caracteres = "!@#$%^&*()-+=<>?/{}[]|";
    private static final SecureRandom random = new SecureRandom();

    public static String gerarSenha(int tamanho) {
        if (tamanho < 8) {
            return "O tamanho da senha deve ser no mínimo 8 caracteres.";
        }

        String todosCaracteres = letras_min + letras_mai + digitos + caracteres;

        StringBuilder senha = new StringBuilder(tamanho);

        senha.append(gerarCaractereAleatorio(letras_min));
        senha.append(gerarCaractereAleatorio(letras_mai));
        senha.append(gerarCaractereAleatorio(digitos));
        senha.append(gerarCaractereAleatorio(caracteres));

        for (int i = 4; i < tamanho; i++) {
            senha.append(gerarCaractereAleatorio(todosCaracteres));
        }
        return embaralhar(senha.toString());
    }

    private static char gerarCaractereAleatorio(String caracteres) {
        int index = random.nextInt(caracteres.length());
        return caracteres.charAt(index);
    }

    private static String embaralhar(String senha) {
        char[] array = senha.toCharArray();
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        return new String(array);
    }

}
