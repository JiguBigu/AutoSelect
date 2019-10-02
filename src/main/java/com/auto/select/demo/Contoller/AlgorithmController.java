package com.auto.select.demo.Contoller;

import com.auto.select.demo.service.AlgorithmFacadeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/8/22 17:02
 */

@RestController
@RequestMapping("/algorithm")
public class AlgorithmController {

    AlgorithmFacadeService algorithmFacadeService = new AlgorithmFacadeService();

    private final Logger logger = LoggerFactory.getLogger(AlgorithmController.class);

    @RequestMapping(value = "/runAlgorithm")
    @CrossOrigin
    public Map<String, Object> runAlgorithm(@RequestParam("fileName") String fileName,HttpSession session){
        AlgorithmFacadeService algorithmFacadeService = new AlgorithmFacadeService();
        logger.info("运行算法的文件名为：" + fileName);
        algorithmFacadeService.setFilePath(fileName);
        Map<String, Object> modelMap = algorithmFacadeService.getBestResult();

        return modelMap;
    }
}
