package pers.cxd.corelibrary.util;

import android.util.Base64;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import pers.cxd.corelibrary.Singleton;

/**
 * Miscellaneous {@link Cipher} utility methods.
 *
 * @author pslilysm
 * @since 1.0.0
 */
public class AesUtils {

    public static final String sAesKey = "NV9MCANO5VVCMUASPSLILYSM19990127";

    private static final String sIvKey =  "PSLILYSM19990127";

    private static final String sAesMode = "AES/CFB/NOPadding";

    private static final Singleton<Cipher> sDefaultEncryptCipher = new Singleton<Cipher>() {
        @Override
        protected Cipher create() {
            try {
                Cipher cipher = Cipher.getInstance(sAesMode);
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(sAesKey.getBytes(), "AES"), new IvParameterSpec(sIvKey.getBytes()));
                return cipher;
            } catch (Exception ex){
                throw new RuntimeException(ex);
            }
        }
    };

    private static final Singleton<Cipher> sDefaultDecryptCipher = new Singleton<Cipher>() {
        @Override
        protected Cipher create() {
            try {
                Cipher cipher = Cipher.getInstance(sAesMode);
                cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(sAesKey.getBytes(), "AES"), new IvParameterSpec(sIvKey.getBytes()));
                return cipher;
            } catch (Exception ex){
                throw new RuntimeException(ex);
            }
        }
    };

    /**
     * @param str 原始字符串
     * @return 加密的字符串
     */
    public static String encrypt(String str){
        try {
            byte[] bytes = sDefaultEncryptCipher.getInstance().doFinal(str.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeToString(bytes, Base64.NO_WRAP);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param str 加密的字符串
     * @return 解密后的字符串
     */
    public static String decrypt(String str){
        try {
            return new String(sDefaultDecryptCipher.getInstance().doFinal(Base64.decode(str, Base64.NO_WRAP)), StandardCharsets.UTF_8);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static String encrypt(String str, String key){
        try {
            Cipher cipher = Cipher.getInstance(sAesMode);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"), new IvParameterSpec(sIvKey.getBytes()));
            byte[] bytes = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeToString(bytes, Base64.NO_WRAP);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String str, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(sAesMode);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"), new IvParameterSpec(sIvKey.getBytes()));
        return new String(cipher.doFinal(Base64.decode(str, Base64.NO_WRAP)), StandardCharsets.UTF_8);
    }


}
