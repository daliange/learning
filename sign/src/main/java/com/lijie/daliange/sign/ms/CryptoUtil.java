/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * paychannel-cmsb-provider $Id$ $Revision$ Last Changed by SJ at 2016年9月18日
 * 上午11:05:16 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2016年9月18日
 * Initailized
 */
package com.lijie.daliange.sign.ms;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @ClassName ：CryptoUtil
 * @author : SJ
 * @Date : 2016年9月18日 上午11:05:16
 * @version 2.0.0
 *
 */
public class CryptoUtil
{
    private static final Logger                            logger = LoggerFactory.getLogger(CryptoUtil.class);
    private static final ConcurrentHashMap<String, Object> keys   = new ConcurrentHashMap<String, Object>();

    public static PublicKey getPulbicKey(String keyName, String publicKeyPath)
    {
        PublicKey publicKey = (PublicKey) keys.get("PUBLIC_KEY");
        if (publicKey == null)
        {
            String classpathKey = "classpath:";
            if (publicKeyPath != null)
            {
                try
                {
                    InputStream inputStream = null;
                    if (publicKeyPath.startsWith(classpathKey))
                    {
                        inputStream = CryptoUtil.class.getClassLoader()
                                .getResourceAsStream(publicKeyPath.substring(classpathKey.length()));
                    }
                    else
                    {
                        inputStream = new FileInputStream(publicKeyPath);
                    }
                    publicKey = CryptoUtil.getPublicKey(inputStream, "RSA");
                    keys.put(keyName, publicKey);
                }
                catch (Exception e)
                {
                    logger.error("无法加载银行公钥[{}]", new Object[] { publicKeyPath });
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return (PublicKey) keys.get(keyName);
    }
    
    /**
     * 获取公钥对象
     * 
     * @param InputStream
     *            公钥输入流
     * @param keyAlgorithm
     *            密钥算法
     * @return 公钥对象
     * @throws Exception
     */
    public static PublicKey getPublicKey(InputStream inputStream, String keyAlgorithm) throws Exception
    {
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String readLine = null;
            while ((readLine = br.readLine()) != null)
            {
                if (readLine.charAt(0) == '-')
                {
                    continue;
                }
                else
                {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(Base64.decodeBase64(sb.toString()));
            KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
            PublicKey publicKey = keyFactory.generatePublic(pubX509);

            return publicKey;
        }
        catch (FileNotFoundException e)
        {
            throw new Exception("公钥路径文件不存在");
        }
        catch (IOException e)
        {
            throw new Exception("读取公钥异常");
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new Exception(String.format("生成密钥工厂时没有[%s]此类算法", keyAlgorithm));
        }
        catch (InvalidKeySpecException e)
        {
            throw new Exception("生成公钥对象异常");
        }
        finally
        {
            try
            {
                if (inputStream != null)
                {
                    inputStream.close();
                }
            }
            catch (IOException e)
            {
            }
        }
    }

    /**
     * 获取私钥对象
     * 
     * @param inputStream
     *            私钥输入流
     * @param keyAlgorithm
     *            密钥算法
     * @return 私钥对象
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(InputStream inputStream, String keyAlgorithm) throws Exception
    {
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String readLine = null;
            while ((readLine = br.readLine()) != null)
            {
                if (readLine.charAt(0) == '-')
                {
                    continue;
                }
                else
                {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(sb.toString()));
            KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
            PrivateKey privateKey = keyFactory.generatePrivate(priPKCS8);

            return privateKey;
        }
        catch (FileNotFoundException e)
        {
            throw new Exception("私钥路径文件不存在");
        }
        catch (IOException e)
        {
            throw new Exception("读取私钥异常");
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new Exception("生成私钥对象异常");
        }
        catch (InvalidKeySpecException e)
        {
            throw new Exception("生成私钥对象异常");
        }
        finally
        {
            try
            {
                if (inputStream != null)
                {
                    inputStream.close();
                }
            }
            catch (IOException e)
            {
            }
        }
    }

    /**
     * 数字签名函数入口
     * 
     * @param plainBytes
     *            待签名明文字节数组
     * @param privateKey
     *            签名使用私钥
     * @param signAlgorithm
     *            签名算法
     * @return 签名后的字节数组
     * @throws Exception
     */
    public static byte[] digitalSign(byte[] plainBytes, PrivateKey privateKey, String signAlgorithm) throws Exception
    {
        try
        {
            Signature signature = Signature.getInstance(signAlgorithm);
            signature.initSign(privateKey);
            signature.update(plainBytes);
            byte[] signBytes = signature.sign();

            return signBytes;
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new Exception(String.format("数字签名时没有[%s]此类算法", signAlgorithm));
        }
        catch (InvalidKeyException e)
        {
            throw new Exception("数字签名时私钥无效");
        }
        catch (SignatureException e)
        {
            throw new Exception("数字签名时出现异常");
        }
    }

    /**
     * 验证数字签名函数入口
     * 
     * @param plainBytes
     *            待验签明文字节数组
     * @param signBytes
     *            待验签签名后字节数组
     * @param publicKey
     *            验签使用公钥
     * @param signAlgorithm
     *            签名算法
     * @return 验签是否通过
     * @throws Exception
     */
    public static boolean verifyDigitalSign(byte[] plainBytes, byte[] signBytes, PublicKey publicKey,
            String signAlgorithm) throws Exception
    {
        boolean isValid = false;
        try
        {
            Signature signature = Signature.getInstance(signAlgorithm);
            signature.initVerify(publicKey);
            signature.update(plainBytes);
            isValid = signature.verify(signBytes);
            return isValid;
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new Exception(String.format("验证数字签名时没有[%s]此类算法", signAlgorithm));
        }
        catch (InvalidKeyException e)
        {
            throw new Exception("验证数字签名时公钥无效");
        }
        catch (SignatureException e)
        {
        	logger.error(e.getMessage(), e);
            throw new Exception("验证数字签名时出现异常");
        }
    }

    /**
     * 加密
     * 
     * @param plainBytes
     *            明文字节数组
     * @param publicKey
     *            公钥
     * @param keyLength
     *            密钥bit长度
     * @param reserveSize
     *            padding填充字节数，预留11字节
     * @param cipherAlgorithm
     *            加解密算法，一般为RSA/ECB/PKCS1Padding
     * @return 加密后字节数组，不经base64编码
     * @throws Exception
     */
    public static byte[] encrypt(byte[] plainBytes, PublicKey publicKey, int keyLength, int reserveSize,
            String cipherAlgorithm) throws Exception
    {
        int keyByteSize = keyLength / 8; // 密钥字节数
        int encryptBlockSize = keyByteSize - reserveSize; // 加密块大小=密钥字节数-padding填充字节数
        int nBlock = plainBytes.length / encryptBlockSize;// 计算分段加密的block数，向上取整
        if ((plainBytes.length % encryptBlockSize) != 0)
        { // 余数非0，block数再加1
            nBlock += 1;
        }

        try
        {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            // 输出buffer，大小为nBlock个keyByteSize
            ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * keyByteSize);
            // 分段加密
            for (int offset = 0; offset < plainBytes.length; offset += encryptBlockSize)
            {
                int inputLen = plainBytes.length - offset;
                if (inputLen > encryptBlockSize)
                {
                    inputLen = encryptBlockSize;
                }

                // 得到分段加密结果
                byte[] encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen);
                // 追加结果到输出buffer中
                outbuf.write(encryptedBlock);
            }

            outbuf.flush();
            outbuf.close();
            return outbuf.toByteArray();
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new Exception(String.format("没有[%s]此类加密算法", cipherAlgorithm));
        }
        catch (NoSuchPaddingException e)
        {
            throw new Exception(String.format("没有[%s]此类填充模式", cipherAlgorithm));
        }
        catch (InvalidKeyException e)
        {
            throw new Exception("无效密钥");
        }
        catch (IllegalBlockSizeException e)
        {
            throw new Exception("加密块大小不合法");
        }
        catch (BadPaddingException e)
        {
            throw new Exception("错误填充模式");
        }
        catch (IOException e)
        {
            throw new Exception("字节输出流异常");
        }
    }

    /**
     * RSA解密
     * 
     * @param encryptedBytes
     *            加密后字节数组
     * @param privateKey
     *            私钥
     * @param keyLength
     *            密钥bit长度
     * @param reserveSize
     *            padding填充字节数，预留11字节
     * @param cipherAlgorithm
     *            加解密算法，一般为RSA/ECB/PKCS1Padding
     * @return 解密后字节数组，不经base64编码
     * @throws Exception
     */
    public static byte[] decrypt(byte[] encryptedBytes, PrivateKey privateKey, int keyLength, int reserveSize,
            String cipherAlgorithm) throws Exception
    {
        int keyByteSize = keyLength / 8; // 密钥字节数
        int decryptBlockSize = keyByteSize - reserveSize; // 解密块大小=密钥字节数-padding填充字节数
        int nBlock = encryptedBytes.length / keyByteSize;// 计算分段解密的block数，理论上能整除

        try
        {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            // 输出buffer，大小为nBlock个decryptBlockSize
            ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * decryptBlockSize);
            // 分段解密
            for (int offset = 0; offset < encryptedBytes.length; offset += keyByteSize)
            {
                // block大小: decryptBlock 或 剩余字节数
                int inputLen = encryptedBytes.length - offset;
                if (inputLen > keyByteSize)
                {
                    inputLen = keyByteSize;
                }

                // 得到分段解密结果
                byte[] decryptedBlock = cipher.doFinal(encryptedBytes, offset, inputLen);
                // 追加结果到输出buffer中
                outbuf.write(decryptedBlock);
            }

            outbuf.flush();
            outbuf.close();
            return outbuf.toByteArray();
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new Exception(String.format("没有[%s]此类解密算法", cipherAlgorithm));
        }
        catch (NoSuchPaddingException e)
        {
            throw new Exception(String.format("没有[%s]此类填充模式", cipherAlgorithm));
        }
        catch (InvalidKeyException e)
        {
            throw new Exception("无效密钥");
        }
        catch (IllegalBlockSizeException e)
        {
            throw new Exception("解密块大小不合法");
        }
        catch (BadPaddingException e)
        {
        	logger.error(e.getMessage(), e);
            throw new Exception("错误填充模式");
        }
        catch (IOException e)
        {
            throw new Exception("字节输出流异常");
        }
    }

    /**
     * 字符数组转16进制字符串
     * 
     * @param bytes
     * @return
     */
    public static String bytes2string(byte[] bytes, int radix)
    {
        // 2个16进制字符占用1个字节，8个二进制字符占用1个字节
        int size = 2;
        if (radix == 2)
        {
            size = 8;
        }
        StringBuilder sb = new StringBuilder(bytes.length * size);
        for (int i = 0; i < bytes.length; i++)
        {
            int integer = bytes[i];
            while (integer < 0)
            {
                integer = integer + 256;
            }
            String str = Integer.toString(integer, radix);
            sb.append(StringUtils.leftPad(str.toUpperCase(), size, "0"));
        }
        return sb.toString();
    }
    
    /**
	 * 十六进制转二进制数组
	 * @param s
	 * @return
	 */
	public static final byte[] hex2Bytes(String s) {
		if(s == null){
			return null;
		}
		byte[] bytes;
		bytes = new byte[s.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2),16);
		}
		return bytes;
	}

    /**
     * AES加密
     * 
     * @param plainBytes
     *            明文字节数组
     * @param keyBytes
     *            密钥字节数组
     * @param keyAlgorithm
     *            密钥算法
     * @param cipherAlgorithm
     *            加解密算法
     * @param IV
     *            随机向量
     * @return 加密后字节数组，不经base64编码
     * @throws ServiceException
     */
    public static byte[] AESEncrypt(byte[] plainBytes, byte[] keyBytes, String keyAlgorithm, String cipherAlgorithm, String IV)
            throws Exception {
        try {
            // AES密钥长度为128bit、192bit、256bit，默认为128bit
            if (keyBytes.length % 8 != 0 || keyBytes.length < 16 || keyBytes.length > 32) {
                throw new Exception("AES密钥长度不合法");
            }

            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            SecretKey secretKey = new SecretKeySpec(keyBytes, keyAlgorithm);
            if (StringUtils.trimToNull(IV) != null) {
                IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            }

            byte[] encryptedBytes = cipher.doFinal(plainBytes);

            return encryptedBytes;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(String.format("没有[%s]此类加密算法", cipherAlgorithm));
        } catch (NoSuchPaddingException e) {
            throw new Exception(String.format("没有[%s]此类填充模式", cipherAlgorithm));
        } catch (InvalidKeyException e) {
            throw new Exception("无效密钥");
        } catch (InvalidAlgorithmParameterException e) {
            throw new Exception("无效密钥参数");
        } catch (BadPaddingException e) {
            throw new Exception("错误填充模式");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("加密块大小不合法");
        }
    }

    /**
     * AES解密
     * 
     * @param encryptedBytes
     *            密文字节数组，不经base64编码
     * @param keyBytes
     *            密钥字节数组
     * @param keyAlgorithm
     *            密钥算法
     * @param cipherAlgorithm
     *            加解密算法
     * @param IV
     *            随机向量
     * @return 解密后字节数组
     * @throws ServiceException
     */
    public static byte[] AESDecrypt(byte[] encryptedBytes, byte[] keyBytes, String keyAlgorithm, String cipherAlgorithm, String IV)
            throws Exception {
        try {
            // AES密钥长度为128bit、192bit、256bit，默认为128bit
            if (keyBytes.length % 8 != 0 || keyBytes.length < 16 || keyBytes.length > 32) {
                throw new Exception("AES密钥长度不合法");
            }

            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            SecretKey secretKey = new SecretKeySpec(keyBytes, keyAlgorithm);
            if (IV != null && StringUtils.trimToNull(IV) != null) {
                IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
                cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
            }

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            return decryptedBytes;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(String.format("没有[%s]此类加密算法", cipherAlgorithm));
        } catch (NoSuchPaddingException e) {
            throw new Exception(String.format("没有[%s]此类填充模式", cipherAlgorithm));
        } catch (InvalidKeyException e) {
            throw new Exception("无效密钥");
        } catch (InvalidAlgorithmParameterException e) {
            throw new Exception("无效密钥参数");
        } catch (BadPaddingException e) {
            throw new Exception("错误填充模式");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("解密块大小不合法");
        }
    }
    
    /**
     * RSA加密
     * 
     * @param plainBytes
     *            明文字节数组
     * @param publicKey
     *            公钥
     * @param keyLength
     *            密钥bit长度
     * @param reserveSize
     *            padding填充字节数，预留11字节
     * @param cipherAlgorithm
     *            加解密算法，一般为RSA/ECB/PKCS1Padding
     * @return 加密后字节数组，不经base64编码
     * @throws ServiceException
     */
    public static byte[] RSAEncrypt(byte[] plainBytes, PublicKey publicKey, int keyLength, int reserveSize, String cipherAlgorithm)
            throws Exception {
        int keyByteSize = keyLength / 8; // 密钥字节数
        int encryptBlockSize = keyByteSize - reserveSize; // 加密块大小=密钥字节数-padding填充字节数
        int nBlock = plainBytes.length / encryptBlockSize;// 计算分段加密的block数，向上取整
        if ((plainBytes.length % encryptBlockSize) != 0) { // 余数非0，block数再加1
            nBlock += 1;
        }

        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            // 输出buffer，大小为nBlock个keyByteSize
            ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * keyByteSize);
            // 分段加密
            for (int offset = 0; offset < plainBytes.length; offset += encryptBlockSize) {
                int inputLen = plainBytes.length - offset;
                if (inputLen > encryptBlockSize) {
                    inputLen = encryptBlockSize;
                }

                // 得到分段加密结果
                byte[] encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen);
                // 追加结果到输出buffer中
                outbuf.write(encryptedBlock);
            }

            outbuf.flush();
            outbuf.close();
            return outbuf.toByteArray();
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(String.format("没有[%s]此类加密算法", cipherAlgorithm));
        } catch (NoSuchPaddingException e) {
            throw new Exception(String.format("没有[%s]此类填充模式", cipherAlgorithm));
        } catch (InvalidKeyException e) {
            throw new Exception("无效密钥");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("加密块大小不合法");
        } catch (BadPaddingException e) {
            throw new Exception("错误填充模式");
        } catch (IOException e) {
            throw new Exception("字节输出流异常");
        }
    }
    /**
     * RSA解密
     * 
     * @param encryptedBytes
     *            加密后字节数组
     * @param privateKey
     *            私钥
     * @param keyLength
     *            密钥bit长度
     * @param reserveSize
     *            padding填充字节数，预留11字节
     * @param cipherAlgorithm
     *            加解密算法，一般为RSA/ECB/PKCS1Padding
     * @return 解密后字节数组，不经base64编码
     * @throws ServiceException
     */
    public static byte[] RSADecrypt(byte[] encryptedBytes, PrivateKey privateKey, int keyLength, int reserveSize, String cipherAlgorithm)
            throws Exception {
        int keyByteSize = keyLength / 8; // 密钥字节数
        int decryptBlockSize = keyByteSize - reserveSize; // 解密块大小=密钥字节数-padding填充字节数
        int nBlock = encryptedBytes.length / keyByteSize;// 计算分段解密的block数，理论上能整除

        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            // 输出buffer，大小为nBlock个decryptBlockSize
            ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * decryptBlockSize);
            // 分段解密
            for (int offset = 0; offset < encryptedBytes.length; offset += keyByteSize) {
                // block大小: decryptBlock 或 剩余字节数
                int inputLen = encryptedBytes.length - offset;
                if (inputLen > keyByteSize) {
                    inputLen = keyByteSize;
                }

                // 得到分段解密结果
                byte[] decryptedBlock = cipher.doFinal(encryptedBytes, offset, inputLen);
                // 追加结果到输出buffer中
                outbuf.write(decryptedBlock);
            }

            outbuf.flush();
            outbuf.close();
            return outbuf.toByteArray();
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(String.format("没有[%s]此类解密算法", cipherAlgorithm));
        } catch (NoSuchPaddingException e) {
            throw new Exception(String.format("没有[%s]此类填充模式", cipherAlgorithm));
        } catch (InvalidKeyException e) {
            throw new Exception("无效密钥");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("解密块大小不合法");
        } catch (BadPaddingException e) {
            throw new Exception("错误填充模式");
        } catch (IOException e) {
            throw new Exception("字节输出流异常");
        }
    }
    public static  byte[] hexString2ByteArr(String hexStr) {
        return new BigInteger(hexStr, 16).toByteArray();
    }
    public static boolean isnull(String str)
    {
        if (null == str || str.equalsIgnoreCase("null") || str.trim().equals(""))
        {
            return true;
        } else
            return false;
    }
    /**
     * 字符数组转16进制字符串
     * 
     * @param bytes
     * @return
     */
    public static byte[] string2bytes(String str, int radix) {
        byte[] srcBytes = str.getBytes();

        // 2个16进制字符占用1个字节，8个二进制字符占用1个字节
        int size = 2;
        if (radix == 2) {
            size = 8;
        }

        byte[] tgtBytes = new byte[srcBytes.length / size];
        for (int i = 0; i < srcBytes.length; i = i + size) {
            String tmp = new String(srcBytes, i, size);
            tgtBytes[i / size] = (byte) Integer.parseInt(tmp, radix);
        }
        return tgtBytes;
    }
    
    /**
	 * 获取RSA私钥对象
	 * 
	 * @param filePath
	 *            RSA私钥路径
	 * @param fileSuffix
	 *            RSA私钥名称，决定编码类型
	 * @param password
	 *            RSA私钥保护密钥
	 * @param keyAlgorithm
	 *            密钥算法
	 * @return RSA私钥对象
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static PrivateKey getRSAPrivateKeyByFileSuffix(String filePath, String fileSuffix, String password, String keyAlgorithm)
			throws Exception {
		String keyType = "";
		if ("keystore".equalsIgnoreCase(fileSuffix)) {
			keyType = "JKS";
		} else if ("pfx".equalsIgnoreCase(fileSuffix) || "p12".equalsIgnoreCase(fileSuffix)) {
			keyType = "PKCS12";
		} else if ("jck".equalsIgnoreCase(fileSuffix)) {
			keyType = "JCEKS";
		} else if ("pem".equalsIgnoreCase(fileSuffix) || "pkcs8".equalsIgnoreCase(fileSuffix)) {
			keyType = "PKCS8";
		} else if ("pkcs1".equalsIgnoreCase(fileSuffix)) {
			keyType = "PKCS1";
		} else if ("yljf".equalsIgnoreCase(fileSuffix)) {
			keyType = "yljf";
		} else if ("ldys".equalsIgnoreCase(fileSuffix)) {
			keyType = "ldys";
		} else{
			keyType = "JKS";
		}

		InputStream in = null;
		try {
			in = new FileInputStream(filePath);
			PrivateKey priKey = null;
			if ("JKS".equals(keyType) || "PKCS12".equals(keyType) || "JCEKS".equals(keyType)) {
				KeyStore ks = KeyStore.getInstance(keyType);
				if (password != null) {
					char[] cPasswd = password.toCharArray();
					ks.load(in, cPasswd);
					Enumeration<String> aliasenum = ks.aliases();
					String keyAlias = null;
					while (aliasenum.hasMoreElements()) {
						keyAlias = (String) aliasenum.nextElement();
						priKey = (PrivateKey) ks.getKey(keyAlias, cPasswd);
						if (priKey != null)
							break;
					}
				}
			}else if("yljf".equals(keyType)){
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String s = br.readLine();
				PKCS8EncodedKeySpec priPKCS8=new PKCS8EncodedKeySpec(hexStrToBytes(s));
				KeyFactory keyf=KeyFactory.getInstance("RSA");
				PrivateKey myprikey=keyf.generatePrivate(priPKCS8);
				return myprikey;
			}else if("ldys".equals(keyType)){
				byte[] b = new byte[20480];
				in.read(b);
				PKCS8EncodedKeySpec priPKCS8=new PKCS8EncodedKeySpec(b);
				KeyFactory keyf=KeyFactory.getInstance("RSA");
				PrivateKey myprikey=keyf.generatePrivate(priPKCS8);
				return myprikey;
			}else {
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				StringBuilder sb = new StringBuilder();
				String readLine = null;
				while ((readLine = br.readLine()) != null) {
					if (readLine.charAt(0) == '-') {
						continue;
					} else {
						sb.append(readLine);
						sb.append('\r');
					}
				}
				if ("PKCS8".equals(keyType)) {
					PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(sb.toString()));
					KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
					priKey = keyFactory.generatePrivate(priPKCS8);
				} 
			}

			return priKey;
		} catch (FileNotFoundException e) {
			throw new Exception("私钥路径文件不存在");
		} catch (KeyStoreException e) {
			throw new Exception("获取KeyStore对象异常");
		} catch (IOException e) {
			throw new Exception("读取私钥异常");
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("生成私钥对象异常");
		} catch (CertificateException e) {
			throw new Exception("加载私钥密码异常");
		} catch (UnrecoverableKeyException e) {
			throw new Exception("生成私钥对象异常");
		} catch (InvalidKeySpecException e) {
			throw new Exception("生成私钥对象异常");
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
			}
		}
	}
	
	public static final byte[] hexStrToBytes(String s) {
		byte[] bytes; 
		bytes = new byte[s.length() / 2];
		for (int i = 0; i < bytes.length; i++) { 
			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * 		i + 2), 16);
		} 
		return bytes;
	}
    
}
