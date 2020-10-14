package com.example.saneh;
import androidx.appcompat.app.AppCompatActivity;

import java.util.*;


public class reservations {
    private String classID;
    private String date;
    private String time;
    private String roomType;
    private boolean confirmed;


    private reservations(){}

    public reservations(String classID, String date, String time, String roomType, boolean confirmed){
        this.classID = classID;
        this.date = date;
        this.time =time;
        this.roomType = roomType;
        this.confirmed = confirmed;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}

