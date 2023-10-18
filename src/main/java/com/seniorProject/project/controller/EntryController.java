package com.seniorProject.project.controller;

import com.seniorProject.project.model.Entry;
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
            List<Entry> list = entryService.selectId(id);
            //System.out.println(list.get(0).getId());
            return list;
        }catch(Exception e){
            throw new RuntimeException("error when searching entries: "+id);
        }
    }
}
