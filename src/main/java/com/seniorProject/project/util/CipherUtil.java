package com.seniorProject.project.util;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Data
@Component
public class CipherUtil {
    static String base64PublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmyViHYQ2Ejv4yxTigRQ26Y6kZ4gzWnVcMaE3EvEQs5pKqxuYWY4Tz8CNB7Wh5kIdPuDsPG25xwY9gh7JjqxcnzFTp5udFEbmOPafPSk5U6/PvEtlj3AEQ8jZ3+USwuhj+k9w2/5gHELDgAdVV/TLRq9U47f8V34JGfKfby9C/+DsonQf+h+z43XdwSEZy2Oa0EEpn8YMAEZl5s5wwyHJgAumMrkNiyQ/7BOv8a8G69iUqw8h+vthnatkRguWMF4Z/EzG3pTU2Rfpm6bdxQTi3cGoiaEY93tJpQd2FBxk3H26dP04Ks03CwWWx+2+eDF8tnz5dmyNfiFn98yLNw4cvQIDAQAB";
    static String base64PrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCbJWIdhDYSO/jLFOKBFDbpjqRniDNadVwxoTcS8RCzmkqrG5hZjhPPwI0HtaHmQh0+4Ow8bbnHBj2CHsmOrFyfMVOnm50URuY49p89KTlTr8+8S2WPcARDyNnf5RLC6GP6T3Db/mAcQsOAB1VX9MtGr1Tjt/xXfgkZ8p9vL0L/4OyidB/6H7Pjdd3BIRnLY5rQQSmfxgwARmXmznDDIcmAC6YyuQ2LJD/sE6/xrwbr2JSrDyH6+2Gdq2RGC5YwXhn8TMbelNTZF+mbpt3FBOLdwaiJoRj3e0mlB3YUHGTcfbp0/TgqzTcLBZbH7b54MXy2fPl2bI1+IWf3zIs3Dhy9AgMBAAECggEAANZVJDb4GmXBHvTFkCVtcm829elnQDVil6X4vR0Ylk+JLZ6K1kONBHxT29cgFjUEdrgSA6AioNn+GlCo4nXi/hJEaE16WIdVa2NGqAwr51wLMUdFjLOcYkfth7wdu3gzlJXkfwYYEK76N8ZSWz99RVa24jpg8zvr4qb2M0xm/n3UbMl3ZzZ+dxOHwReLcv1MgFun3yaLS5h0vZsf6uiLJKqKMrRxK5e397kLsb+MYDu6bjb0VB952nqWufYylx8eeKLNplGADm7+zaSjBCQ1Y1BGisZLsM1Sbf2GyPRg44752vTvmx7703EswzyFaeKBWi9sAyQ9IrQ367D1CVjcwQKBgQDAq0t3vGBZyDZ4lCMiuk4XHFTIyhG4XNI7/jUwyOIGiwqOwLfQY81/4YMnA5nPEveWAw6QY80eVptWewhiOf7URuvhwLX5ksLzmpuvo7ueTmz7fRPcHIk805GPuyo65y7YfE+fYL6Ze6ObwWOJz+RYzqY74S7raD9lXLOI25v7cQKBgQDOJJkyulLEVxa9wiEKtNsCn8iAlTjzRBYPzie1svp58CqwuTPB1WIq2dDlfXhLjI72Au2YxeO5TA0CiXBYZ/wosNYxRg5b7QUqn7rWtIv2AbNc6DxQFkqsj90W+rpMv5mfL1vgpmc0S1c3SiJ3cJ+6cr1DU+m1MCroXxtguCvYDQKBgDJ/cvtCA1rkz+oQoZjjjb4e7dXmqHhS08NnoFdV/2/Jl43M0yCqV90xpSdfmTud9Ah8ap/LmKJf/rzO6GaeXr0SjZaQ5OEx5yjkF/QUDQOz+Hp90Ou7CvlHVnp+itX/a9bP1iqmt64QqtQS/tzLAUWJfCZrodmPbDZL8r5nGN3xAoGBAMafSOUx1hHL/+NyqzePrv5GSoBqR2dNkRxFfMl73A+lstx4dkArg9zZ5odYb5ILqqurgpcTBSpC9r4dAxyLhNEIIjlz0N4N8E5zfSjm6Xxtgw7fzU01TVobe+FqUBsm+bQBg7tfWk1u0mg5jCAdainR6D55GdQ0xwMCsHRuv//5AoGBALRVMdSsvPUr+okzxfUgeemiRSDmXxgpZSjkpGrYoqwQRuExUOBG0RA/d+RMUXFsQxHUOgM0XikJ1QpGYca+KndxytxeH87y4s4FSBdOu8nHYKoVS8BBT68W6PftvH7Pv6XGNCgnJP/4IjEO/pIFsOsikKfMg1aInS4nO0Wx7Ljw";

    public static PublicKey getPublicKey(){
        PublicKey publicKey = null;
        try{
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        }catch(Exception e){
            System.out.println("Exception thrown : " + e);
        }
        return publicKey;
    }

    public static PrivateKey getPrivateKey(){
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        }catch(Exception e){
            System.out.println("Exception thrown : " + e);
        }
        try {
            privateKey = keyFactory.generatePrivate(keySpec);
        }catch(Exception e){
            System.out.println("Exception thrown : " + e);
        }
        return privateKey;
    }

    public static String rsaEncrypt(String data){
        try{
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey());
            byte[] encryptedMessageBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedMessageBytes);
        }catch(Exception e){
            System.out.println("Exception thrown : " + e);
        }
        return null;
    }

    public static String rsaDecrypt(String data){
        try{
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());
            return new String(cipher.doFinal(Base64.getDecoder().decode(data.getBytes())));
        }catch(Exception e){
            System.out.println("Exception thrown : " + e);
        }
        return null;
    }

    public static String aesDecrypt(String data, String aesKey){
        try{
            byte[] keyBytes = aesKey.getBytes("UTF-8");
            SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] encryptedBytes = Base64.getDecoder().decode(data);
            return new String(cipher.doFinal(encryptedBytes));
        }catch(Exception e){
            System.out.println("Exception thrown : " + e);
        }
        return null;
    }
}
