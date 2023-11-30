package com.seniorProject.project.controller;

import com.seniorProject.project.model.Entry;
import com.seniorProject.project.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/entry")
public class EntryController {
    @Autowired
    EntryService entryService;

    //http://localhost:8080/entry/selectEntry/abc1234/2023-09-23
    @GetMapping("/selectEntry/{id}/{date}")
    public Entry selectEntry(@PathVariable String id, @PathVariable String date){
        try{
            Entry entry = entryService.selectEntry(id, date);
            return entry;
        }catch(Exception e){
            throw new RuntimeException("error when searching entry: "+id+", "+date);
        }
    }

    //http://localhost:8080/entry/selectEntries/abc1234
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

    //http://localhost:8080/entry/insertEntry
    @PostMapping("/insertEntry") 
    public void insertEntry(@RequestBody Entry entry){
        try{
            //validateID(entry.id);
            entryService.insertEntry(entry);
        }catch(Exception e){
            throw new RuntimeException("error when inserting entry: " + entry);
        }
    }

    //http://localhost:8080/entry/deleteEntry/abc1234/2023-09-23
    @DeleteMapping("/deleteEntry/{id}/{date}") 
    public void deleteEntry(@PathVariable String id, @PathVariable String date){
        try{
            entryService.deleteEntry(id, date);
        }catch(Exception e){
            throw new RuntimeException("error when deleting entry: "+id+", "+date+":\n"+e);
        }
    }

    //http://localhost:8080/entry/updateEntry/abc1234/2023-09-23/3/classes,projects/hello
    @PostMapping("/updateEntry") 
    public void updateEntry(@RequestBody Entry entry){
        try{
            entryService.updateEntry(entry);
        }catch(Exception e){
            throw new RuntimeException("error when updating entry: "+entry.getId()+", "+entry.getEntryDate()+":\n"+e);
        }
    }

    //Maps activites to their average mood value
    //http://localhost:8080/entry/activityMoodMap/abc1234
    @GetMapping("/activityMoodMap/{id}")
    public Map<String, Double> activityMoodMap(@PathVariable String id){
        Map<String, Double> out = new HashMap<String, Double>();
        Map<String, Integer> totals = new HashMap<String, Integer>();
        //Iterate through days
        for (Entry entry : selectEntries(id)) {
            //Iterate through all activities on that day
            for (String a : entry.getActivities().split(",")) {
                String activity = a.trim();
                //Ensure activity is valid
                if (!activity.equals("")) {
                    double oldAvg = out.getOrDefault(activity, 0.0);
                    int newTotal = totals.getOrDefault(activity, 0) + 1;
                    //Compute average mood using previous average
                    out.put(activity, oldAvg + ((entry.getMood() - oldAvg) / newTotal));
                    totals.put(activity, newTotal);
                }
            }
        }
        return out;
    }

    //Creates a histogram of mood ratings
    //http://localhost:8080/entry/moodHistogram/abc1234
    @GetMapping("/moodHistogram/{id}")
    public int[] moodHistogram(@PathVariable String id){
        int[] out = new int[5];
        //Iterate through days
        for (Entry entry : selectEntries(id)) {
            out[entry.getMood()]++;
        }
        return out;
    }

    //Creates a histogram of activities
    //http://localhost:8080/entry/activityHistogram/abc1234
    @GetMapping("/activityHistogram/{id}")
    public Map<String, Integer> activityHistogram(@PathVariable String id){
        Map<String, Integer> out = new HashMap<String, Integer>();
        //Iterate through days
        for (Entry entry : selectEntries(id)) {
            //Iterate through all activities on that day
            for (String a : entry.getActivities().split(",")) {
                String activity = a.trim();
                //Ensure activity is valid
                if (!activity.equals("")) {
                    out.put(activity, out.getOrDefault(activity, 0) + 1);
                }
            }
        }
        return out;
    }

    //Creates a rolling average of mood ratings given a time span
    //http://localhost:8080/entry/rollingAvg/abc1234/7
    @GetMapping("/rollingAvg/{id}/{days}")
    public Map<java.sql.Date, Double> rollingAvg(@PathVariable String id, @PathVariable int days){
        Map<java.sql.Date, Double> out = new HashMap<java.sql.Date, Double>();
        List<Entry> entrySet = selectEntries(id);
        //Iterate through days
        for (Entry entry1 : entrySet) {
            int total = entry1.getMood(), count = 1;
            java.sql.Date minDate = java.sql.Date.valueOf(entry1.getEntryDate());
            java.sql.Date maxDate = java.sql.Date.valueOf(Date.valueOf(entry1.getEntryDate()).toLocalDate().plusDays(days));
            //Find all entries within the rolling average range
            for (Entry entry2 : entrySet) {
                java.sql.Date date = java.sql.Date.valueOf(entry2.getEntryDate());
                if (date.before(maxDate) && date.after(minDate)){
                    total += entry2.getMood();
                    count++;
                }
            }
            //Averages are mapped by the date on the most recent end of the rolling window
            out.put(maxDate, (double)total / count);
        }
        return out;
    }

    //Ensures the CaseID is formatted correctly
    /*private void validateID(String input) throws Exception{
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
    }*/
}
