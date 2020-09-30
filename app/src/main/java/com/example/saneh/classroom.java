package com.example.saneh;

import java.util.List;
import java.util.Objects;

public class classroom {

    private String roomNum;
    private int capacity;
    private boolean interactive;
    private boolean projector;
    private boolean[] s;
    private boolean[] m;
    private boolean[] t;
    private boolean[] w;
    private boolean[] th;

    public classroom(String roomNum, int capacity, boolean interactive,boolean projector,
                     boolean[] s,boolean[] m,boolean[] t,boolean[] w,boolean[]th){

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

    public void setCapacity(int capacity){
      this.capacity=capacity;
    }

    public void setProjector(boolean projector){
        this.projector=projector;
    }

    public void setInteractive(boolean interactive){
        this.interactive=interactive;
    }
    public void setM(boolean[] m){
        this.m=m;
    }

    public void setS(boolean[] s){
        this.s=s;
    }

    public void setT(boolean[] t){
        this.t=t;
    }

    public void setTH(boolean[] th){
        this.th=th;
    }

    public void setW(boolean[] w){
        this.w=w;
    }
    //get
    public String getRoomNum(){
        return roomNum;
    }

    public int getCapacity(){
        return capacity;
    }

    public boolean isInteractive(){
        return  interactive;
    }

    public boolean isProjector(){
        return projector;
    }

    public boolean[] getS(){
        return s;
    }

    public boolean[] getM(){
        return m;
    }

    public boolean[] getT(){
        return t;
    }

    public boolean[] getW(){
        return w;
    }

    public boolean[] getTH(){
        return th;
    }

}
