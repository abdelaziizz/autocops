package com.mdp.autocops.service.impl.processes;

import com.mdp.autocops.model.entity.InstitutionConfig;
import com.mdp.autocops.model.entity.InstitutionsConfigMapping;
import com.mdp.autocops.service.framework.processes.CardActivationService;
import com.mdp.autocops.service.framework.InstitutionConfigMappingService;
import com.mdp.autocops.service.framework.InstitutionConfigService;
import lombok.extern.log4j.Log4j2;
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
import java.util.List;

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

            FileInputStream file = new FileInputStream(config.getImport_path());
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            for ( int j = config.getReading_line() ; j <= sheet.getLastRowNum() ; j++ ) {
                Row row = sheet.getRow(j);
                Document document = DocumentHelper.createDocument();
                Element applications = document.addElement( "APPLICATIONS" );
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
                Element card = contract.addElement("card").addAttribute("id","card_1");
                card.addElement("command").addText("21");
                Element card_number = card.addElement("card_number");
                card.addElement("card_status").addText("18");
                for ( int i = 0 ; i < mappings.size() ; i++ ) {
                    InstitutionsConfigMapping mapping = mappings.get(i);
                    String fieldName = mapping.getExport_field_head().getField_name();
                    if (fieldName.equals("customer_number")) {
                        customer_number.addText(Integer.toString((int)row.getCell(Integer.valueOf(mapping.getImport_field_index())).getNumericCellValue()));
                    }
                    if (fieldName.equals("contract_number")) {
                        contract_number.addText(Integer.toString((int)row.getCell(Integer.valueOf(mapping.getImport_field_index())).getNumericCellValue()));
                    }
                    if (fieldName.equals("card_number")) {
                        card_number.addText(Integer.toString((int)row.getCell(Integer.valueOf(mapping.getImport_field_index())).getNumericCellValue()));
                    }
                    if (fieldName.equals("agent_id")) {
                        agent_id.addText(Integer.toString((int)row.getCell(Integer.valueOf(mapping.getImport_field_index())).getNumericCellValue()));
                    }
                }
                OutputFormat format = OutputFormat.createPrettyPrint();
                XMLWriter writer;
                writer = new XMLWriter( System.out, format );
                writer.write( document );
            }
            return "success";
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return "fail";
        }
    }

}
