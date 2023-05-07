package com.yogeshpaliyal.common.dbhelper

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.security.InvalidKeyException
import java.security.Key
import java.security.NoSuchAlgorithmException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/*
* @author Yogesh Paliyal
* techpaliyal@gmail.com
* https://techpaliyal.com
* https://yogeshpaliyal.com
* created on 07-02-2021 18:50
*/

private const val BUFFER_SIZE = 4096

object EncryptionHelper {
    private const val ALGORITHM = "AES"
    private const val TRANSFORMATION = "AES/CBC/PKCS5Padding"

    private val iV = byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

    @Throws(CryptoException::class)
    fun doCryptoEncrypt(
        key: String,
        data: String,
        outputFile: OutputStream?
    ) {
        try {
            val secretKey: Key =
                SecretKeySpec(key.toByteArray(), ALGORITHM)
            val cipher = Cipher.getInstance(TRANSFORMATION)

            cipher.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(iV))

            data.byteInputStream().use {
                val inputStream = it
                outputFile?.use {
                    val outputStream = it
                    CipherOutputStream(outputStream, cipher).use {
                        inputStream.copyTo(it, BUFFER_SIZE)
                    }
                }
            }
        } catch (ex: NoSuchPaddingException) {
            // Log.d("TestingEnc","NoSuchPaddingException")
            throw CryptoException("Error encrypting/decrypting file", ex)
        } catch (ex: NoSuchAlgorithmException) {
            // Log.d("TestingEnc","NoSuchAlgorithmException")
            throw CryptoException("Error encrypting/decrypting file", ex)
        } catch (ex: InvalidKeyException) {
            // Log.d("TestingEnc","InvalidKeyException")
            throw CryptoException("Error encrypting/decrypting file", ex)
        } catch (ex: BadPaddingException) {
            // Log.d("TestingEnc","BadPaddingException")
            throw CryptoException("Error encrypting/decrypting file", ex)
        } catch (ex: IllegalBlockSizeException) {
            // Log.d("TestingEnc","IllegalBlockSizeException")
            throw CryptoException("Error encrypting/decrypting file", ex)
        } catch (ex: IOException) {
            // Log.d("TestingEnc","IOException")
            throw CryptoException("Error encrypting/decrypting file", ex)
        }
    }

    @Throws(CryptoException::class)
    fun doCryptoDecrypt(
        key: String,
        inputFile: InputStream?
    ): String {
        var data : String
        try {
            val secretKey: Key =
                SecretKeySpec(key.toByteArray(), ALGORITHM)
            val cipher = Cipher.getInstance(TRANSFORMATION)

            cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iV))

            inputFile.use {
                val inputStream = it
                CipherInputStream(inputStream, cipher).use {
                    data = String(it.readBytes())
                }
            }
        } catch (ex: NoSuchPaddingException) {
            // Log.d("TestingEnc","NoSuchPaddingException")
            throw CryptoException("Error encrypting/decrypting file", ex)
        } catch (ex: NoSuchAlgorithmException) {
            // Log.d("TestingEnc","NoSuchAlgorithmException")
            throw CryptoException("Error encrypting/decrypting file", ex)
        } catch (ex: InvalidKeyException) {
            // Log.d("TestingEnc","InvalidKeyException")
            throw CryptoException("Error encrypting/decrypting file", ex)
        } catch (ex: BadPaddingException) {
            // Log.d("TestingEnc","BadPaddingException")
            throw CryptoException("Error encrypting/decrypting file", ex)
        } catch (ex: IllegalBlockSizeException) {
            // Log.d("TestingEnc","IllegalBlockSizeException")
            throw CryptoException("Error encrypting/decrypting file", ex)
        } catch (ex: IOException) {
            // Log.d("TestingEnc","IOException")
            throw CryptoException("Error encrypting/decrypting file", ex)
        }

        return data
    }
}
