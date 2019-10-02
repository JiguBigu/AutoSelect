package com.auto.select.demo.algorithm.GA;


import com.auto.select.demo.algorithm.util.Pro;

import java.util.ArrayList;

public class Population {
    private double PC;
    private double PM;
    private int POPSIZE;
    private int[][]Time;

    private double GS=0.6;
    private double LS=0.3;
    private double RD=0.1;

    private Individual[] individuals;

    Population(double pc,double pm,int size,int[][]T,int p, int m, int w, ArrayList<Pro> pronum)
    {
        PC=pc;
        PM=pm;
        POPSIZE=size;
        individuals=new Individual[POPSIZE];
        Time=T;

        for (int i = 0; i < individuals.length; i++) {
            individuals[i]=new Individual(p,m,w,pronum);
        }
    }

    public void Init()
    {
        for (int i = 0; i < individuals.length; i++) {
            double s=Math.random();
            if(s<RD)    individuals[i].RD(Time);
            else if(s>1-LS) individuals[i].LS(Time);
            else individuals[i].GS(Time);
        }
    }

    public void Select()
    {
        Individual[] newPop=new Individual[POPSIZE];
        for (int i = 0; i < POPSIZE; i++) {
            int a,b,c,min;
            a = (int)(Math.random()*1000) % POPSIZE;
            b = (int)(Math.random()*1000) % POPSIZE;
            c = (int)(Math.random()*1000) % POPSIZE;

            min=individuals[a].getTime()<individuals[b].getTime()?a:b;
            min=individuals[min].getTime()<individuals[c].getTime()?min:c;

            newPop[i]=individuals[min].clone();
        }
        individuals=newPop;
    }


    public void Variations()
    {
        //精英不变异
        for (int i = 1; i < individuals.length; i++) {
            double s=Math.random();
            if(s<PM)    individuals[i].Variation(Time);
        }
    }


    public void Cross()
    {
        Individual best=FindBest();//精英保留

        for (int i = 0; i < individuals.length/2; i++) {
            double s=Math.random();
            if(s<PC)
            {
                int a,b;
                Individual p1,p2;
                a = (int)(Math.random()*1000) % POPSIZE;
                b = (int)(Math.random()*1000) % POPSIZE;
                p1=individuals[a];
                p2=individuals[b];


                int WorkpieceNum=p1.getWorkpieceNum();
                int ProcessNum=p1.getM().length;

                int[] Hash = new int[WorkpieceNum];//工件全集，1、0属于不同工件集
                for (int g = 0; g < WorkpieceNum; g++)	Hash[g] = 0;
                for (int p = 0; p < WorkpieceNum / 2 + 1; p++)	Hash[(int)(Math.random()*1000) % WorkpieceNum] = 1;//划分工序集

                int[] C1 = new int[ProcessNum];
                int[] C2 = new int[ProcessNum];
                for (int j = 0; j < ProcessNum; j++)
                {
                    if (Hash[p1.getP()[j]-1]!=0)
                        C1[j] = p1.getP()[j];
                    else					C1[j] = 0;

                    if (Hash[p2.getP()[j]-1]!=0)
                        C2[j] = p2.getP()[j];
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
                            if (Hash[p2.getP()[j]-1]==0)
                            {
                                index = j + 1;
                                C1[k] = p2.getP()[j];
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
                            if (Hash[p1.getP()[j]-1]==0)
                            {
                                index = j + 1;
                                C2[k] = p1.getP()[j];
                                break;
                            }
                        }
                    }
                }

                p1.setP(C1);
                p2.setP(C2);

                p1.CalTime(Time);
                p2.CalTime(Time);
            }
        }
        individuals[0]=best;
    }

    public Individual FindBest()
    {
        int min=0,mintime=individuals[min].getTime();
        for (int i = 1; i < individuals.length; i++) {
            if(individuals[i].getTime()<mintime)
            {
                mintime=individuals[i].getTime();
                min=i;
            }
        }
        return individuals[min].clone();
    }
}
