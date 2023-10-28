package com.seniorProject.project.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class Verification {
    private String id;
    private String verCode;
    private Date expiredDate;
}
