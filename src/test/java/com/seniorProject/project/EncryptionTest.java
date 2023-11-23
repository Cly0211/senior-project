package com.seniorProject.project;


import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seniorProject.project.model.Verification;
import com.seniorProject.project.util.CipherUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;
import java.security.spec.X509EncodedKeySpec;

public class EncryptionTest {
    private String text = "Abc@1234";
    @Test
    public void md5(){
        String s1 = SecureUtil.md5(text);
        String s2 = SecureUtil.md5(text);
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s1.equals(s2));
    }

    @Test
    public void aes(){
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        AES aes = SecureUtil.aes(key);
        String s1 = aes.encryptHex(text);
        String s2 = aes.encryptHex(text);
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s1.equals(s2));
        String s3 = aes.decryptStr(s1);
        System.out.println(s3);
    }

    @Test
    public void des(){
        DES des = SecureUtil.des();
        String s1 = des.encryptHex(text);
        String s2 = des.encryptHex(text);
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s1.equals(s2));
        String s3 = des.decryptStr(s1);
        System.out.println(s3);
    }

    @Test
    public void bcrypt(){
        String s1 = BCrypt.hashpw(text);
        String s2 = BCrypt.hashpw(text);
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(BCrypt.checkpw(text,s1));
        System.out.println(BCrypt.checkpw(text,s2));
    }

    @Test
    public void jsonToClass(){
        String j = "{\n" +
                "    \"id\" : \"jxw13355\",\n" +
                "    \"verCode\" : \"9616\",\n" +
                "    \"expiredDate\"  :  \"2023-11-22T13:43:15\" \n" +
                "}";
        try{
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            ObjectMapper objectMapper = new ObjectMapper();
            Verification v = objectMapper.readValue(j, Verification.class);
            System.out.println(v.getId());
        }catch (Exception e){
            throw new RuntimeException("error when converting json to class");
        }
    }

    @Test
    public void generateRSAkeyPair(){
        String plain = "test secret message";
        try{
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair pair = generator.generateKeyPair();
            byte[] publicKey = pair.getPublic().getEncoded();
            byte[] privateKey = pair.getPrivate().getEncoded();
            Base64.Encoder encoder = Base64.getEncoder();
            String publicKey_string = encoder.encodeToString(publicKey);
            String privateKey_string = encoder.encodeToString(privateKey);
            System.out.println(publicKey_string);
            System.out.println(privateKey_string);
        }catch(Exception e){
            System.out.println("Exception thrown : " + e);
        }
    }

    @Test
    public void encryptDecrypt(){
        String e = CipherUtil.rsaEncrypt(text);
        System.out.println(e);
        String d = CipherUtil.rsaDecrypt(e);
        System.out.println(d);

        String t = "MqARWTjpvJ4uwXl5m1ASxlrtf2l2ZRWVgEoeItpGzj34asejHMvM0Ti97jtQ9Eqz";
        System.out.println(CipherUtil.aesDecrypt(t,"rovTLCG5nEG74M1F"));
    }

}
