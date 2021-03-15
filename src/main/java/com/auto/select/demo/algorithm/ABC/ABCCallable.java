package com.auto.select.demo.algorithm.ABC;

import com.auto.select.demo.algorithm.util.AlgorithmResult;

import java.util.concurrent.Callable;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/3/15 18:33
 */
public class ABCCallable implements Callable<AlgorithmResult> {
    private String filePath;

    public ABCCallable(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public AlgorithmResult call() throws Exception {
        FileRead fileRead = new FileRead();
        fileRead.Read(filePath);
        ABC abc = new ABC(fileRead.ProcessNum, fileRead.MachineNum, fileRead.WorkpieceNum, fileRead.Pronum, fileRead.Time);
        int change = 0, time = 0;
        abc.Init();

        for (int i = 0; i < 1000; i++) {
            abc.EmployedBees();
            abc.OnlookerBees();

            if (abc.getBestTime() == time) {
                change++;
            } else {
                time = abc.getBestTime();
                change = 0;
            }

            if (change > 50) {
                abc.New();
            }
        }
        Food[] foods = abc.getFood();
        return foods[abc.getBestNum()];
    }
}
