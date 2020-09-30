package com.example.saneh;
import androidx.appcompat.app.AppCompatActivity;

import java.util.*;


public class classes {
    private String roomNum;
    private int capacity;
    private boolean interactive;
    private boolean projector;
    private boolean[] s ;
    private boolean[] m ;
    private boolean[] t ;
    private boolean[] w ;
    private boolean[] th;

    private classes(){}

    public classes(String roomNum ,int capacity, boolean interactive,boolean projector ,boolean[] s,boolean[] m ,boolean[] t , boolean[] w ,boolean[] th) {
        this.roomNum=roomNum;
        this.capacity=capacity;
        this.interactive=interactive;
        this.projector=projector;
        this.s = s;
        this.m=m;
        this.t=t;
        this.w=w;
        this.th=th;
    }

    public boolean[] getT() {
        return t;
    }

    public boolean[] getS() {
        return s;
    }

    public void setS(boolean[] s) {
        this.s = s;
    }

    public boolean[] getM() {
        return m;
    }

    public boolean[] getW() {
        return w;
    }

    public void setW(boolean[] w) {
        this.w = w;
    }

    public void setM(boolean[] m) {
        this.m = m;
    }

    public boolean[] getTh() {
        return th;
    }

    public void setTh(boolean[] th) {
        this.th = th;
    }

    public void setT(boolean[] t) {
        this.t = t;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
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


}