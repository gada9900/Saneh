package com.example.saneh;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class classroom {

    private String roomNum;
    private long capacity;
    private boolean interactive;
    private boolean projector;
    private List<Boolean> sunday;
    private List<Boolean> monday;
    private List<Boolean> tuesday;
    private List<Boolean> wednesday;
    private List<Boolean> thursday;

    public classroom(){



    }

    public classroom(String roomNum, long capacity, boolean interactive,boolean projector,
                     List<Boolean> sunday,List<Boolean> monday,List<Boolean> tuesday,List<Boolean> wednesday,List<Boolean> thursday){

        this.roomNum = roomNum;
        this.capacity=capacity;
        this.interactive=interactive;
        this.projector=projector;
        this.sunday=sunday;
        this.monday=monday;
        this.tuesday=tuesday;
        this.wednesday=wednesday;
        this.thursday=thursday;

    }
    //set

    public void setRoomNum(String roomNum){
        this.roomNum = roomNum;
    }

    public void setCapacity(long capacity){
        this.capacity=capacity;
    }

    public void setProjector(boolean projector){
        this.projector=projector;
    }

    public void setInteractive(boolean interactive){
        this.interactive=interactive;
    }
    public void setMonday(List<Boolean> monday){
        this.monday=monday;
    }

    public void setSunday(List<Boolean> sunday){
        this.sunday=sunday;
    }

    public void setTuesday(List<Boolean> tuesday){
        this.tuesday=tuesday;
    }

    public void setThursday(List<Boolean> thursday){
        this.thursday=thursday;
    }

    public void setW(List<Boolean> wednesday){
        this.wednesday=wednesday;
    }
    //get
    public String getRoomNum(){
        return roomNum;
    }

    public long getCapacity(){
        return capacity;
    }

    public boolean isInteractive(){
        return  interactive;
    }

    public boolean isProjector(){
        return projector;
    }

    public List<Boolean> getSunday(){
        return sunday;
    }

    public List<Boolean> getMonday(){
        return monday;
    }

    public List<Boolean> getTuesday(){
        return tuesday;
    }

    public List<Boolean> getWednesday(){
        return wednesday;
    }

    public List<Boolean> getThursday(){
        return thursday;
    }


}

