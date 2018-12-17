package com.example.guestbook;

import com.google.appengine.repackaged.org.joda.time.LocalDate;

public class Group{

    private static String[] firstDates = {
       "2018-11-05",
       "2018-11-05",
       "2018-11-05",
       "2018-11-06",
       "2018-11-07",
       "2018-11-07"
    };

    private static String[] times = {
        "09:00",
        "14:15",
        "16:15",
        "15:00",
        "10:00",
        "12:00"
     };

    private static String[] places = {
        "01.11.018, Seminarraum (5611.01.018)",
        "01.11.018, Seminarraum (5611.01.018)",
        "01.11.018, Seminarraum (5611.01.018)",
        "01.11.018, Seminarraum (5611.01.018)",
        "00.11.038, Forsoft/Seminarraum (5611.EG.038)",
        "01.11.018, Seminarraum (5611.01.018)",
    };

    private static String[] tutors = {
        "Ahmed Elnaggar",
        "Martin Kleehaus",
        "Martin Kleehaus",
        "Ahmed Elnaggar",
        "Patrick Holl",
        "Patrick Holl"
    };
    
    public static String getGroupInfos(String groupName){
        String s = "";
        int index = groupName.charAt(6) - 49;

        for(int i = 0; i < 11; i++){
            if(i > 6)
                s += "<p>" + LocalDate.parse(firstDates[index]).plusWeeks(i + 2).toString() + "\t" + times[index] + "\t" + places[index] + "\t" + tutors[index] + "<p>\n";
            else
                s += "<p>" + LocalDate.parse(firstDates[index]).plusWeeks(i).toString() + "\t" + times[index] + "\t" + places[index] + "\t" + tutors[index] + "<p>\n";
        }

        return s;
    }
}