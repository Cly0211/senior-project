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

    public Entry selectEntry(String id, String date) {
        return entryMapper.selectEntry(id, date);
    }

    public List<Entry> selectEntries(String id) {
        return entryMapper.selectEntries(id);
    }

    public void insertEntry(Entry entry) {
        entryMapper.insertEntry(entry);
    }

    public void deleteEntry(String id, String date) {
        entryMapper.deleteEntry(id, date);
    }

    public void updateEntry(Entry entry) {
        entryMapper.updateEntry(entry);
    }

}
