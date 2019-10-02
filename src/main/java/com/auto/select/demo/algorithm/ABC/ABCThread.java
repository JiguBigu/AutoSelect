package com.auto.select.demo.algorithm.ABC;

public class ABCThread extends Thread{
    Food best;
    String filePath;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

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

            if(abc.best_time==time) change++;
            else {
                time=abc.best_time;
                change=0;
            }

            if(change>50)   abc.New();
            //System.out.println("时间:"+abc.best_time);
        }
        //System.out.println("时间:"+abc.best_time);
        best=abc.food[abc.bestnum];
    }
    /*public static void main(String[] args) {
        FileRead fileRead=new FileRead();
        fileRead.Read("G:\\Data\\FJSP\\mk01.txt");
        ABC abc=new ABC(fileRead.ProcessNum,fileRead.MachineNum,fileRead.WorkpieceNum,fileRead.Pronum,fileRead.Time);
        int change=0,time=0;
        abc.Init();
        for(int i=0;i<1000;i++)
        {
            abc.EmployedBees();
            abc.OnlookerBees();

            if(abc.best_time==time) change++;
            else {
                time=abc.best_time;
                change=0;
            }

            if(change>50)   abc.New();
            //System.out.println("时间:"+abc.best_time);
        }
        System.out.println("时间:"+abc.best_time);
    }*/

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
