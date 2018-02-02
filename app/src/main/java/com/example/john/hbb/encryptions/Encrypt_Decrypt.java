package com.example.john.hbb.encryptions;

import android.util.Base64;

import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import static com.example.john.hbb.encryptions.AESHelper.toHex;

/**
 * Created by john on 10/23/17.
 */

public class Encrypt_Decrypt {

    private final static String ALGORITHM = "AES";
    public static String encrypt(String message){

        String seedValue = "OPM_k3NuBeME@gmail.com";
        String normalTextEnc="";
        try {
            normalTextEnc = AESHelper.encrypt(seedValue, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return normalTextEnc;
    }
    public static String decrypt(String message){


        String seedValue = "OPM_k3NuBeME@gmail.com";
        String strDecryptedText="";
        try {
            strDecryptedText = AESHelper.decrypt(seedValue, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDecryptedText;
    }

    public static String cipher(String secretKey, String data) throws Exception {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), secretKey.getBytes(), 128, 256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKey key = new SecretKeySpec(tmp.getEncoded(), ALGORITHM);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return toHex(cipher.doFinal(data.getBytes()));
    }

    public static String decipher(String secretKey, String data) throws Exception {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), secretKey.getBytes(), 128, 256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKey key = new SecretKeySpec(tmp.getEncoded(), ALGORITHM);

        Cipher cipher = Cipher.getInstance(ALGORITHM);

        cipher.init(Cipher.DECRYPT_MODE, key);

        //return new String (Base64.encode(cipher.doFinal(toByte(data)), Base64.DEFAULT));
        return new String(cipher.doFinal(toByte(data)));
    }

    private static byte[] toByte(String hexString) {
        int len = hexString.length()/2;

        byte[] result = new byte[len];

        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();
        return result;
    }

    public static String toHex(byte[] stringBytes) {
        StringBuffer result = new StringBuffer(2*stringBytes.length);

        for (int i = 0; i < stringBytes.length; i++) {
            result.append(HEX.charAt((stringBytes[i]>>4)&0x0f)).append(HEX.charAt(stringBytes[i]&0x0f));
        }

        return result.toString();
    }

    private final static String HEX = "0123456789ABCDEF";
}
