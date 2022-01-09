package com.mdp.autocops.controller;

import com.mdp.autocops.service.framework.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class SchedulerController {

    @Autowired
    SchedulerService service;

    //    @Scheduled(fixedRate = 10000)
    @GetMapping("/schedulingService")
    public String fileReader() throws IOException {
        return service.scheduledReader();
    }


}
