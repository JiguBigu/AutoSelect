package com.auto.select.demo.algorithm.ABC;

import com.auto.select.demo.algorithm.util.AlgorithmResult;
import com.auto.select.demo.algorithm.util.Pro;


import java.util.ArrayList;
import java.util.Arrays;

public class Food extends AlgorithmResult {

    public Food() {
    }

    Food(int p1, int m1, int w, ArrayList<Pro> pronum) {
        p = new int[p1];
        m = new int[p1];
        machineNum = m1;
        workpieceNum = w;
        Pronum = pronum;
    }

    Food(int w, int ma, int t, int[] p1, int[] m1, ArrayList<Pro> pronum) {
        p = p1;
        m = m1;
        Pronum = pronum;
        workpieceNum = w;
        machineNum = ma;
        time = t;
    }


    public void Init(int Time[][]) {
        int ptr = 0;//P的下标
        int[] temptime = new int[machineNum];
        int[] Select = new int[workpieceNum];
        for (int g = 0; g < machineNum; g++) {
            temptime[g] = 0;
        }
        for (int g = 0; g < workpieceNum; g++) {
            Select[g] = 0;
        }
        for (int i = 0; i < p.length; i++) {
            m[i] = 0;
        }


        for (int o = 0; o < workpieceNum; o++) {
            int se;//工件序号

            do {
                se = (int) (Math.random() * 1000) % workpieceNum;
            } while (Select[se] == 1);

            Select[se] = 1;
            for (int i = 0; i < Pronum.get(se).num; i++) {
                //工序号，0开始
                int pronum = Pronum.get(se).start + i;
                int[] temp = new int[machineNum];
                for (int p1 = 0; p1 < machineNum; p1++) {
                    temp[p1] = temptime[p1];
                }
                for (int p1 = 0; p1 < machineNum; p1++) {
                    temp[p1] += Time[pronum][p1];
                }
                int minnum = 0;
                int min = temp[minnum];
                for (int p1 = 0; p1 < machineNum; p1++) {
                    if (min > temp[p1]) {
                        min = temp[p1];
                        minnum = p1;
                    }
                }

                temptime[minnum] += Time[pronum][minnum];

                m[pronum] = minnum;

            }
        }

        for (int i = 0; i < workpieceNum; i++) {
            for (int j = Pronum.get(i).start; j < Pronum.get(i).start + Pronum.get(i).num; j++) {
                p[ptr++] = i + 1;
            }
        }

        for (int i = 0; i < p.length / 2; i++) {
            int a, b, t;
            a = (int) (Math.random() * 1000) % p.length;
            b = (int) (Math.random() * 1000) % p.length;
            t = p[a];
            p[a] = p[b];
            p[b] = t;
        }

    }

    public void CalTime(int[][] Time) {
        //工序转码
        int[] TF = new int[p.length];
        int index, i;
        for (int j = 0; j < workpieceNum; j++) {
            index = 0;
            for (i = 0; i < p.length; i++) {
                if (p[i] == j + 1) {
                    TF[i] = Pronum.get(j).start + index;
                    index++;
                }
            }
        }

        int[] M_endtime = new int[machineNum];
        int[] O_start = new int[p.length];
        int[] O_end = new int[p.length];
        int[] J_end = new int[workpieceNum];
        for (int g = 0; g < p.length; g++) {
            O_start[g] = O_end[g] = 0;
        }
        for (int g = 0; g < machineNum; g++) {
            M_endtime[g] = 0;
        }
        for (int g = 0; g < workpieceNum; g++) {
            J_end[g] = 0;
        }


        for (i = 0; i < p.length; i++) {
            //当前工序序号
            int tf = TF[i];
            //获取机器号
            int tm = m[tf];
            //如越界，检查这里
            int Ttime = Time[tf][tm];
            //2019.8.02修改，删去-1
            int TJ = p[i] - 1;
            if (M_endtime[tm] > J_end[TJ]) {
                O_start[tf] = M_endtime[tm];
            } else {
                O_start[tf] = J_end[TJ];
            }
            O_end[tf] = O_start[tf] + Ttime;
            M_endtime[tm] = O_end[tf];
            J_end[TJ] = O_end[tf];
        }

        int maxtime = 0;
        for (i = 0; i < machineNum; i++) {
            if (M_endtime[i] > maxtime) {
                maxtime = M_endtime[i];
            }
        }
        time = maxtime;

    }

