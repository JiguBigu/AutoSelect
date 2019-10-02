package com.auto.select.demo.algorithm.ABC;

import com.auto.select.demo.algorithm.util.Pro;


import java.util.ArrayList;
import java.util.Arrays;

public class Food {
    private int time;
    private int[] P;
    private int[] M;
    private int WorkpieceNum;
    private int MachineNum;
    private ArrayList<Pro>Pronum;

    public Food(){}

    Food(int p, int m, int w, ArrayList<Pro>pronum)
    {
        P=new int[p];
        M=new int[p];
        MachineNum=m;
        WorkpieceNum=w;
        Pronum=pronum;
    }

    Food(int w, int ma, int t, int[] p, int[] m, ArrayList<Pro>pronum)
    {
        P=p;
        M=m;
        Pronum=pronum;
        WorkpieceNum=w;
        MachineNum=ma;
        time=t;
    }


    public void Init(int Time[][])
    {
        int ptr=0;//P的下标
        int[] temptime=new int[MachineNum];
        int[] Select=new int[WorkpieceNum];
        for (int g = 0; g < MachineNum; g++)	temptime[g] = 0;
        for (int g = 0; g < WorkpieceNum; g++)	Select[g] = 0;
        for (int i = 0; i < P.length; i++)	M[i]=0;




        for (int o = 0; o < WorkpieceNum; o++)
        {
            int se;//工件序号
            do {
                se = (int)(Math.random()*1000) % WorkpieceNum;
            } while (Select[se] == 1);
            Select[se] = 1;
            for (int i = 0; i < Pronum.get(se).num; i++)
            {
                int pronum = Pronum.get(se).start + i;//工序号，0开始
                int[] temp=new int[MachineNum];
                for (int p = 0; p < MachineNum; p++)	temp[p] = temptime[p];
                for (int p = 0; p < MachineNum; p++)	temp[p] += Time[pronum][p];
                int minnum = 0;
                int min = temp[minnum];
                for (int p = 0; p < MachineNum; p++)
                {
                    if (min > temp[p])
                    {
                        min = temp[p];
                        minnum = p;
                    }
                }

                temptime[minnum] += Time[pronum][minnum];

                M[pronum] = minnum;

            }
        }

        for (int i = 0; i < WorkpieceNum; i++)
        {
            for (int j = Pronum.get(i).start; j < Pronum.get(i).start + Pronum.get(i).num; j++)
                P[ptr++]=i + 1;
        }

        for (int i = 0; i < P.length / 2; i++)
        {
            int a, b, t;
            a = (int)(Math.random()*1000) % P.length;
            b = (int)(Math.random()*1000) % P.length;
            //System.out.println(a);
            t = P[a];
            P[a] = P[b];
            P[b] = t;
        }
        //for(int i=0;i<M.length;i++) System.out.println("M:"+M[i]);
        //for(int i=0;i<P.length;i++) System.out.println("P:"+P[i]);
    }

    public void CalTime(int [][] Time)
    {
        //工序转码
        int[] TF=new int[P.length];
        int index, i;
        for (int j = 0; j < WorkpieceNum; j++)
        {
            index = 0;
            for (i = 0; i < P.length; i++)
            {
                if (P[i] == j + 1)
                {
                    TF[i] = Pronum.get(j).start + index;
                    index++;
                }
            }
        }

        int[] M_endtime=new int[MachineNum];
        int[] O_start=new int[P.length];
        int[] O_end=new int[P.length];
        int[] J_end=new int[WorkpieceNum];
        for (int g = 0; g < P.length; g++)	O_start[g] = O_end[g] = 0;
        for (int g = 0; g < MachineNum; g++)	M_endtime[g] = 0;
        for (int g = 0; g < WorkpieceNum; g++)	J_end[g] = 0;

        //for(i=0;i<P.length;i++) System.out.println("w:"+P[i]);

        for (i = 0; i < P.length; i++)
        {
            int tf = TF[i];//当前工序序号
            int tm = M[tf];//获取机器号
            int Ttime = Time[tf][tm];//如越界，检查这里
            int TJ = P[i]-1;//2019.8.02修改，删去-1
            if (M_endtime[tm] > J_end[TJ])
            {
                O_start[tf] = M_endtime[tm];
            }
            else
            {
                O_start[tf] = J_end[TJ];
            }
            O_end[tf] = O_start[tf] + Ttime;
            M_endtime[tm] = O_end[tf];
            J_end[TJ] = O_end[tf];
        }

        int maxtime = 0;
        for (i = 0; i < MachineNum; i++)
        {
            if (M_endtime[i] > maxtime)	maxtime = M_endtime[i];
        }
        time = maxtime;

    }

