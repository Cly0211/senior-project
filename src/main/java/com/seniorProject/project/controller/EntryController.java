package com.seniorProject.project.controller;

import com.seniorProject.project.model.Entry;
import com.seniorProject.project.model.User;
import com.seniorProject.project.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/entry")
public class EntryController {
    @Autowired
    EntryService entryService;

    @GetMapping("/getId/{id}")
    public List<Entry> getId(@PathVariable String id){
        try{
            return entryService.selectId(id);
        }catch(Exception e){
            throw new RuntimeException("error when searching entries: "+id);
        }
    }

    @PostMapping("/add")
    public void addEntry(@RequestBody Entry entry){
        try{
            entryService.insertEntry(entry);
        }catch(Exception e){
            throw new RuntimeException("error when inserting entries: "+entry);
        }
    }

    @PutMapping("/update")
    public void updateUser(@RequestBody Entry entry){
        try{
            entryService.updateEntry(entry);
        }catch(Exception e){
            throw new RuntimeException("error when updating entry: "+entry);
        }
    }
}
