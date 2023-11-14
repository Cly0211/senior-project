package com.seniorProject.project.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Entry {
    private String id;

    private String entryDate;

    private Integer mood;
    private String activities;
    private String journal;
}
