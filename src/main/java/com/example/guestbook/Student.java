package com.example.guestbook;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Index
public class Student{
    @Id public Long id;
    public String nickname;
    public String groupname;
    Date date;

    public Student(String name, String groupName){
        this.nickname = name;
        this.groupname = groupName;
    }

    public Student(){
        this.date = new Date();
    }
}