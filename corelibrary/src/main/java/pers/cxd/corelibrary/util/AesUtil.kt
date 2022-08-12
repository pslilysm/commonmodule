package pers.cxd.corelibrary.util

import android.util.Base64
import pers.cxd.corelibrary.Singleton
import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * Miscellaneous [Cipher] utility methods.
 *
 * @author pslilysm
 * @since 1.0.0
 */
object AesUtil {
    private const val sAesMode = "AES/CFB/NOPadding"
    const val sAesKey = "NV9MCANO5VVCMUASPSLILYSM19990127"
    private const val sIvKey = "PSLILYSM19990127"
    private val sDefaultEncryptCipher: Singleton<Cipher> = object : Singleton<Cipher>() {
        override fun create(): Cipher {
            return try {
                val cipher = Cipher.getInstance(sAesMode)
                cipher.init(
                    Cipher.ENCRYPT_MODE,
                    SecretKeySpec(sAesKey.toByteArray(), "AES"),
                    IvParameterSpec(
                        sIvKey.toByteArray()
                    )
                )
                cipher
            } catch (ex: Exception) {
                throw RuntimeException(ex)
            }
        }
    }
    private val sDefaultDecryptCipher: Singleton<Cipher> = object : Singleton<Cipher>() {
        override fun create(): Cipher {
            return try {
                val cipher = Cipher.getInstance(sAesMode)
                cipher.init(
                    Cipher.DECRYPT_MODE,
                    SecretKeySpec(sAesKey.toByteArray(), "AES"),
                    IvParameterSpec(
                        sIvKey.toByteArray()
                    )
                )
                cipher
            } catch (ex: Exception) {
                throw RuntimeException(ex)
            }
        }
    }

    /**
     * 使用默认的AES配置进行加密
     *
     * @param str 要加密的字符串
     * @return 加密好的字符串，最后会BASE64编码一下
     */
    fun encrypt(str: String): String {
        return try {
            val bytes =
                sDefaultEncryptCipher.get().doFinal(str.toByteArray(StandardCharsets.UTF_8))
            Base64.encodeToString(bytes, Base64.NO_WRAP)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    /**
     * 使用默认的AES配置进行解密
     *
     * @param str 要解密的字符串，一定要是BASE64格式的
     * @return 解密好的字符串
     */
    fun decrypt(str: String?): String {
        return try {
            String(
                sDefaultDecryptCipher.get()
                    .doFinal(Base64.decode(str, Base64.NO_WRAP)), StandardCharsets.UTF_8
            )
        } catch (e: Exception) {
            throw RuntimeException(e)
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
    fun encrypt(str: String, aesMode: String?, aesKey: String, ivKey: String): String {
        return try {
            val cipher = Cipher.getInstance(aesMode)
            cipher.init(
                Cipher.ENCRYPT_MODE,
                SecretKeySpec(aesKey.toByteArray(), "AES"),
                IvParameterSpec(ivKey.toByteArray())
            )
            val bytes = cipher.doFinal(str.toByteArray(StandardCharsets.UTF_8))
            Base64.encodeToString(bytes, Base64.NO_WRAP)
        } catch (e: Exception) {
            throw RuntimeException(e)
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
    @Throws(Exception::class)
    fun decrypt(str: String?, aesMode: String?, aesKey: String, ivKey: String): String {
        val cipher = Cipher.getInstance(aesMode)
        cipher.init(
            Cipher.DECRYPT_MODE,
            SecretKeySpec(aesKey.toByteArray(), "AES"),
            IvParameterSpec(ivKey.toByteArray())
        )
        return String(cipher.doFinal(Base64.decode(str, Base64.NO_WRAP)), StandardCharsets.UTF_8)
    }
}