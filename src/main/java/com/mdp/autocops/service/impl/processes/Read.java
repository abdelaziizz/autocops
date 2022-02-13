package com.mdp.autocops.service.impl.processes;

import com.mdp.autocops.model.entity.InstitutionConfig;
import com.mdp.autocops.model.entity.InstitutionsConfigMapping;
import com.mdp.autocops.service.framework.InstitutionConfigMappingService;
import com.mdp.autocops.service.framework.InstitutionConfigService;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class Read {
    @Autowired
    InstitutionConfigMappingService mappingService;
    @Autowired
    InstitutionConfigService configService;

    public List<Map> readExcel(int reading_line, String path, List<InstitutionsConfigMapping> mappings) throws IOException {
        List<Map> records = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(path);
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            for (int j = reading_line; j <= sheet.getLastRowNum(); j++) {
                Map<String, String> current_record = new HashMap<>();
                Row row = sheet.getRow(j);
                for (int i = 0; i < mappings.size(); i++) {
                    String type = mappings.get(i).getImport_field_type().getField_type();
                    if (type.equals("Number")) {
                        current_record.put(mappings.get(i).getExport_field_head().getField_name(), String.valueOf((long) row.getCell(mappings.get(i).getImport_field_index()).getNumericCellValue()));
                    }
                    if (type.equals("String")) {
                        current_record.put(mappings.get(i).getExport_field_head().getField_name(), row.getCell(mappings.get(i).getImport_field_index()).getStringCellValue());
                    }
                } records.add(current_record);
            } return records;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public List<Map> readXML(String reading_root, String fileName, List<InstitutionsConfigMapping> mappings) throws ParserConfigurationException {
        try {
            List<Map> maps = new ArrayList<>();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(fileName));
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName(reading_root);

            for (int temp = 0; temp < list.getLength(); temp++) {
                Map<String, String> map = new HashMap<>();
                Node node = list.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    for (int i = 0; i < mappings.size(); i++) {
                        InstitutionsConfigMapping mapping = mappings.get(i);
                        if (element.getElementsByTagName(mapping.getImport_field().getField_name()).getLength() != 0) {
                            map.put(mapping.getExport_field_head().getField_name(), element.getElementsByTagName(mapping.getImport_field().getField_name()).item(0).getTextContent());
                        }
                    }
                } maps.add(map);
            } return maps;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Map> readXMLNested(long config_id) {
        try {
            InstitutionConfig config = configService.getById(config_id);
            List<Map> maps = new ArrayList<>();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(config.getImport_path()));
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName(config.getReading_root());
            for ( int i = 0 ; i < list.getLength() ; i++ ) {
                Map<String,String> map = new HashMap<>();
                Node node = list.item(i);
                String path = "";
                readChildren(node, map, path);
                maps.add(map);
            }
            //displayListOfMaps(maps);
            return maps;
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }
    public void readChildren (Node node, Map<String, String> map, String path) {
        if (node.hasChildNodes()) {
            NodeList nodes = node.getChildNodes();
            path+= "/"+node.getNodeName();
            for ( int i = 0 ; i < nodes.getLength() ; i++ )  readChildren(nodes.item(i), map, path);
        } else {
            if (node.getNodeType() == Node.ELEMENT_NODE ) {
                if (node.getTextContent().trim().length()>0) {
                    path += "/" + node.getNodeName();
                    Element current_node = (Element) node;
                    String value = current_node.getTextContent();
                    map.put(path, value);
                }
            }
            else {
                if (node.getTextContent().trim().length()>0) {
                    System.out.println(node.getParentNode().getNodeName() + " ----> " + node.getTextContent());
                    path+="/"+node.getParentNode().getNodeName();
                    map.put(path, node.getTextContent());
                }
            }
        } }

    public void displayListOfMaps (List<Map> maps) {
        for (Map map : maps) {
            Map<String, String> current = map;
            for (Map.Entry<String,String> entry : current.entrySet()) {
                System.out.println("Key = " + entry.getKey() +", Value = " + entry.getValue());
            }
            System.out.println("----------------------------------------------------------------------------------------");
        }
    }
}
