package com.seniorProject.project;

record Entry (Integer mood, String[] activities, String journal) 
{
    Integer getMood () { return mood; }
    String[] getActivities () { return activities; };
    String getJournal () { return journal; };
}
