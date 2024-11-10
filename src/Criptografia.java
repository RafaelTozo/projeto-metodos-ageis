import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class Criptografia {
    private static SecretKey chave;

    public Criptografia() {
        chave = null;
    }

    public SecretKey geradorChave() {
        try{
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            chave = keyGen.generateKey();
            return chave;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public static String criptografa(String str, SecretKey chave)  {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, chave);
            byte[] encryptedData = cipher.doFinal(str.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String descriptografa(String strCriptografada, SecretKey chave)  {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, chave);
            byte[] decodedData = Base64.getDecoder().decode(strCriptografada);
            byte[] decryptedData = cipher.doFinal(decodedData);
            return new String(decryptedData);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
