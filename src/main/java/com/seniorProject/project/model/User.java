package com.seniorProject.project.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class User {
    private String email;
    private String password;
}
