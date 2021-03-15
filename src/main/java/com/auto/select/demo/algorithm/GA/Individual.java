package com.auto.select.demo.algorithm.GA;



import com.auto.select.demo.algorithm.util.AlgorithmResult;
import com.auto.select.demo.algorithm.util.Pro;

import java.util.ArrayList;

public class Individual extends AlgorithmResult {

    Individual(int p1, int m1, int w, ArrayList<Pro>pronum)
    {
        p=new int[p1];
        m=new int[p1];
        machineNum=m1;
        workpieceNum=w;
        Pronum=pronum;
    }


    Individual(){}


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
            t = p[a];
            p[a] = p[b];
            p[b] = t;
        }
        CalTime(Time);
    }

    public void GS(int[][] Time)
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
            t = p[a];
            p[a] = p[b];
            p[b] = t;
        }
        CalTime(Time);
    }

    public void LS(int[][] Time)
    {
        int ptr=0;//P的下标
        int[] temptime = new int[machineNum];
        for (int i = 0; i < p.length; i++)	m[i]=0;

        for (int o = 0; o < workpieceNum; o++)
        {
            for (int g = 0; g < machineNum; g++)	temptime[g] = 0;
            int se=o;//工件序号
            for (int i = 0; i < Pronum.get(se).num; i++)
            {
                int pronum = Pronum.get(se).start + i;//工序号，0开始
                int[] temp = new int[machineNum];
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
            t = p[a];
            p[a] = p[b];
            p[b] = t;
        }
        CalTime(Time);
    }

    public void RD(int[][] Time)
    {
        int ptr=0;
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
            t = p[a];
            p[a] = p[b];
            p[b] = t;
        }

        for (int i = 0; i < p.length; i++)	m[i]=0;
        for (int i = 0; i < p.length; i++)
        {
            int M_Select;
            do {
                M_Select = (int)(Math.random()*1000) % machineNum;
            } while (Time[i][M_Select] == Integer.MAX_VALUE/1000);
            m[i] = M_Select;
        }
        CalTime(Time);
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
            int TJ = p[i]-1;//2019.8.02修改，删去-1
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

    public void Variation(int [][]Time)
    {

            LocalSearch(Time);
    }

    public void LocalSearch(int [][]Time)
    {
        int i,j,min;

        int min_find=3;
        int max_find=5;
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

    public Individual clone()
    {
        Individual individual=new Individual();
        individual.setM(m);
        individual.setMachineNum(machineNum);
        individual.setP(p);
        individual.setPronum(Pronum);
        individual.setWorkpieceNum(workpieceNum);
        individual.setTime(time);

        return individual;
    }


}
