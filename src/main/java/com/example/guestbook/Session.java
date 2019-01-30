package com.example.guestbook;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Index
public class Session {
    @Id
    Long id;
    String student_id;
    Date expired_data;

    public Session() {
        LocalDateTime dateTime = LocalDateTime.now().plus(Duration.of(90, ChronoUnit.MINUTES));
        this.expired_data = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public Session(String student_id) {
        this.student_id = student_id;
        LocalDateTime dateTime = LocalDateTime.now().plus(Duration.of(90, ChronoUnit.MINUTES));
        this.expired_data = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * @return the student_id
     */
    public String getStudent_id() {
        return student_id;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id.toString();
    }
}