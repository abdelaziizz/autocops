package com.mdp.autocops.controller;

import com.mdp.autocops.model.entity.InstitutionConfig;
import com.mdp.autocops.model.entity.InstitutionsConfigMapping;
import com.mdp.autocops.service.framework.InstitutionConfigMappingService;
import com.mdp.autocops.service.framework.InstitutionConfigService;
import com.mdp.autocops.service.impl.processes.Execute;
import com.mdp.autocops.service.impl.processes.Read;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/execute")
public class ExecutionController {

    private final Execute execute;
    private final Read read;
    private final InstitutionConfigMappingService mappingService;
    private final InstitutionConfigService configService;

    @ResponseBody
    @PostMapping("/{config_id}")
    public String execute(@PathVariable long config_id) {
        return execute.execute(config_id);
    }

    @ResponseBody
    @PostMapping("/read/{config_id}")
    public List<Map> read(@PathVariable long config_id) {
        try {
            List<InstitutionsConfigMapping> mappings = mappingService.findByInstConfig(config_id);
            InstitutionConfig config = configService.getById(config_id);
            return read.readCSV(config.getReading_line(), config.getImport_path(), mappings).getMaps();
        } catch (Exception e) {
            log.error(e);
            return null;
        }

    }

}
