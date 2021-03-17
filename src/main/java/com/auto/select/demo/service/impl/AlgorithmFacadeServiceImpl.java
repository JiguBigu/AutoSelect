package com.auto.select.demo.service.impl;

import com.auto.select.demo.algorithm.ABC.ABCCallable;
import com.auto.select.demo.algorithm.ABC.ABCThread;
import com.auto.select.demo.algorithm.ABC.Food;
import com.auto.select.demo.algorithm.GA.GACallable;
import com.auto.select.demo.algorithm.GA.GAThread;
import com.auto.select.demo.algorithm.PSO.PSOCallable;
import com.auto.select.demo.algorithm.PSO.PSOThread;
import com.auto.select.demo.algorithm.PSO.Panicle;
import com.auto.select.demo.algorithm.SA.Individual;
import com.auto.select.demo.algorithm.SA.SACallable;
import com.auto.select.demo.algorithm.SA.SAThread;
import com.auto.select.demo.algorithm.util.AlgorithmResult;
import com.auto.select.demo.service.AlgorithmFacadeService;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/8/22 16:02
 * 外观模式，运行算法并获取最佳优化结果
 */

@Service
public class AlgorithmFacadeServiceImpl implements AlgorithmFacadeService {

    private final static ThreadFactory NAMED_THREAD_FACTORY = new ThreadFactoryBuilder().setNameFormat("algorithm-executor-pool-%d").build();
    private static ThreadPoolExecutor algorithmThreadPool = new ThreadPoolExecutor(4, 8,
            60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(20), NAMED_THREAD_FACTORY);
    public final static String BASE_FILE_PATH = "G:/AutoSelectFiles/";


    /**
     * 获取最佳答案
     *
     * @return 最佳算法名称、加工时间、机器号、工序号
     */
    public Map<String, Object> executeAlgorithm(String fileName) {

        Map<String, Object> modelMap = new HashMap<>();
        String filePath =BASE_FILE_PATH + fileName;
        List<Future<AlgorithmResult>> futureList = Lists.newArrayList();
        List<AlgorithmResult> results = Lists.newArrayList();
        //运行优化算法
        futureList.add(algorithmThreadPool.submit(new ABCCallable(filePath)));
        futureList.add(algorithmThreadPool.submit(new GACallable(filePath)));
        futureList.add(algorithmThreadPool.submit(new PSOCallable(filePath)));
        futureList.add(algorithmThreadPool.submit(new SACallable(filePath)));
        futureList.forEach(future -> {
            try {
                results.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        AlgorithmResult result = results.stream().distinct().min(Comparator.comparingInt(AlgorithmResult::getTime)).get();
        modelMap.put("name", getAlgorithmName(result.getClass().getName()));
        modelMap.put("data", result);
        return modelMap;
    }

    private String getAlgorithmName(String name) {
        if ("com.auto.select.demo.algorithm.ABC.Food".equals(name)) {
            return "ABC";
        } else if ("com.auto.select.demo.algorithm.GA.Individual".equals(name)) {
            return "GA";
        } else if ("com.auto.select.demo.algorithm.PSO.Panicle".equals(name)) {
            return "PSO";
        } else if ("com.auto.select.demo.algorithm.SA.Individual".equals(name)) {
            return "SA";
        } else {
            return  "未知算法";
        }
    }



}
