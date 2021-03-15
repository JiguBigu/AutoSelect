package com.auto.select.demo.algorithm.new_ga;



import com.auto.select.demo.algorithm.new_ga.util.FileRead;
import com.auto.select.demo.algorithm.new_ga.util.Mtime;
import com.auto.select.demo.algorithm.new_ga.util.Pro;

import java.util.ArrayList;


public class GA {
    String path;

    double PC;
    double PM;
    int POPSIZE;
    int G=200;

    int ProcessNum;//工序数
    int MachineNum;//机器数
    int WorkpieceNum;//工件数

    Answer[] Pop;//种群
    int[][] Time;//各工序加工时间
    public ArrayList<Pro> Pronum;
    public ArrayList<ArrayList<Mtime>>ProTime;

    public GA(String str) throws Exception {
        path=str;
        Creat();
        Python python=new Python(MachineNum,WorkpieceNum);
        python.Run();
        POPSIZE=python.POPSIZE;
        PC=python.pc;
        PM=python.pm;

        path=str;
        PC = python.pc;


        System.out.println(python.pc);
        System.out.println(python.pm);
        System.out.println(python.POPSIZE);
        System.out.println();
        System.out.println(python.effect);



        Pop=new Answer[POPSIZE];
        for (int i = 0; i < POPSIZE; i++) {
            Pop[i]=new Answer(ProcessNum);
        }
    }

    public void Run()
    {
        //Creat();
        InitPop();
        Print();
        for(int i=0;i<G;i++)
        {
            Select();
            Cross();
            Variation();
            CalTime();
        }
        Print();
    }

    void Creat()//读文件
    {
        FileRead fileRead=new FileRead();
        fileRead.Read("G:\\Brandimarte_Data\\Text\\mk02.txt");

        ProcessNum = fileRead.ProcessNum;
        MachineNum = fileRead.MachineNum;
        WorkpieceNum = fileRead.WorkpieceNum;
        Time = fileRead.Time;
        Pronum = fileRead.Pronum;
        ProTime = fileRead.ProTime;

    }

    void InitPop()//种群初始化
    {
        for (int i = 0; i < POPSIZE; i++) {
            RD(Pop[i]);
        }
        CalTime();
    }

    public void RD(Answer answer)
    {
        answer.M=new int[ProcessNum];
        answer.P=new int[ProcessNum];


        int ptr=0;
        for (int i = 0; i < WorkpieceNum; i++)
        {
            for (int j = Pronum.get(i).start; j < Pronum.get(i).start + Pronum.get(i).num; j++)
                answer.P[ptr++]=i + 1;
        }

        for (int i = 0; i < answer.P.length / 2; i++)
        {
            int a, b, t;
            a = (int)(Math.random()*1000) % answer.P.length;
            b = (int)(Math.random()*1000) % answer.P.length;
            t = answer.P[a];
            answer.P[a] = answer.P[b];
            answer.P[b] = t;
        }

        for (int i = 0; i < answer.P.length; i++)	answer.M[i]=0;
        for (int i = 0; i < answer.P.length; i++)
        {
            int M_Select;
            do {
                M_Select = (int)(Math.random()*1000) % MachineNum;
            } while (Time[i][M_Select] == Integer.MAX_VALUE/1000);
            answer.M[i] = M_Select;
        }
    }


    void Select() //锦标赛
    {
        Answer[] newpop=new Answer[POPSIZE];
        for (int i = 0; i < POPSIZE; i++)
        {
            int index;//三选一最佳个体下标
            int mintime;
            int a, b, c;
            a = (int)(Math.random()*1000) % POPSIZE;
            b = (int)(Math.random()*1000) % POPSIZE;
            c = (int)(Math.random()*1000) % POPSIZE;
            index = a;
            mintime = Pop[index].time;
            if (Pop[b].time < mintime)
            {
                index = b;
                mintime = Pop[index].time;
            }
            if (Pop[c].time < mintime)
            {
                index = c;
                mintime = Pop[index].time;
            }
            newpop[i] = Pop[index].clone();

		/*if (pop[i].time < bestpop.time)
		{
			bestpop = pop[i];
		}*/

        }
        for (int i = 0; i < POPSIZE; i++)	Pop[i] = newpop[i].clone();
        //pop[0] = bestpop;//精英选择，插在最前
    }

