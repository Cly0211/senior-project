package com.seniorProject.project.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Entry {
    public String id;

    public String entryDate;

    public Integer mood;
    public String activities;
    public String journal;
}
