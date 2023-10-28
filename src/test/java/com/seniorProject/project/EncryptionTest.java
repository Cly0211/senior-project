package com.seniorProject.project;


import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import org.junit.jupiter.api.Test;

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
}
