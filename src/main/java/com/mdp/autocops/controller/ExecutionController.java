package com.mdp.autocops.controller;

import com.mdp.autocops.model.entity.InstitutionConfig;
import com.mdp.autocops.service.framework.InstitutionConfigMappingService;
import com.mdp.autocops.service.framework.InstitutionConfigService;
import com.mdp.autocops.service.impl.processes.Execute;
import com.mdp.autocops.service.impl.processes.Process;
import com.mdp.autocops.service.impl.processes.Read;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/execute")
public class ExecutionController {

    private final Execute execute;
    private final InstitutionConfigService configService;
    private final Process process;

 // @Scheduled(fixedRate = 300000)
    @ResponseBody
    @PostMapping
    public List<List<String>> executeScheduled() {
        System.out.println("Running Configurations");
        List<InstitutionConfig> configs = configService.getAll();
        List<List<String>> responses = new ArrayList<>();
        for (int i = 0 ; i < configs.size() ; i++ ) {
            if (configs.get(i).isActive()) {
                List<String> response = execute.execute(configs.get(i).getId());
                responses.add(response);
            }
        }
        return responses;
    }

    @ResponseBody
    @PostMapping("/{config_id}")
    public List<String> execute(@PathVariable long config_id) {
        return execute.execute(config_id);
    }

    @ResponseBody
    @PostMapping("/run/{config_id}")
    public List<String> run(@PathVariable long config_id) {
        return process.runConfig(config_id);
    }
}
