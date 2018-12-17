package com.example.guestbook;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Index
public class Attendance{
    @Id public long id;
    private String attandance_id;
    private String student_id;
    private String tutorial_group_id;
    private String week_id;
    private boolean presented;

    public Attendance(){
        presented = false;
    }

    public Attendance(String attandance_id, String student_id,String tutorial_group_id,String week_id,boolean presented){
        this.attandance_id = attandance_id;
        this.student_id = student_id;
        this.tutorial_group_id = tutorial_group_id;
        this.week_id = week_id;
        this.presented = presented;
    }
}