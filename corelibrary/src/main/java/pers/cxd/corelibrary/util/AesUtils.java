package pers.cxd.corelibrary.util;

import android.util.Base64;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import pers.cxd.corelibrary.Singleton;

public class AesUtils {

    private static final String sAesKey = "NV9MCANO5VVCMUASPSLILYSM19990127";

    private static final String sIvKey =  "PSLILYSM19990127";

    private static final String sAesMode = "AES/CFB/NOPadding";

    private static final Singleton<Cipher> sEncryptCipher = new Singleton<Cipher>() {
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

    private static final Singleton<Cipher> sDecryptCipher = new Singleton<Cipher>() {
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
            byte[] bytes = sEncryptCipher.getInstance().doFinal(str.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeToString(bytes, Base64.DEFAULT);
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
            return new String(sDecryptCipher.getInstance().doFinal(Base64.decode(str, Base64.DEFAULT)), StandardCharsets.UTF_8);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }


}
