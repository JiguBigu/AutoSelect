package com.auto.select.demo.service.impl;

import com.auto.select.demo.algorithm.new_ga.GA;
import com.auto.select.demo.service.GAService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/12/13 9:47
 */
@Service
public class GAServiceImpl implements GAService {
    private static final String FILE_PATH = "G:/AutoSelectFiles/";

    public Map<String, Object> getGAResult(String fileName){
        Map<String,Object> modelMap = new HashMap<>();
        try {
            GA ga = new GA(FILE_PATH + fileName);
            ga.Run();
            modelMap.put("configInfo", ga.getConfigStr());
            modelMap.put("data", ga.findBest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelMap;
    }

}
