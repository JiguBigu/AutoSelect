package com.auto.select.demo.algorithm.SA;

import com.auto.select.demo.algorithm.util.AlgorithmResult;

import java.util.concurrent.Callable;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/3/15 18:38
 */
public class SACallable implements Callable<AlgorithmResult> {
    private String filePath;

    public SACallable(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public AlgorithmResult call() throws Exception {
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
        return individual;
    }
}
