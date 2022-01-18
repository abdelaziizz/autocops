package com.mdp.autocops.service.impl;

import com.mdp.autocops.model.entity.InstitutionConfig;
import com.mdp.autocops.model.entity.InstitutionsConfigMapping;
import com.mdp.autocops.service.framework.CardActivationService;
import com.mdp.autocops.service.framework.InstitutionConfigMappingService;
import com.mdp.autocops.service.framework.InstitutionConfigService;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
            List<InstitutionsConfigMapping> mappings = mappingService.findByInstConfig(22);
            InstitutionConfig config = configService.getById(22);

            FileInputStream file = new FileInputStream("C:\\Users\\ab.ashraf\\Desktop\\file samples\\card activation\\ADIB\\in\\ActivationCard.xlsx");
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
                application.addElement("agent_id").addText("6565");
                application.addElement("customer_type").addText("14");
                application.addElement("appl_prioritized").addText("15");
                Element customer = application.addElement("customer");
                customer.addElement("command").addText("16");
                customer.addElement("customer_number").addText("55555");
                Element contract = customer.addElement("contract");
                contract.addElement("command").addText("17");
                contract.addElement("contract_number").addText("453453");
                Element card = contract.addElement("card").addAttribute("id","card_1");
                card.addElement("command").addText("21");
                card.addElement("card_number").addText("777777");
                card.addElement("card_status").addText("18");
                OutputFormat format = OutputFormat.createPrettyPrint();
                XMLWriter writer;
                writer = new XMLWriter( System.out, format );
                writer.write( document );
//                for ( int i = 0 ; i < mappings.size() ; i++ ) {
//                    InstitutionsConfigMapping mapping = mappings.get(i);
//                    System.out.println(mapping.getExport_field_head().getField_name() + " : " + row.getCell(mapping.getImport_field_index()-1));
//                }
            }
            return "success";
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return "fail";
        }
    }

}
