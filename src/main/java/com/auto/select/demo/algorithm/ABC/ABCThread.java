package com.auto.select.demo.algorithm.ABC;

public class ABCThread extends Thread{
    private Food best;
    private String filePath;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run()
    {
        FileRead fileRead=new FileRead();
        fileRead.Read(filePath);
        ABC abc=new ABC(fileRead.ProcessNum,fileRead.MachineNum,fileRead.WorkpieceNum,fileRead.Pronum,fileRead.Time);
        int change=0,time=0;
        abc.Init();

        for(int i=0;i<1000;i++)
        {
            abc.EmployedBees();
            abc.OnlookerBees();

            if(abc.getBestTime()==time){
                change++;
            }
            else {
                time=abc.getBestTime();
                change=0;
            }

            if(change>50){
                abc.New();
            }
        }
        Food[] foods = abc.getFood();
        best = foods[abc.getBestNum()];
    }

    public Food getBest() {
        return best;
    }

    @Override
    public String toString() {
        return "ABCThread{" +
                "best=" + best +
                '}';
    }
}
