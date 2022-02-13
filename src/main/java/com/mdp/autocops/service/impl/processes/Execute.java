package com.mdp.autocops.service.impl.processes;

import com.mdp.autocops.model.entity.InstitutionConfig;
import com.mdp.autocops.model.entity.InstitutionsConfigMapping;
import com.mdp.autocops.service.framework.InstitutionConfigMappingService;
import com.mdp.autocops.service.framework.InstitutionConfigService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class Execute {

    @Autowired
    InstitutionConfigMappingService mappingService;
    @Autowired
    InstitutionConfigService configService;
    @Autowired
    Read read;
    @Autowired
    Write write;

    public String execute (long config_id) {
         try {
             InstitutionConfig config = configService.getById(config_id);
             List<InstitutionsConfigMapping> mappings = mappingService.findByInstConfig(config_id);
             List<Map> maps = new ArrayList<>();
             if (config.getImport_File_format().getFormat_type().equals("Excel")) {
                 maps = read.readExcel(config.getReading_line(), config.getImport_path(), mappings);
             }
             else if ( config.getImport_File_format().getFormat_type().equals("XML")) {
                 maps = read.readXML(config.getReading_root(), config.getImport_path(), mappings);
             }
             write.writeXML(config.getWriting_root(), config.getTemplate_path(), config.getExport_path(), maps);
             return "success";
         } catch (Exception e) {
             log.error(e.getStackTrace());
             return "fail";
         }
    }

}