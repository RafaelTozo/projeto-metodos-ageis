import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class Criptografia {
    private static SecretKey chave;

    public Criptografia() throws Exception{
        chave = null;
    }

    public SecretKey GeradorChave() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        chave = keyGen.generateKey();
        return chave;
    }

    public SecretKey getChave(){
        return chave;
    }

    public static String criptografa(String str, SecretKey chave) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, chave);
        byte[] encryptedData = cipher.doFinal(str.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public static String descriptografa(String strCriptografada, SecretKey chave) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, chave);
        byte[] decodedData = Base64.getDecoder().decode(strCriptografada);
        byte[] decryptedData = cipher.doFinal(decodedData);
        return new String(decryptedData);
    }
}