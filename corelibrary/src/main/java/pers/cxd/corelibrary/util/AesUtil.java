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
public class AesUtil {

    private static final String sAesMode = "AES/CFB/NOPadding";

    public static final String sAesKey = "NV9MCANO5VVCMUASPSLILYSM19990127";

    private static final String sIvKey = "PSLILYSM19990127";

    private static final Singleton<Cipher> sDefaultEncryptCipher = new Singleton<Cipher>() {
        @Override
        protected Cipher create() {
            try {
                Cipher cipher = Cipher.getInstance(sAesMode);
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(sAesKey.getBytes(), "AES"), new IvParameterSpec(sIvKey.getBytes()));
                return cipher;
            } catch (Exception ex) {
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
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    };

    /**
     * 使用默认的AES配置进行加密
     *
     * @param str 要加密的字符串
     * @return 加密好的字符串，最后会BASE64编码一下
     */
    public static String encrypt(String str) {
        try {
            byte[] bytes = sDefaultEncryptCipher.getInstance().doFinal(str.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeToString(bytes, Base64.NO_WRAP);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用默认的AES配置进行解密
     *
     * @param str 要解密的字符串，一定要是BASE64格式的
     * @return 解密好的字符串
     */
    public static String decrypt(String str) {
        try {
            return new String(sDefaultDecryptCipher.getInstance().doFinal(Base64.decode(str, Base64.NO_WRAP)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * AES加密
     *
     * @param str     要加密的字符串
     * @param aesMode AES模式
     * @param aesKey  AES加密的Key
     * @param ivKey   AES加密的向量
     * @return 加密好的字符串，最后会BASE64编码一下
     */
    public static String encrypt(String str, String aesMode, String aesKey, String ivKey) {
        try {
            Cipher cipher = Cipher.getInstance(aesMode);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(aesKey.getBytes(), "AES"), new IvParameterSpec(ivKey.getBytes()));
            byte[] bytes = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeToString(bytes, Base64.NO_WRAP);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * AES解密
     *
     * @param str     要解密的字符串，一定要是BASE64格式的
     * @param aesMode AES模式
     * @param aesKey  AES加密的Key
     * @param ivKey   AES加密的向量
     * @return 解密好的字符串
     */
    public static String decrypt(String str, String aesMode, String aesKey, String ivKey) throws Exception {
        Cipher cipher = Cipher.getInstance(aesMode);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(aesKey.getBytes(), "AES"), new IvParameterSpec(ivKey.getBytes()));
        return new String(cipher.doFinal(Base64.decode(str, Base64.NO_WRAP)), StandardCharsets.UTF_8);
    }


}
