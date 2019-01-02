package com.example.guestbook;

import java.util.Date;
import java.util.UUID;

import com.googlecode.objectify.annotation.Subclass;

@Subclass(index=true)
public class Tutor extends Person{
    String session_id;

    public Tutor(){
        this.date = new Date();
    }

    public Tutor(String firstName, String lastName, String email, String pwd){
        super(firstName, lastName, email, pwd);
        session_id = UUID.randomUUID().toString();
    }

    // public Long getSessionId(){
    //     return session_id;
    // }
}