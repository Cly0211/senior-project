package com.seniorProject.project.service;

import com.seniorProject.project.model.Entry;
import com.seniorProject.project.mapper.EntryMapper;
import com.seniorProject.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntryService {
    @Autowired
    Entry entry;
    @Autowired
    EntryMapper entryMapper;

    public Entry selectEntry(String id, java.sql.Date date) {
        return entryMapper.selectEntry(id, date);
    }

    public List<Entry> selectEntries(String id) {
        return entryMapper.selectEntries(id);
    }

    public void insertEntry(String id, java.sql.Date date, int mood, String activities, String journal) {
        insertEntry(id, date, mood, activities, journal);
    }

    public void deleteEntry(String id, java.sql.Date date) {
        entryMapper.deleteEntry(id, date);
    }

    public void updateEntry(String id, java.sql.Date date, int mood, String activities, String journal) {
        entryMapper.updateEntry(id, date, mood, activities, journal);
    }

    public void insertEntry(Entry entry){
        entryMapper.insert(entry);
    }

    public void updateEntry(Entry entry) {
        entryMapper.updateEntry(entry);
    }

}
