package com.example.guestbook;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Index
public class Person{
    @Id Long id;
    String firstName;
    String lastName;
    String email;
    String pwd;
    Date date;

    public Person(){
        this.date = new Date();
    }

    public Person(String firstName, String lastName, String email, String pwd){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.pwd = pwd;
        this.date = new Date();
    }

    public String getId(){
        return id.toString();
    }

    public boolean validatePwd(String pwd){
        return pwd.equals(this.pwd);
    }
}