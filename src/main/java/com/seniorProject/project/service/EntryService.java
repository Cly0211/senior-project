package com.seniorProject.project.service;

import com.seniorProject.project.model.Entry;
import com.seniorProject.project.mapper.EntryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntryService {
    @Autowired
    Entry entry;
    @Autowired
    EntryMapper entryMapper;

    public List<Entry> selectId(String id) {
        return entryMapper.selectEntry(id);
    }
}
