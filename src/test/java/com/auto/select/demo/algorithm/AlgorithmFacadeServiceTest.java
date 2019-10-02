package com.auto.select.demo.algorithm;

import com.auto.select.demo.service.AlgorithmFacadeService;
import org.junit.Test;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/8/22 19:13
 */
public class AlgorithmFacadeServiceTest {

    @Test
    public void getBestResult() {
        AlgorithmFacadeService algorithmFacadeService = new AlgorithmFacadeService();
        algorithmFacadeService.setFilePath("Mk01.fjs");
        System.out.println(algorithmFacadeService.getBestResult());
    }
}