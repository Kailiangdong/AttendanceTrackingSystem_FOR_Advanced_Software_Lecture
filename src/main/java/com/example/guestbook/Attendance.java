package com.example.guestbook;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Index
public class Attendance{
    @Id Long id;
    String token;
    String student_id;
    String tutorial_group_id;
    String week_id;
    boolean presented;
    Date date;

    public Attendance(){
        presented = false;
        date = new Date();
    }

    public Attendance(String token, String student_id,String tutorial_group_id,String week_id,boolean presented){
        this.token = token;
        this.student_id = student_id;
        this.tutorial_group_id = tutorial_group_id;
        this.week_id = week_id;
        this.presented = presented;
        date = new Date();
    }

    public String getAttandanceId(){
        return token;
    }

    public String getGroupId(){
        return tutorial_group_id;
    }
    public String getWeek(){
        return week_id;
    }
    public Boolean getPresent(){
        return presented;
    }

    public String getStudentId(){
        return student_id;
    }
}