    void Cross()//交叉
    {

        for (int i = 0; i < POPSIZE / 2; i++)
        {
            double s = Math.random();
            if (s < PC)
            {
                int[] Hash = new int[WorkpieceNum];//工件全集，1、0属于不同工件集
                for (int g = 0; g < WorkpieceNum; g++)	Hash[g] = 0;
                for (int p = 0; p < WorkpieceNum / 2 + 1; p++)	Hash[(int)(Math.random()*1000) % WorkpieceNum] = 1;//划分工序集
                int a, b;//交叉个体
                a =(int)(Math.random()*1000) % POPSIZE;
                b = (int)(Math.random()*1000) % POPSIZE;
                int[] C1 = new int[ProcessNum];
                int[] C2 = new int[ProcessNum];
                for (int j = 0; j < ProcessNum; j++)
                {
                    if (Hash[Pop[a].P[j]-1]==1)	C1[j] = Pop[a].P[j];
                    else					C1[j] = 0;

                    if (Hash[Pop[b].P[j]-1]==1)	C2[j] = Pop[b].P[j];
                    else					C2[j] = 0;
                }
                int index;
                index = 0;
                for (int k = 0; k < ProcessNum; k++)
                {
                    if (C1[k]==0)
                    {
                        for (int j = index; j < ProcessNum; j++)
                        {
                            if (Hash[Pop[b].P[j]-1]==0)
                            {
                                index = j + 1;
                                C1[k] = Pop[b].P[j];
                                break;
                            }
                        }
                    }
                }

                index = 0;
                for (int k = 0; k < ProcessNum; k++)
                {
                    if (C2[k]==0)
                    {
                        for (int j = index; j < ProcessNum; j++)
                        {
                            if (Hash[Pop[a].P[j]-1]==0)
                            {
                                index = j + 1;
                                C2[k] = Pop[a].P[j];
                                break;
                            }
                        }
                    }
                }
                for (int j = 0; j < ProcessNum; j++)
                {
                    Pop[a].P[j] = C1[j];
                    Pop[b].P[j] = C2[j];
                }
            }
        }
        //pop[0] = bestpop;//保留最好个体不变
    }

    void Variation()//变异
    {
        //机器变异
        for (int i = 0; i < POPSIZE; i++)
        {
            double s = Math.random();
            if (s < PM)
            {
                //随机变异
                int a = (int)(Math.random()*1000) % ProcessNum;
                int b;
                do {
                    b = (int)(Math.random()*1000) % MachineNum;
                } while (Time[a][b] == Integer.MAX_VALUE/1000);
                Pop[i].M[a] = b;

                //最优变异
                int mintime,index;
                index=0;
                mintime=Time[a][index];
                a = (int)(Math.random()*1000) % ProcessNum;
                for (int j = 1; j < Time[a].length; j++) {
                    if(mintime>Time[a][j])
                    {
                        index=j;
                        mintime=Time[a][j];
                    }
                }
                Pop[i].M[a]=index;


            }
        }


        //工序变异
        for (int i = 0; i < POPSIZE; i++)//精英不变异
        {
            double s = Math.random();
            if (s < PM)
            {
                int a, b, t;
                a = (int)(Math.random()*1000) % ProcessNum;
                b = (int)(Math.random()*1000) % ProcessNum;
                t = Pop[i].P[a];
                Pop[i].P[a] = Pop[i].P[b];
                Pop[i].P[b] = t;
            }
        }
    }

    void CalTime()//计算各个个体的总时间
    {
        for (int i = 0; i < POPSIZE; i++) {
            CalPop(Pop[i]);
        }
    }
    void CalPop(Answer answer)//计算单个个体时间
    {
        //工序转码
        int[] TF=new int[answer.P.length];
        int index, i;
        for (int j = 0; j < WorkpieceNum; j++)
        {
            index = 0;
            for (i = 0; i < answer.P.length; i++)
            {
                if (answer.P[i] == j + 1)
                {
                    TF[i] = Pronum.get(j).start + index;
                    index++;
                }
            }
        }

        int[] M_endtime=new int[MachineNum];
        int[] O_start=new int[answer.P.length];
        int[] O_end=new int[answer.P.length];
        int[] J_end=new int[WorkpieceNum];
        for (int g = 0; g < answer.P.length; g++)	O_start[g] = O_end[g] = 0;
        for (int g = 0; g < MachineNum; g++)	M_endtime[g] = 0;
        for (int g = 0; g < WorkpieceNum; g++)	J_end[g] = 0;


        for (i = 0; i < answer.P.length; i++)
        {
            int tf = TF[i];//当前工序序号
            int tm = answer.M[tf];//获取机器号
            int Ttime = Time[tf][tm];//如越界，检查这里
            int TJ = answer.P[i]-1;//2019.8.02修改，删去-1
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
        answer.time = maxtime;
    }

    //void testPrint(Answer &pop);//转码输出检查结果
    //int GetPronum(Answer &pop, int a);
    void Print()//输出
    {
        System.out.println("time="+findBest().time);
    }

    public Answer findBest()
    {
        int time,index;
        time=Pop[0].time;
        index=0;
        for (int i = 1; i < POPSIZE; i++) {
            if(Pop[i].time<time)
            {
                time=Pop[i].time;
                index=i;
            }
        }
        return Pop[index];
    }


    public double getPC() {
        return PC;
    }

    public double getPM() {
        return PM;
    }

    public int getPOPSIZE() {
        return POPSIZE;
    }

    public String getConfigStr(){
        return "最佳优化算法：GA\n参数配置：\n种群规模  -  " + getPOPSIZE() + "    交叉概率  -  " + getPC() +
                "    变异概率  -  " + getPM();
    }

}
