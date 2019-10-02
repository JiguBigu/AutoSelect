package com.auto.select.demo.algorithm.GA;



public class GAThread extends Thread{
    Individual best;
    String filePath;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void run()
    {
        double PC=0.8;
        double PM=0.1;
        int POPSIZE=200;
        int G=500;

        FileRead fileRead=new FileRead();
        fileRead.Read(filePath);
        Population population=new Population(PC,PM,POPSIZE,fileRead.Time,fileRead.ProcessNum,fileRead.MachineNum,fileRead.WorkpieceNum,fileRead.Pronum);
        population.Init();

        for (int i = 0; i < G; i++) {
            population.Select();
            population.Cross();
            population.Variations();
        }
        best=population.FindBest();
    }

    public Individual getBest() {
        return best;
    }
}
