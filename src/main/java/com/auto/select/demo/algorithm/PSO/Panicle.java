package com.auto.select.demo.algorithm.PSO;

import com.auto.select.demo.algorithm.util.AlgorithmResult;
import com.auto.select.demo.algorithm.util.Pro;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Panicle extends AlgorithmResult {
    private int[] pbest_P;
    private int[] pbest_M;
    private int p_best;

    double w=1.4
          ,c1
          ,c2;
    double[] V_P;
    double[] V_M;

    Panicle(int p1, int m1, int w, ArrayList<Pro>pronum)
    {
        p=new int[p1];
        m=new int[p1];
        machineNum=m1;
        workpieceNum=w;
        Pronum=pronum;
        V_P=new double[2];
        V_M=new double[2];
    }

    Panicle(int w, int ma, int t, int[] p1, int[] m1, ArrayList<Pro>pronum)
    {
        p=p1;
        m=m1;
        Pronum=pronum;
        workpieceNum=w;
        machineNum=ma;
        time=t;
    }

    Panicle(){}

    public void Init(int Time[][])
    {
        int ptr=0;//P的下标
        int[] temptime=new int[machineNum];
        int[] Select=new int[workpieceNum];
        for (int g = 0; g < machineNum; g++)	temptime[g] = 0;
        for (int g = 0; g < workpieceNum; g++)	Select[g] = 0;
        for (int i = 0; i < p.length; i++)	m[i]=0;




        for (int o = 0; o < workpieceNum; o++)
        {
            int se;//工件序号
            do {
                se = (int)(Math.random()*1000) % workpieceNum;
            } while (Select[se] == 1);
            Select[se] = 1;
            for (int i = 0; i < Pronum.get(se).num; i++)
            {
                int pronum = Pronum.get(se).start + i;//工序号，0开始
                int[] temp=new int[machineNum];
                for (int p1 = 0; p1 < machineNum; p1++)	temp[p1] = temptime[p1];
                for (int p1 = 0; p1 < machineNum; p1++)	temp[p1] += Time[pronum][p1];
                int minnum = 0;
                int min = temp[minnum];
                for (int p1 = 0; p1 < machineNum; p1++)
                {
                    if (min > temp[p1])
                    {
                        min = temp[p1];
                        minnum = p1;
                    }
                }

                temptime[minnum] += Time[pronum][minnum];

                m[pronum] = minnum;

            }
        }

        for (int i = 0; i < workpieceNum; i++)
        {
            for (int j = Pronum.get(i).start; j < Pronum.get(i).start + Pronum.get(i).num; j++)
                p[ptr++]=i + 1;
        }

        for (int i = 0; i < p.length / 2; i++)
        {
            int a, b, t;
            a = (int)(Math.random()*1000) % p.length;
            b = (int)(Math.random()*1000) % p.length;
            //System.out.println(a);
            t = p[a];
            p[a] = p[b];
            p[b] = t;
        }
        CalTime(Time);
        p_best=time;

        for (int i = 0; i < V_P.length; i++)    V_P[i]=Math.random();
        for (int i = 0; i < V_M.length; i++)    V_M[i]=Math.random();

        pbest_M=m.clone();
        pbest_P=p.clone();

        c1=p.length;
        c2=p.length;

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
        for(int i=m.length-length;i<m.length;i++){
            m[i]=pbest_M[i];
        }

        length=(int)Math.floor(V_M[1]);
        for (int i = 0; i < length; i++) {
            m[i]=gbest.m[i];
        }

        //工序部分
        length=(int)Math.floor(V_P[0]);
        for(int i=p.length-length;i<p.length;i++){
            p[i]=pbest_P[i];
        }
        length=(int)Math.floor(V_P[1]);
        for (int i = 0; i < length; i++) {
            p[i]=gbest.p[i];
        }

        Adjust(Time);

        /*for (int i = 0; i < p.length; i++) {
        System.out.print(p[i]+"     ");
        }*/

        CalTime(Time);
        LocalSearch(Time);

    }

    public void Adjust(int[][]Time)
    {
        //调整约束

        //机器部分
        for (int i = 0; i < m.length; i++) {
            if(Time[i][m[i]]>1000)
            {
                int min=0;
                for (int j = 0; j < Time[i].length; j++) {
                    if(Time[i][min]<Time[i][j]) min=j;
                }
                m[i]=min;
            }
        }
        /*System.out.print("p     ");
        for (int i = 0; i < p.length; i++) {
            System.out.print(p[i]+"     ");
        }*/

        //工序部分
        HashMap<Integer,Integer>Missing=new HashMap<Integer,Integer>();
        for (int i = 1; i < workpieceNum+1; i++) {
            int Number=Pronum.get(i-1).num;
            int sum=0;
            for (int j = 0; j < p.length; j++) {
                if(p[j]==i) sum++;
                if(sum==Number)
                {
                    for (int k = j+1; k < p.length; k++) {
                        if(p[k]==i) p[k]=-1;
                    }
                    break;
                }
                if(j==p.length-1&&sum<Number)
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
        for (int i = 0; i < p.length; i++) {
            if(p[i]==-1)    count++;
        }
       // System.out.println(sum+" "+count);




        int index=0;
        for (Map.Entry<Integer,Integer> entry : Missing.entrySet()) {
            int P_num=entry.getKey();
            int num=entry.getValue();
            //System.out.println(Pronum.get(P_num).num+"      "+num);
            for (int i = 0; i < num; i++) {
                while (index<p.length&&p[index]!=-1)    index++;
                if (index==p.length)    break;
                p[index]=P_num;
            }
        }

        for (int i = 0; i < p.length; i++) {
            if(p[i]==-1)
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
        int []p1=p.clone();
        int[]m1=m.clone();
        int in;
        do{
            in=time;

            p=p1.clone();
            m=m1.clone();

            //最优变异
            int a = (int)(Math.random()*1000) % p.length;
            min = 0;
            int mintime = Time[a][min];
            for (j = 1; j < machineNum; j++)
            {
                if (Time[a][j] < mintime)
                {
                    min = j;
                    mintime = Time[a][min];
                }
            }
            m[a] = min;

            //随机变异
            a = (int)(Math.random()*1000) % p.length;
            do {
                m[a]=(int)(Math.random()*1000) % machineNum;
            }while (Time[a][m[a]]>1000);

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
                p=p1.clone();
                m=m1.clone();
                setTime(in);
            }
        }while (min_find>0&&max_find>0);
    }


    public void Swap()
    {
        int i,j,t;
        i=(int)(Math.random()*1000) % p.length;
        j=(int)(Math.random()*1000) % p.length;

        t=p[i];
        p[i]=p[j];
        p[j]=t;
    }

    public void Insert()
    {
        //将在位置pos1的值插到位置pos2
        int pos1,pos2;
        pos1=(int)(Math.random()*1000) % p.length;
        pos2=(int)(Math.random()*1000) % p.length;
        if(pos1<pos2)
        {
            int value=p[pos1];
            for(int i=pos1;i<pos2;i++)  p[i]=p[i+1];
            p[pos2]=value;
        }
    }

    public void Reserse()
    {
        int start,end,t;
        do{
            start=(int)(Math.random()*1000) % p.length;
            end=(int)(Math.random()*1000) % p.length;
        }while (start>=end);

        for (int i = 0; i < end-start; i++) {
            t=p[start+i];
            p[start+i]=p[end-i];
            p[end-i]=t;
        }
    }

    public void CalTime(int [][] Time)
    {
        //工序转码
        int[] TF=new int[p.length];
        int index, i;
        for (int j = 0; j < workpieceNum; j++)
        {
            index = 0;
            for (i = 0; i < p.length; i++)
            {
                if (p[i] == j + 1)
                {
                    TF[i] = Pronum.get(j).start + index;
                    index++;
                }
            }
        }

        int[] M_endtime=new int[machineNum];
        int[] O_start=new int[p.length];
        int[] O_end=new int[p.length];
        int[] J_end=new int[workpieceNum];
        for (int g = 0; g < p.length; g++)	O_start[g] = O_end[g] = 0;
        for (int g = 0; g < machineNum; g++)	M_endtime[g] = 0;
        for (int g = 0; g < workpieceNum; g++)	J_end[g] = 0;

        for (i = 0; i < p.length; i++)
        {
            int tf = TF[i];//当前工序序号
            int tm = m[tf];//获取机器号
            int Ttime = Time[tf][tm];//如越界，检查这里
            int TJ = p[i] - 1;
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
        for (i = 0; i < machineNum; i++)
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
        panicle.setM(m);
        panicle.setMachineNum(machineNum);
        panicle.setP(p);
        panicle.setP_best(p_best);
        panicle.setPronum(Pronum);
        panicle.setWorkpieceNum(workpieceNum);
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
            pbest_P=p.clone();
            pbest_M=m.clone();
        }
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
                ", p=" + Arrays.toString(p) +
                ", m=" + Arrays.toString(m) +
                ", workpieceNum=" + workpieceNum +
                ", machineNum=" + machineNum +
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
