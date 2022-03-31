package com.mdp.autocops.service.impl.processes;

import com.mdp.autocops.model.entity.InstitutionConfig;
import com.mdp.autocops.model.entity.InstitutionsConfigMapping;
import com.mdp.autocops.model.entity.ReadingResponse;
import com.mdp.autocops.service.framework.InstitutionConfigMappingService;
import com.mdp.autocops.service.framework.InstitutionConfigService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    public List<String> execute(long config_id) {
        try {
            InstitutionConfig config = configService.getById(config_id);
            List<InstitutionsConfigMapping> mappings = mappingService.findByInstConfig(config_id);
            List<String> import_paths = new ArrayList<>();
            File folder = new File(config.getImport_path());
            File[] listOfFiles = folder.listFiles();
            List<String> responses = new ArrayList<>();
            if (listOfFiles.length == 0) {
                String res = "folder is empty";
                responses.add(res);
                return responses;
            }
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    import_paths.add(config.getImport_path()+"\\"+file.getName());
                }
            }

            for (String file : import_paths) {
                List<Map> maps = new ArrayList<>();
                String [] fileNameParsed = file.split("\\\\");
                String config_response = "For configuration with id : " + config_id + ", the response for file : " + fileNameParsed[fileNameParsed.length -1] + " is ---> ";
                String extension = file.substring(file.length()-3);
                ReadingResponse response = new ReadingResponse();
                String input_date = config.getImport_date();
                String output_date = config.getExport_date();
                if (config.getImport_File_format().equals("Excel")) {
                    if (file.substring(file.length()-4).equals("xlsx")) {
                        response = read.readExcel(config.getReading_line(), file, mappings, input_date, output_date);
                        config_response += response.getMessage();
                    } else config_response += "wrong file type";
                } else if (config.getImport_File_format().equals("XML")) {
                    if (extension.equals("xml")) {
                        response = read.readXML(config.getReading_root(), file, mappings, input_date, output_date);
                        config_response += response.getMessage();
                    } else config_response += "wrong file type";
                } else if (config.getImport_File_format().equals("CSV")) {
                    if (extension.equals("csv")) {
                        response = read.readCSV(config.getReading_line(), file, mappings, input_date, output_date);
                        config_response += response.getMessage();
                    } else config_response += "wrong file type";
                } else if (config.getImport_File_format().equals("Text")) {
                    if (extension.equals("txt")) {
                        response = read.readText(config.getReading_line(), file, mappings, config.getLast_lines(), input_date, output_date);
                        config_response += response.getMessage();
                    } else config_response += "wrong file type";
                }
                if (response.getMaps() == null || response.getMaps().size() == 0) {
                    config_response = "For configuration with id : " + config_id + ", the response for file : " + fileNameParsed[fileNameParsed.length -1] + " is ---> no data read";
                } else {
                    maps = response.getMaps();
                    String response2 = write.writeXML(config.getWriting_root(), config.getTemplate_path(), config.getExport_path(), maps);
                    config_response = "For configuration with id : " + config_id + ", the response for file : " + fileNameParsed[fileNameParsed.length -1] + " is ---> " + response2;
                    if (response2.equals("success")) {
                        File file_to_be_deleted = new File(file);
                        file_to_be_deleted.delete();
                        TimeUnit.SECONDS.sleep(1);
                    }
//                return response2;
                }
                responses.add(config_response);
            }
            return responses;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

}
