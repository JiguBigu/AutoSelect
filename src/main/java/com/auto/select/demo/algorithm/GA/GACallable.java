package com.auto.select.demo.algorithm.GA;

import com.auto.select.demo.algorithm.util.AlgorithmResult;

import java.util.concurrent.Callable;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/3/15 18:41
 */
public class GACallable implements Callable<AlgorithmResult> {
    private String filePath;

    public GACallable(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public AlgorithmResult call() throws Exception {
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
        return population.FindBest();
    }
}
