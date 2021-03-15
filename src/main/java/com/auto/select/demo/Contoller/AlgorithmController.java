package com.auto.select.demo.Contoller;

import com.auto.select.demo.service.AlgorithmFacadeService;
import com.auto.select.demo.service.GAService;
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
   // @Autowired
    GAService gaService = new GAService();

    private final Logger logger = LoggerFactory.getLogger(AlgorithmController.class);

    @RequestMapping(value = "/run/all")
    @CrossOrigin
    public Map<String, Object> runAlgorithm(@RequestParam("fileName") String fileName,HttpSession session){
        AlgorithmFacadeService algorithmFacadeService = new AlgorithmFacadeService();
        logger.info("运行算法的文件名为：" + fileName);
        Map<String, Object> modelMap = algorithmFacadeService.executeAlgorithm(fileName);

        return modelMap;
    }

    @RequestMapping(value = "/run/ga")
    @CrossOrigin
    public Map<String, Object> runGA(@RequestParam("fileName") String fileName){
        logger.info("运行GA算法的文件名为：" + fileName);
        return gaService.getGAResult(fileName);
    }
}
