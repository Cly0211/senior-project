package com.seniorProject.project.controller;

import com.seniorProject.project.model.Entry;
import com.seniorProject.project.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/entry")
public class EntryController {
    @Autowired
    EntryService entryService;

    @GetMapping("/selectEntry/{id}") //TODO: add new vars to path
    public Entry selectEntry(@PathVariable String id, @PathVariable String date){
        try{
            Entry entry = entryService.selectEntry(id, date);
            return entry;
        }catch(Exception e){
            throw new RuntimeException("error when searching entry: "+id+", "+date);
        }
    }

    @GetMapping("/selectEntries/{id}")
    public List<Entry> selectEntries(@PathVariable String id){
        try{
            List<Entry> list = entryService.selectEntries(id);
            //System.out.println(list.get(0).getId());
            return list;
        }catch(Exception e){
            throw new RuntimeException("error when searching entries: "+id+":\n"+e);
        }
    }

    @PostMapping("/insertEntry") //TODO: add new vars to path
    public void insertEntry(@RequestBody Entry entry){
        try{
            entryService.insertEntry(entry);
        }catch(Exception e){
            throw new RuntimeException("error when inserting entry: " + entry);
        }
    }

    @GetMapping("/deleteEntry/{id}") //TODO: add new vars to path
    public void deleteEntry(@PathVariable String id, @PathVariable String date){
        try{
            entryService.deleteEntry(id, date);
        }catch(Exception e){
            throw new RuntimeException("error when deleting entry: "+id+", "+date+":\n"+e);
        }
    }

    @GetMapping("/updateEntry/{id}") //TODO: add new vars to path
    public void updateEntry(@PathVariable String id, @PathVariable String date, @PathVariable int mood, @PathVariable String activities, @PathVariable String journal){
        try{
            entryService.updateEntry(id, date, mood, activities, journal);
        }catch(Exception e){
            throw new RuntimeException("error when updating entry: "+id+", "+date+":\n"+e);
        }
    }

    //Maps activites to their average mood value
    public Map<String, Double> activityMoodMap( String id){
        Map<String, Double> out = new HashMap<String, Double>();
        Map<String, Integer> totals = new HashMap<String, Integer>();
        //Iterate through days
        for (Entry entry : selectEntries(id)) {
            //Iterate through all activities on that day
            for (String activity : entry.getActivities().split(",")) {
                double oldAvg = out.getOrDefault(activity, 0.0);
                int newTotal = totals.getOrDefault(activity, 0) + 1;
                //Compute average mood using previous average
                out.put(activity, oldAvg + ((entry.getMood() - oldAvg) / newTotal));
                totals.put(activity, newTotal);
            }
        }
        return out;
    }

    //Ensures the CaseID is formatted correctly
    private void validateID(String input) throws Exception{
        //Make sure size is correct
        if (input.length() > 7 || input.length() < 4) { throw new Exception("Malformed ID: Length"); }
        //Iterate through input
        for (int i = 0; i < input.length(); i++){
            //Make sure first three characters are letters
            if (i < 3 && !Character.isLetter(input.charAt(i))){
                throw new Exception("Malformed ID: Letters");
            }
            else
            //Make sure rest of the characters are numbers
            if (!Character.isDigit(input.charAt(i))){
                throw new Exception("Malformed ID: Numbers");
            }
        }
    }
}
