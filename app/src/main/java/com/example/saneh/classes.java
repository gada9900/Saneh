package com.example.saneh;
import androidx.appcompat.app.AppCompatActivity;

import java.util.*;


public class classes {
    private String roomNum;
    private long capacity;
    private boolean interactive;
    private boolean projector;
    private List<Boolean> s , m , t , w ,th ;


    private classes(){}

    public classes(String roomNum ,long capacity, boolean interactive,boolean projector){
        this.roomNum=roomNum;
        this.capacity=capacity;
        this.interactive=interactive;
        this.projector=projector;
    }



    public classes(String roomNum ,long capacity, boolean interactive,boolean projector ,List<Boolean> s,List<Boolean> m ,List<Boolean> t , List<Boolean> w ,List<Boolean> th) {
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

    public List<Boolean> getT() {
        return t;
    }

    public List<Boolean> getS() {
        return s;
    }

    public void setS(List<Boolean> s) {
        this.s = s;
    }

    public List<Boolean> getM() {
        return m;
    }

    public List<Boolean> getW() {
        return w;
    }

    public void setW(List<Boolean> w) {
        this.w = w;
    }

    public void setM(List<Boolean> m) {
        this.m = m;
    }

    public List<Boolean> getTh() {
        return th;
    }

    public void setTh(List<Boolean> th) {
        this.th = th;
    }

    public void setT(List<Boolean> t) {
        this.t = t;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
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


}

