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
    private List<Boolean> s;
    private List<Boolean> m;
    private List<Boolean> t;
    private List<Boolean> w;
    private List<Boolean> th;

    public classroom(){



    }

    public classroom(String roomNum, long capacity, boolean interactive,boolean projector,
                     List<Boolean> s,List<Boolean> m,List<Boolean> t,List<Boolean> w,List<Boolean> th){

        this.roomNum = roomNum;
        this.capacity=capacity;
        this.interactive=interactive;
        this.projector=projector;
        this.s=s;
        this.m=m;
        this.t=t;
        this.w=w;
        this.th=th;

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
    public void setM(List<Boolean> m){
        this.m=m;
    }

    public void setS(List<Boolean> s){
        this.s=s;
    }

    public void setT(List<Boolean> t){
        this.t=t;
    }

    public void setTh(List<Boolean> th){
        this.th=th;
    }

    public void setW(List<Boolean> w){
        this.w=w;
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

    public List<Boolean> getS(){
        return s;
    }

  /*  public boolean getS(String index){
        int ind = Integer.parseInt(index);
        return s[ind];
    } */

    public List<Boolean> getM(){
        return m;
    }

    public List<Boolean> getT(){
        return t;
    }

    public List<Boolean> getW(){
        return w;
    }

    public List<Boolean> getTh(){
        return th;
    }


}

