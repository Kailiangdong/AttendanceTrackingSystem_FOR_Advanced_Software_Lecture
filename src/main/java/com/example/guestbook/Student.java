package com.example.guestbook;

import java.util.Date;
import java.util.UUID;

import com.googlecode.objectify.annotation.Subclass;

@Subclass(index = true)
public class Student extends Person {

    int group;
    String[] tokens;

    public Student(String firstName, String lastName, String email, String pwd, int group) {
        super(firstName, lastName, email, pwd);
        this.group = group;
        tokens = new String[11];
        for (int i = 0; i < 11; i++) {
            // Generates random token id
            String uniqueID = UUID.randomUUID().toString();
            boolean same = false;
            for(int j = 0; j < i; j++){
                if(uniqueID.equals(tokens[j])){
                    same = true;
                    i--;
                    break;
                }
            }
            if(!same) tokens[i] = uniqueID;
        }
    }

    public Student() {
        this.date = new Date();
    }

    public String[] getTokens(){
        return tokens;
    }

    public String getToken(int week){
        return tokens[week];
    }

    public int getGroup(){
        return group;
    }

    public void setGroup(int group){
        this.group = group;
    }

    public boolean validateToken(String token, int week){
        return token.equals(tokens[week]);
    }
}