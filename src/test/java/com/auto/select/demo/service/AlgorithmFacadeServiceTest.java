package com.auto.select.demo.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/3/15 19:46
 */
public class AlgorithmFacadeServiceTest {

    @Autowired
    private AlgorithmFacadeService algorithmFacadeService;

    @Test
    public void executeAlgorithm() {
        Map map = new AlgorithmFacadeService().executeAlgorithm("Mk01.fjs");
        assertNotNull(map.get("data"));
    }
}