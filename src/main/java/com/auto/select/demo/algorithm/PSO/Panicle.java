package com.auto.select.demo.algorithm.PSO;

import com.auto.select.demo.algorithm.util.Pro;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Panicle {
    private int time;
    private int[] P;
    private int[] M;
    private int WorkpieceNum;
    private int MachineNum;
    private ArrayList<Pro>Pronum;

    private int[] pbest_P;
    private int[] pbest_M;
    private int p_best;

    double w=1.4
          ,c1
          ,c2;
    double[] V_P;
    double[] V_M;

    Panicle(int p, int m, int w, ArrayList<Pro>pronum)
    {
        P=new int[p];
        M=new int[p];
        MachineNum=m;
        WorkpieceNum=w;
        Pronum=pronum;
        V_P=new double[2];
        V_M=new double[2];
    }

    Panicle(int w, int ma, int t, int[] p, int[] m, ArrayList<Pro>pronum)
    {
        P=p;
        M=m;
        Pronum=pronum;
        WorkpieceNum=w;
        MachineNum=ma;
        time=t;
    }

    Panicle(){}

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
        CalTime(Time);
        p_best=time;

        for (int i = 0; i < V_P.length; i++)    V_P[i]=Math.random();
        for (int i = 0; i < V_M.length; i++)    V_M[i]=Math.random();

        pbest_M=M.clone();
        pbest_P=P.clone();

        c1=P.length;
        c2=P.length;

    }



    public void UpdataV(int g_best)
    {
        V_P[0]=c1*Math.random();
        V_P[1]=c2*Math.random();

        V_M[0]=c1*Math.random();
        V_M[1]=c2*Math.random();

    }


    public void UpdataX(int [][]Time,Panicle gbest)
    {
        //机器部分
        //先将pbest部分基因放在后部分，在将gbest部分基因放在前面
        double sum=0;
        int length=(int)Math.floor(V_M[0]);
        for(int i=M.length-length;i<M.length;i++){
            M[i]=pbest_M[i];
        }

        length=(int)Math.floor(V_M[1]);
        for (int i = 0; i < length; i++) {
            M[i]=gbest.M[i];
        }

        //工序部分
        length=(int)Math.floor(V_P[0]);
        for(int i=P.length-length;i<P.length;i++){
            P[i]=pbest_P[i];
        }
        length=(int)Math.floor(V_P[1]);
        for (int i = 0; i < length; i++) {
            P[i]=gbest.P[i];
        }

        Adjust(Time);

        /*for (int i = 0; i < P.length; i++) {
        System.out.print(P[i]+"     ");
        }*/

        CalTime(Time);
        LocalSearch(Time);

    }

    public void Adjust(int[][]Time)
    {
        //调整约束

        //机器部分
        for (int i = 0; i < M.length; i++) {
            if(Time[i][M[i]]>1000)
            {
                int min=0;
                for (int j = 0; j < Time[i].length; j++) {
                    if(Time[i][min]<Time[i][j]) min=j;
                }
                M[i]=min;
            }
        }
        /*System.out.print("P     ");
        for (int i = 0; i < P.length; i++) {
            System.out.print(P[i]+"     ");
        }*/

        //工序部分
        HashMap<Integer,Integer>Missing=new HashMap<Integer,Integer>();
        for (int i = 1; i < WorkpieceNum+1; i++) {
            int Number=Pronum.get(i-1).num;
            int sum=0;
            for (int j = 0; j < P.length; j++) {
                if(P[j]==i) sum++;
                if(sum==Number)
                {
                    for (int k = j+1; k < P.length; k++) {
                        if(P[k]==i) P[k]=-1;
                    }
                    break;
                }
                if(j==P.length-1&&sum<Number)
                {
                    Missing.put(i,Number-sum);
                }
            }
        }
        int sum=0;
        for (Map.Entry<Integer,Integer> entry : Missing.entrySet())
        {
            //System.out.println(entry.getKey()+"   <>  "+entry.getValue()+"   <>   "+Pronum.get(entry.getKey()-1).num);
            sum+=entry.getValue();
        }
        int count=0;
        for (int i = 0; i < P.length; i++) {
            if(P[i]==-1)    count++;
        }
       // System.out.println(sum+" "+count);




        int index=0;
        for (Map.Entry<Integer,Integer> entry : Missing.entrySet()) {
            int P_num=entry.getKey();
            int num=entry.getValue();
            //System.out.println(Pronum.get(P_num).num+"      "+num);
            for (int i = 0; i < num; i++) {
                while (index<P.length&&P[index]!=-1)    index++;
                if (index==P.length)    break;
                P[index]=P_num;
            }
        }

        for (int i = 0; i < P.length; i++) {
            if(P[i]==-1)
            {
                System.out.println("error============================================"+sum+"======="+count);
                for (Map.Entry<Integer,Integer> entry : Missing.entrySet())
                {
                    System.out.println(entry.getKey()+"   <>   "+entry.getValue());
                }
            }
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

        for (i = 0; i < P.length; i++)
        {
            int tf = TF[i];//当前工序序号
            int tm = M[tf];//获取机器号
            int Ttime = Time[tf][tm];//如越界，检查这里
            int TJ = P[i] - 1;
            //System.out.println(tm+"   "+TJ);
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

    public Panicle clone()
    {
        Panicle panicle=new Panicle();
        panicle.setC1(c1);
        panicle.setC2(c2);
        panicle.setM(M);
        panicle.setMachineNum(MachineNum);
        panicle.setP(P);
        panicle.setP_best(p_best);
        panicle.setPronum(Pronum);
        panicle.setWorkpieceNum(WorkpieceNum);
        panicle.setTime(time);
        panicle.setW(w);
        panicle.setV_M(V_M);
        panicle.setV_P(V_P);
        panicle.setPbest_M(pbest_M);
        panicle.setPbest_P(pbest_P);

        return panicle;
    }

    public void Updata_pbest()
    {
        if(time<p_best)
        {
            p_best=time;
            pbest_P=P.clone();
            pbest_M=M.clone();
        }
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

    public void setTime(int time) {
        this.time = time;
    }

    public void setP(int[] p) {
        P = p;
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

    public void setP_best(int p_best) {
        this.p_best = p_best;
    }

    public void setW(double w) {
        this.w = w;
    }

    public void setC1(double c1) {
        this.c1 = c1;
    }

    public void setC2(double c2) {
        this.c2 = c2;
    }

    public void setV_P(double[] v_P) {
        V_P = v_P;
    }

    public void setV_M(double[] v_M) {
        V_M = v_M;
    }

    public void setPbest_P(int[] pbest_P) {
        this.pbest_P = pbest_P;
    }

    public void setPbest_M(int[] pbest_M) {
        this.pbest_M = pbest_M;
    }

    @Override
    public String toString() {
        return "Panicle{" +
                "time=" + time +
                ", P=" + Arrays.toString(P) +
                ", M=" + Arrays.toString(M) +
                ", WorkpieceNum=" + WorkpieceNum +
                ", MachineNum=" + MachineNum +
                ", Pronum=" + Pronum +
                ", pbest_P=" + Arrays.toString(pbest_P) +
                ", pbest_M=" + Arrays.toString(pbest_M) +
                ", p_best=" + p_best +
                ", w=" + w +
                ", c1=" + c1 +
                ", c2=" + c2 +
                ", V_P=" + Arrays.toString(V_P) +
                ", V_M=" + Arrays.toString(V_M) +
                '}';
    }
}
