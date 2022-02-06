package com.mdp.autocops.controller;

import com.mdp.autocops.service.impl.processes.Execute;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/execute")
public class ExecutionController {

    private final Execute execute;

    @ResponseBody
    @PostMapping("/{config_id}")
    public String execute(@PathVariable long config_id) {
        return execute.execute(config_id);
    }
}