    public void NewIndividual(int[][] Time) {
        //机器变异
        int in = time;

        int a = (int) (Math.random() * 1000) % p.length;
        int min = 0, mintime = Time[a][min];
        for (int j = 1; j < machineNum; j++) {
            if (Time[a][j] < mintime) {
                min = j;
                mintime = Time[a][min];
            }
        }
        m[a] = min;


        a = (int) (Math.random() * 1000) % p.length;
        do {
            m[a] = (int) (Math.random() * 1000) % machineNum;
        } while (Time[a][m[a]] > 1000);

        //工序变异

        int b, t, c;
        int pos1, pos2;
        b = (int) (Math.random() * 1000) % p.length;
        c = (int) (Math.random() * 1000) % p.length;

        if (b > c) {
            t = b;
            b = c;
            c = t;
        }
        while (b < c) {
            t = p[b];
            p[b] = p[c];
            p[c] = t;
            b++;
            c--;
        }

        CalTime(Time);
        //System.out.println("in:"+in+",  out:"+time);
    }

    public void Swap() {
        int i, j, t;
        i = (int) (Math.random() * 1000) % p.length;
        j = (int) (Math.random() * 1000) % p.length;

        t = p[i];
        p[i] = p[j];
        p[j] = t;
    }

    public void Insert() {
        //将在位置pos1的值插到位置pos2
        int pos1, pos2;
        pos1 = (int) (Math.random() * 1000) % p.length;
        pos2 = (int) (Math.random() * 1000) % p.length;
        if (pos1 < pos2) {
            int value = p[pos1];
            for (int i = pos1; i < pos2; i++) p[i] = p[i + 1];
            p[pos2] = value;
        }
    }

    public void Reserse() {
        int start, end, t;
        do {
            start = (int) (Math.random() * 1000) % p.length;
            end = (int) (Math.random() * 1000) % p.length;
        } while (start >= end);

        for (int i = 0; i < end - start; i++) {
            t = p[start + i];
            p[start + i] = p[end - i];
            p[end - i] = t;
        }
    }

    public void LocalSearch(int[][] Time) {
        int i, j, min;

        int min_find = 5;
        int max_find = 10;
        int[] p1 = p.clone();
        int[] m1 = m.clone();
        int in;
        do {
            in = time;

            p = p1.clone();
            m = m1.clone();

            //最优变异
            int a = (int) (Math.random() * 1000) % p.length;
            min = 0;
            int mintime = Time[a][min];
            for (j = 1; j < machineNum; j++) {
                if (Time[a][j] < mintime) {
                    min = j;
                    mintime = Time[a][min];
                }
            }
            m[a] = min;

            //随机变异
            a = (int) (Math.random() * 1000) % p.length;
            do {
                m[a] = (int) (Math.random() * 1000) % machineNum;
            } while (Time[a][m[a]] > 1000);

            //工序变异
            Swap();
            Insert();
            Reserse();

            CalTime(Time);

            max_find--;
            if (time < in) {
                min_find--;
            } else if (time > in) {
                p = p1.clone();
                m = m1.clone();
                setTime(in);
            }
        } while (min_find > 0 && max_find > 0);
    }

    public Food clone() {
        Food food = new Food();
        food.setP(p);
        food.setM(m);
        food.setMachineNum(machineNum);
        food.setPronum(Pronum);
        food.setWorkpieceNum(workpieceNum);
        food.setTime(time);
        return food;
    }

    @Override
    public String toString() {
        return "Food{" +
                "time=" + time +
                ", p=" + Arrays.toString(p) +
                ", m=" + Arrays.toString(m) +
                ", workpieceNum=" + workpieceNum +
                ", machineNum=" + machineNum +
                ", Pronum=" + Pronum +
                '}';
    }
}