    public void NewIndividual(int [][] Time)
    {
        //机器变异
        int in=time;

        int a = (int)(Math.random()*1000) % P.length;
        int min = 0, mintime = Time[a][min];
        for (int j = 1; j < MachineNum; j++)
        {
            if (Time[a][j] < mintime)
            {
                min = j;
                mintime = Time[a][min];
            }
        }
        M[a] = min;


        a = (int)(Math.random()*1000) % P.length;
        do {
            M[a]=(int)(Math.random()*1000) % MachineNum;
        }while (Time[a][M[a]]>1000);

        //工序变异

        int  b, t, c;
        int pos1, pos2;
        b = (int)(Math.random()*1000) % P.length;
        c = (int)(Math.random()*1000) % P.length;

        if(b>c)
        {
            t=b;
            b=c;
            c=t;
        }
        while (b<c)
        {
            t=P[b];
            P[b]=P[c];
            P[c]=t;
            b++;
            c--;
        }

        CalTime(Time);
        //System.out.println("in:"+in+",  out:"+time);
    }

    public void Swap()
    {
        int i,j,t;
        i=(int)(Math.random()*1000) % P.length;
        j=(int)(Math.random()*1000) % P.length;

        t=P[i];
        P[i]=P[j];
        P[j]=t;
    }

    public void Insert()
    {
        //将在位置pos1的值插到位置pos2
        int pos1,pos2;
        pos1=(int)(Math.random()*1000) % P.length;
        pos2=(int)(Math.random()*1000) % P.length;
        if(pos1<pos2)
        {
            int value=P[pos1];
            for(int i=pos1;i<pos2;i++)  P[i]=P[i+1];
            P[pos2]=value;
        }
    }

    public void Reserse()
    {
        int start,end,t;
        do{
            start=(int)(Math.random()*1000) % P.length;
            end=(int)(Math.random()*1000) % P.length;
        }while (start>=end);

        for (int i = 0; i < end-start; i++) {
            t=P[start+i];
            P[start+i]=P[end-i];
            P[end-i]=t;
        }
    }

    public void LocalSearch(int [][]Time)
    {
        int i,j,min;

        int min_find=5;
        int max_find=10;
        int []p=P.clone();
        int[]m=M.clone();
        int in;
        do{
            in=time;

            P=p.clone();
            M=m.clone();

            //最优变异
            int a = (int)(Math.random()*1000) % P.length;
            min = 0;
            int mintime = Time[a][min];
            for (j = 1; j < MachineNum; j++)
            {
                if (Time[a][j] < mintime)
                {
                    min = j;
                    mintime = Time[a][min];
                }
            }
            M[a] = min;

            //随机变异
            a = (int)(Math.random()*1000) % P.length;
            do {
                M[a]=(int)(Math.random()*1000) % MachineNum;
            }while (Time[a][M[a]]>1000);

            //工序变异
            Swap();
            Insert();
            Reserse();

            CalTime(Time);

            max_find--;
            if(time<in)
            {
                min_find--;
            }
            else if(time>in)
            {
                P=p.clone();
                M=m.clone();
                setTime(in);
            }
        }while (min_find>0&&max_find>0);
    }

    public Food clone()
    {
        Food food=new Food();
        food.setP(P);
        food.setM(M);
        food.setMachineNum(MachineNum);
        food.setPronum(Pronum);
        food.setWorkpieceNum(WorkpieceNum);
        food.setTime(time);
        return food;
    }

    public int getTime() {
        return time;
    }

    public int[] getP() {
        return P;
    }

    public int[] getM() {
        return M;
    }

    public int getWorkpieceNum() {
        return WorkpieceNum;
    }

    public int getMachineNum() {
        return MachineNum;
    }

    public ArrayList<Pro> getPronum() {
        return Pronum;
    }

    public void setP(int[] p) {
        P = p;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setM(int[] m) {
        M = m;
    }

    public void setWorkpieceNum(int workpieceNum) {
        WorkpieceNum = workpieceNum;
    }

    public void setMachineNum(int machineNum) {
        MachineNum = machineNum;
    }

    public void setPronum(ArrayList<Pro> pronum) {
        Pronum = pronum;
    }

    @Override
    public String toString() {
        return "Food{" +
                "time=" + time +
                ", P=" + Arrays.toString(P) +
                ", M=" + Arrays.toString(M) +
                ", WorkpieceNum=" + WorkpieceNum +
                ", MachineNum=" + MachineNum +
                ", Pronum=" + Pronum +
                '}';
    }
}
