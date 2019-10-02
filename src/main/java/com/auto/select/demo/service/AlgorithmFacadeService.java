package com.auto.select.demo.service;

import com.auto.select.demo.algorithm.ABC.ABCThread;
import com.auto.select.demo.algorithm.ABC.Food;
import com.auto.select.demo.algorithm.GA.GAThread;
import com.auto.select.demo.algorithm.PSO.PSOThread;
import com.auto.select.demo.algorithm.PSO.Panicle;
import com.auto.select.demo.algorithm.SA.Individual;
import com.auto.select.demo.algorithm.SA.SAThread;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.State.TERMINATED;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/8/22 16:02
 * 外观模式，运行算法并获取最佳优化结果
 */


public class AlgorithmFacadeService {
    private String filePath = "G:/AutoSelectFiles/";
    private ABCThread abcThread;
    private PSOThread psoThread;
    private SAThread saThread;
    private GAThread gaThread;
    private final String[] algorithmName = {"ABC", "PSO", "SA", "GA"};


    public AlgorithmFacadeService() {

        abcThread = new ABCThread();
        psoThread = new PSOThread();
        saThread = new SAThread();
        gaThread = new GAThread();
    }

    public void setFilePath(String fileName){
        this.filePath = filePath + fileName;
        //为各个算法线程设置读取文件路径
        abcThread.setFilePath(filePath);
        psoThread.setFilePath(filePath);
        saThread.setFilePath(filePath);
        gaThread.setFilePath(filePath);
    }

    /**
     * 运行算法
     */
    private void startThread() {
        abcThread.start();
        psoThread.start();
        saThread.start();
        gaThread.start();
    }

    /**
     * 获取最佳答案
     *
     * @return 最佳算法名称、加工时间、机器号、工序号
     */
    public Map<String, Object> getBestResult() {
        Map<String, Object> modelMap = new HashMap<>();
        startThread();
        while (abcThread.getState() != TERMINATED || psoThread.getState() != TERMINATED || saThread.getState() != TERMINATED || gaThread.getState() != TERMINATED) {
        }
        modelMap = selectBest();
        return modelMap;
    }


    /**
     * 算法运行后，获取所有算法中最佳结果
     *
     * @return 成功标志、最佳结果
     */
    private Map<String, Object> selectBest() {
        Map<String, Object> modelMap = new HashMap<>();

        Food abcBest = abcThread.getBest();
        Panicle psoBest = psoThread.getBest();
        Individual saBest = saThread.getBest();
        com.auto.select.demo.algorithm.GA.Individual gaBest = gaThread.getBest();

        //寻找最小时间的下标
        int index = 0;
        int minTime = Integer.MAX_VALUE;
        ArrayList<String> name = new ArrayList<>();
        ArrayList<Integer> time = new ArrayList<>();
        time.add(abcBest.getTime());
        time.add(psoBest.getTime());
        time.add(saBest.getTime());
        time.add(gaBest.getTime());
        for (int i = 0; i < time.size(); i++) {
            if (time.get(i) < minTime) {
                index = i;
                minTime = time.get(i);
            }
        }
        for(int i = 0; i < time.size(); i++){
            if(time.get(i) == minTime){
                name.add(algorithmName[i]);
            }
        }


        //返回最佳值
        modelMap.put("name", name);
        if (index == 0) {
            //ABC算法时间最短
            modelMap.put("data", abcBest);
        } else if (index == 1) {
            //PSO算法时间最短
            modelMap.put("data", psoBest);
        } else if (index == 2) {
            //SA算法时间最短
            modelMap.put("data", saBest);
        } else if(index == 3){
            //GA算法时间最短
            modelMap.put("data", gaBest);
        } else {
            modelMap.put("name", "发生未知错误");
        }
        return modelMap;
    }



}
