package com.seniorProject.project.data;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

class DataHandler
{
    String id;
    Connection con;
    String[] attributes = {"mood", "activities", "journal"};

    public static void main (String[] args)
    {
        System.out.println("=====");
        DataHandler tester = new DataHandler("jwb175");
        String[] activities = {"running", "jumping"};
        tester.setEntry(java.sql.Date.valueOf("2000-10-29"),  1,  activities, "hello hi hello");
        System.out.println(tester.getEntry(java.sql.Date.valueOf("2000-10-29")));
    }

    DataHandler (String id)
    {
        this.id = validateID(id);
        con = openConnection();
    }

    //Sets a tuple in the database
    //Returns null if an error occurs
    boolean setEntry (java.sql.Date date, Integer mood, String[] activitiesArr, String journal)
    {
        try
        {
            java.sql.Array activities = con.createArrayOf("String", activitiesArr);
            String query = "insert into 'entries' values ('" + id + "', '" + date + "', '" + mood + "', '" + activities.toString() + "', '" + journal + "')";
            con.createStatement().executeQuery(query); //TODO: error handling
        }
        catch (Exception e)
        {
           System.out.println("ERROR:\n" + e + "\n=====");
            return false;
        }
        return true;
    }

    //Returns an Entry object of mood, activities, and journal
    //Returns null if the entire tuple does not exist
    Entry getEntry (java.sql.Date date)
    {
        String query = "select mood, activities, journal from 'entries' WHERE id = '" + id + "' AND entryDate = '" + date + "'";
        Integer mood;
        String[] activities;
        String journal;

        try
        {
            ResultSet result = con.createStatement().executeQuery(query);
            if (result.next())
            {
                mood = result.getInt("mood");
                activities = (String[])result.getString("activities").replaceAll("[{ }]","").split(",");
                journal = result.getString("journal");
            }
            //Tuple does not exist
            else return null;
        }
        catch (Exception e)
        {
            System.out.println("ERROR:\n" + e + "\n=====");
            return null;
        }

        return new Entry(mood, activities, journal);
    }

    //Helper methods for individual entries
    Integer getMood (java.sql.Date date) { return getEntry(date).getMood(); }
    String[] getActivity (java.sql.Date date) { return getEntry(date).getActivities(); }
    String getJournal (java.sql.Date date) { return getEntry(date).getJournal(); }

    //Returns map between dates and Entry objects
    //Returns null if an error occurs
    Map<java.sql.Date, Entry> getMap ()
    {
        String query = "select entryDate, mood, activities, journal from 'entries' WHERE id = '" + id;
        Map<java.sql.Date, Entry> out = new HashMap<java.sql.Date, Entry>();

        try
        {
            ResultSet result = con.createStatement().executeQuery(query);
            while (result.next())
            {
                int mood = result.getInt("mood");
                String[] activities = (String[])result.getArray("activities").getArray();
                String journal = result.getString("journal");

                out.put(result.getDate("entryDate"), new Entry(mood, activities, journal));
            }
        }
        catch (Exception e)
        {
            System.out.println("ERROR:\n" + e + "\n=====");
            return null;
        }

        return out;
    }

    //Returns map between activities and their average mood
    //Returns null if an error occurs
    Map<String, Double> activityMoodMap ()
    {
        Map<java.sql.Date, Entry> entries = getMap();
        Map<String, Double> out = new HashMap<String, Double>();
        Map<String, Integer> totals = new HashMap<String, Integer>();

        //Iterate through tuples
        for (Entry entry : entries.values())
        {
            int mood = entry.getMood();
            String[] activities = entry.getActivities();
            //Iterate through all activities on that day
            for (String activity : activities)
            {
                double oldAvg = out.getOrDefault(activity, 0.0);
                int newTotal = totals.getOrDefault(activity, 0) + 1;
                //Compute average mood using previous average
                out.put(activity, oldAvg + ((mood - oldAvg) / newTotal));
                totals.put(activity, newTotal);
            }
        }

        return out;
    }
    
    //Opens and returns a connection to the database
    private Connection openConnection ()
    {
        try
        {
            //Load driver
            Class.forName("com.mysql.cj.jdbc.Driver");
                
            String server = "jdbc:mysql://localhost:3306/seniorProject?useUnicode=true&characterEncoding=utf-8&useSSL=false";
            String username = "root";
            String password = "Chen@197813551";

            //Connect to DB
            return DriverManager.getConnection(server, username, password);
        }
        catch (Exception e)
        {
           System.out.println("ERROR:\n" + e + "\n=====");
            return null;
        }
    }

    //Ensures the CaseID is formatted correctly
    private String validateID (String input)
    {
        //Make sure size is correct
        if (input.length() > 6 || input.length() < 4) { /* error */ }

        for (int i = 0; i < input.length(); i++)
        {
            //Make sure first three characters are letters
            if (i < 3 && !Character.isLetter(input.charAt(i)))
            {
                /* error */
            }
            else
            //Make sure last one to three characters are numbers
            if (!Character.isDigit(input.charAt(i)))
            {
                /* error */
            }
        }

        return input;
    }
}