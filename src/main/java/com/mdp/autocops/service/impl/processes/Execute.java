package com.mdp.autocops.service.impl.processes;

import com.mdp.autocops.model.entity.InstitutionConfig;
import com.mdp.autocops.model.entity.InstitutionsConfigMapping;
import com.mdp.autocops.model.entity.ReadingResponse;
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
    @Autowired
    FileAccess fileAccess;

    // Performs the read and write in from the bank to smart vista
    public String execute(long config_id) {
        try {
            InstitutionConfig config = configService.getById(config_id);
            List<InstitutionsConfigMapping> mappings = mappingService.findByInstConfig(config_id);
            List<Map> maps = new ArrayList<>();
            ReadingResponse response = new ReadingResponse();
            String input_date = config.getImport_date();
            String output_date = config.getExport_date();
            if (config.getImport_File_format().equals("Excel")) {
                response = read.readExcel(config.getReading_line(), config.getImport_path(), mappings, input_date, output_date);
            } else if (config.getImport_File_format().equals("XML")) {
                response = read.readXML(config.getReading_root(), config.getImport_path(), mappings, input_date, output_date);
            } else if (config.getImport_File_format().equals("CSV")) {
                response = read.readCSV(config.getReading_line(), config.getImport_path(), mappings, input_date, output_date);
            } else if (config.getImport_File_format().equals("Text")) {
                System.out.println(config.getReading_line());
                System.out.println(config.getImport_path());
                System.out.println(mappings.size());
                System.out.println(config.getLast_lines());
                System.out.println(input_date);
                System.out.println(output_date);
                response = read.readText(config.getReading_line(), config.getImport_path(), mappings, config.getLast_lines(), input_date, output_date);
            }
            if (response.getMaps() == null || response.getMaps().size() == 0) return response.getMessage();
            else {
                maps = response.getMaps();
                String response2 = write.writeXML(config.getWriting_root(), config.getTemplate_path(), config.getExport_path(), maps);
                return response2;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return "fail";
        }
    }

}
