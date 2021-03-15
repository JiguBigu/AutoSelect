package com.auto.select.demo.algorithm.PSO;

import com.auto.select.demo.algorithm.util.AlgorithmResult;

import java.util.concurrent.Callable;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/3/15 18:36
 */
public class PSOCallable implements Callable<AlgorithmResult> {
    private String filePath;

    public PSOCallable(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public AlgorithmResult call() throws Exception {
        Panicle bestPanicle;
        FileRead fileRead = new FileRead();
        fileRead.Read(filePath);
        Panicle[] panicles = new Panicle[80];
        int min = 0, mintime = Integer.MAX_VALUE;
        for (int i = 0; i < panicles.length; i++) {
            panicles[i] = new Panicle(fileRead.ProcessNum, fileRead.MachineNum, fileRead.WorkpieceNum, fileRead.Pronum);
            panicles[i].Init(fileRead.Time);
            if (panicles[i].getTime() < mintime) {
                min = i;
            }
        }
        bestPanicle = panicles[min].clone();
        //System.out.println("时间："+bestPanicle.getTime());
        for (int o = 0; o < 400; o++) {
            //System.out.println(o+" <> "+bestPanicle.getTime());
            for (Panicle value : panicles) {
                value.UpdataV(bestPanicle.getTime());
                value.UpdataX(fileRead.Time, bestPanicle);
                value.CalTime(fileRead.Time);
                value.Updata_pbest();
            }

            //排序
            Panicle panicle;
            int i, j;
            for (i = 0; i < panicles.length - 1; i++) {
                //查找最小值
                min = i;
                for (j = i + 1; j < panicles.length; j++) {
                    if (panicles[min].getTime() > panicles[j].getTime()) {
                        min = j;
                    }
                }

                //交换
                if (min != i) {
                    panicle = panicles[min];
                    panicles[min] = panicles[i];
                    panicles[i] = panicle;
                }
            }

            if (panicles[0].getTime() < bestPanicle.getTime()) {
                bestPanicle = panicles[0].clone();
            }
            bestPanicle.LocalSearch(fileRead.Time);
            for (int k = (int) (panicles.length * 0.3); k < (int) (panicles.length * 0.5); k++) {
                panicles[k].Init(fileRead.Time);
            }
            for (int k = (int) (panicles.length * 0.5); k < (int) (panicles.length * 0.8); k++) {
                int index = ((int) (Math.random() * 1000)) % (int) (panicles.length * 0.3);
                panicles[k] = panicles[k].clone();
            }
            for (int k = (int) (panicles.length * 0.8); k < panicles.length; k++) {
                panicles[k] = bestPanicle.clone();
            }

        }
        return bestPanicle;
    }
}
