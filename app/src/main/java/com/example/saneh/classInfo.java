package com.example.saneh;
import androidx.appcompat.app.AppCompatActivity;

import java.util.*;


public class classInfo {
    //private String name;
    private long capacity;
    private boolean interactive;
    private boolean projector;
    //boolean[][] schedule = new boolean[5][7];

    private classInfo(){}

    private classInfo(long capacity, boolean interactive, boolean projector){
        this.capacity = capacity;
        this.interactive = interactive;
        this.projector = projector;

    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public boolean isInteractive() {
        return interactive;
    }

    public void setInteractive(boolean interactive) {
        this.interactive = interactive;
    }

    public boolean isProjector() {
        return projector;
    }

    public void setProjector(boolean projector) {
        this.projector = projector;
    }

    /*public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }*/
}
