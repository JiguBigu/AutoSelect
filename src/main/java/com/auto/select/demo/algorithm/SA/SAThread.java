package com.auto.select.demo.algorithm.SA;

import com.auto.select.demo.algorithm.util.Pro;

import java.util.ArrayList;

public class SAThread extends Thread{
    Individual best;

    private String filePath;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run()
    {
        double T=1000;
        FileRead fileRead=new FileRead();
        fileRead.Read(filePath);
        Individual individual=new Individual(fileRead.ProcessNum,fileRead.MachineNum,fileRead.WorkpieceNum,fileRead.Pronum);
        individual.Init(fileRead.Time);
        individual.CalTime(fileRead.Time);

        //System.out.println(individual.getTime());
        while (T>1)
        {
            for(int i=0;i<800;i++)
            {
                Individual newindividual
                        =new Individual(individual.getWorkpieceNum(),individual.getMachineNum(),individual.getTime(),individual.getP(),individual.getM(),individual.getPronum());
                newindividual.NewIndividual(fileRead.Time);
                if(newindividual.getTime()< individual.getTime()||newindividual.getTime()== individual.getTime())    individual=newindividual;
                else
                {
                    double delta=newindividual.getTime()-individual.getTime();
                    double number = Math.random();
                    if(number<Math.exp(-delta/T))  individual=newindividual;
                }
            }
            T*=0.95;
        }
        best=individual;
    }

    public Individual New(int p, int m, int w, ArrayList<Pro> pronum, int [][]Time)
    {
        Individual[] individuals=new Individual[3];
        for (int i = 0; i < individuals.length; i++) {
            individuals[i]=new Individual(p,m,w,pronum);
            individuals[i].Init(Time);
            individuals[i].CalTime(Time);
        }
        int min=0;
        int mintime=individuals[min].getTime();
        for (int i = 1; i < individuals.length; i++) {
            if(individuals[i].getTime()<mintime)
            {
                min=i;
                mintime=individuals[min].getTime();
            }
        }
        return individuals[min];
    }

    public Individual getBest() {
        return best;
    }
}
