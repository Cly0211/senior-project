package com.seniorProject.project.data;

record Entry (Integer mood, String[] activities, String journal) 
{
    Integer getMood () { return mood; }
    String[] getActivities () { return activities; };
    String getJournal () { return journal; };

    public String toString () { return "Mood:" + mood + ", Activities: " + activities + ", Journal: " + journal; }
}
