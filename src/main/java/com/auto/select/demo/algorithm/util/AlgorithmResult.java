package com.auto.select.demo.algorithm.util;

import java.util.ArrayList;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/3/15 16:22
 */
public class AlgorithmResult {
    protected int time;
    protected int[] p;
    protected int[] m;
    protected int workpieceNum;
    protected int machineNum;
    protected ArrayList<Pro> Pronum;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int[] getP() {
        return p;
    }

    public void setP(int[] p) {
        this.p = p;
    }

    public int[] getM() {
        return m;
    }

    public void setM(int[] m) {
        this.m = m;
    }

    public int getWorkpieceNum() {
        return workpieceNum;
    }

    public void setWorkpieceNum(int workpieceNum) {
        this.workpieceNum = workpieceNum;
    }

    public int getMachineNum() {
        return machineNum;
    }

    public void setMachineNum(int machineNum) {
        this.machineNum = machineNum;
    }

    public ArrayList<Pro> getPronum() {
        return Pronum;
    }

    public void setPronum(ArrayList<Pro> pronum) {
        Pronum = pronum;
    }
}
