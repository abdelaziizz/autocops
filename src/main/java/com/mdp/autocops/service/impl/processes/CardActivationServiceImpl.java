package com.mdp.autocops.service.impl.processes;

import com.mdp.autocops.model.entity.InstitutionConfig;
import com.mdp.autocops.model.entity.InstitutionsConfigMapping;
import com.mdp.autocops.service.framework.processes.CardActivationService;
import com.mdp.autocops.service.framework.InstitutionConfigMappingService;
import com.mdp.autocops.service.framework.InstitutionConfigService;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class CardActivationServiceImpl implements CardActivationService {

    @Autowired
    InstitutionConfigMappingService mappingService;

    @Autowired
    InstitutionConfigService configService;

    @Override
    public String readAndExport() {
        try {
            List<InstitutionsConfigMapping> mappings = mappingService.findByInstConfig(1);
            InstitutionConfig config = configService.getById(1);
            if (!config.getImport_File_format().getFormat_type().equals("Excel")) {
                return "Incompatible input file";
            }
            else {
                List<Map> maps = readExcel(config.getReading_line(), config.getImport_path(), mappings);
                Document document = DocumentHelper.createDocument();
                Element applications = document.addElement("APPLICATIONS");
                for (int i = 0; i < maps.size(); i++) {
                    Map<String, String> current_map = maps.get(i);
                    Element application = applications.addElement("application").addAttribute("xmlns:xsd", "http://sv.bpc.in/SVAP");
                    application.addElement("application_type").addText("19");
                    application.addElement("application_flow_id").addText("11");
                    application.addElement("application_status").addText("12");
                    application.addElement("operator_id").addText("20");
                    application.addElement("institution_id").addText("13");
                    Element agent_id = application.addElement("agent_id");
                    application.addElement("customer_type").addText("14");
                    application.addElement("appl_prioritized").addText("15");
                    Element customer = application.addElement("customer");
                    customer.addElement("command").addText("16");
                    Element customer_number = customer.addElement("customer_number");
                    Element contract = customer.addElement("contract");
                    contract.addElement("command").addText("17");
                    Element contract_number = contract.addElement("contract_number");
                    Element card = contract.addElement("card").addAttribute("id", "card_1");
                    card.addElement("command").addText("21");
                    Element card_number = card.addElement("card_number");
                    card.addElement("card_status").addText("18");

                    if (current_map.get("contract_number") != null) {
                        contract_number.addText(current_map.get("contract_number"));
                    }
                    if (current_map.get("customer_number") != null) {
                        customer_number.addText(current_map.get("customer_number"));
                    }
                    if (current_map.get("card_number") != null) {
                        card_number.addText(current_map.get("card_number"));
                    }
                    if (current_map.get("agent_id") != null) {
                        agent_id.addText(current_map.get("agent_id"));
                    }

                }
                OutputFormat format = OutputFormat.createPrettyPrint();
                XMLWriter writer;
                writer = new XMLWriter(System.out, format);
                writer.write(document);
                return "success";
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return "fail";
        }
    }


    public List<Map> readExcel (int reading_line, String path, List<InstitutionsConfigMapping> mappings) throws IOException {
        List<Map> records = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(path);
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            for (int j = reading_line; j <= sheet.getLastRowNum(); j++) {
                Map<String, String> current_record = new HashMap<>();
                Row row = sheet.getRow(j);
                for ( int i = 0 ; i < mappings.size() ; i++ ) {
                    String type = mappings.get(i).getImport_field_type().getField_type();
                    if (type.equals("Number")) {
                        current_record.put(mappings.get(i).getExport_field_head().getField_name(), String.valueOf((long) row.getCell(mappings.get(i).getImport_field_index()).getNumericCellValue()));
                    }
                    if (type.equals("String")) {
                        current_record.put(mappings.get(i).getExport_field_head().getField_name(), row.getCell(mappings.get(i).getImport_field_index()).getStringCellValue());
                    }
                }
                records.add(current_record);
            }
            System.out.println(records.toString());
            return records;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
