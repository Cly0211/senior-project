package com.seniorProject.project.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Encryption {
    String encryptedAESkey;
    String cipherText;
}
