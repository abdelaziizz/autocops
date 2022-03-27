package com.mdp.autocops.service.impl.processes;

import com.mdp.autocops.model.entity.InstitutionsConfigMapping;
import com.mdp.autocops.model.entity.ReadingResponse;
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
import java.io.*;
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

    // For reading Excel files produced by banks.
    public ReadingResponse readExcel(int reading_line, String path, List<InstitutionsConfigMapping> mappings, String input_date, String output_date) throws IOException {
        String message = "";
        ReadingResponse response = new ReadingResponse();
        List<Map> records = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(path);
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            //System.out.println(sheet.getLastRowNum());
            if (sheet.getLastRowNum() == 0) {
                message = "Input file is empty";
                response.setMessage(message);
            } else {
                for (int j = reading_line; j <= sheet.getLastRowNum(); j++) {
                    Map<String, String> current_record = new HashMap<>();
                    Row row = sheet.getRow(j);
                    for (int i = 0; i < mappings.size(); i++) {
                        if (row.getCell(mappings.get(i).getImport_field_index()) == null) {
                            message = "Missing field in row : " + row.getRowNum() + " at index : " + mappings.get(i).getImport_field_index();
                            response.setMessage(message);
                            return response;
                        } else {
                            String type = mappings.get(i).getImport_field_type();
                            if (type.equals("Number")) {
                                try {
                                    current_record.put(mappings.get(i).getExport_field_head().getField_name(), String.valueOf((long) row.getCell(mappings.get(i).getImport_field_index()).getNumericCellValue()));
                                } catch (Exception e) {
                                    message = "Cannot get a NUMERIC value from a STRING cell in row : " + row.getRowNum() + " at index : " + mappings.get(i).getImport_field_index();
                                    response.setMessage(message);
                                    return response;
                                }
                            }
                            if (type.equals("String")) {
                                current_record.put(mappings.get(i).getExport_field_head().getField_name(), row.getCell(mappings.get(i).getImport_field_index()).getStringCellValue());
                            }
                            if (type.equals("Date")) {
                                String day = "";
                                String month = "";
                                String year = "";
                                String date = "";
                                if (input_date.equals(output_date)) {
                                    current_record.put(mappings.get(i).getExport_field_head().getField_name(), row.getCell(mappings.get(i).getImport_field_index()).getStringCellValue());
                                }
                                else {
                                    String date_on_hand = row.getCell(mappings.get(i).getImport_field_index()).getStringCellValue();
                                    if ( input_date.equals("DD/MM/YYYY") || input_date.equals("DD-MM-YYYY") ) {
                                        day = date_on_hand.substring(0, 2);
                                        month = date_on_hand.substring(3, 5);
                                        year = date_on_hand.substring(6);
                                    }
                                    else if ( input_date.equals("MM/DD/YYYY") || input_date.equals("MM-DD-YYYY") ) {
                                        month = date_on_hand.substring(0, 2);
                                        day = date_on_hand.substring(3, 5);
                                        year = date_on_hand.substring(6);
                                    }
                                    else if ( input_date.equals("MM/DD/YY") || input_date.equals("MM-DD-YY") ) {
                                        month = date_on_hand.substring(0, 2);
                                        day = date_on_hand.substring(3, 5);
                                        year = "20"+date_on_hand.substring(6);
                                    }
                                    else if ( input_date.equals("DD/MM/YY") || input_date.equals("DD-MM-YY") ) {
                                        day = date_on_hand.substring(0, 2);
                                        month = date_on_hand.substring(3, 5);
                                        year = "20"+date_on_hand.substring(6);
                                    }
                                    if (output_date.equals("DD/MM/YYYY")) date = day+"/"+month+"/"+year;
                                    if (output_date.equals("DD-MM-YYYY")) date = day+"-"+month+"-"+year;
                                    if (output_date.equals("MM/DD/YYYY")) date = month+"/"+day+"/"+year;
                                    if (output_date.equals("MM-DD-YYYY")) date = month+"-"+day+"-"+year;
                                    if (output_date.equals("DD/MM/YY")) date = day+"/"+month+"/"+year.substring(0,2);
                                    if (output_date.equals("DD-MM-YY")) date = day+"-"+month+"-"+year.substring(0,2);
                                    if (output_date.equals("MM/DD/YY")) date = month+"/"+day+"/"+year.substring(0,2);
                                    if (output_date.equals("MM-DD-YY")) date = month+"-"+day+"-"+year.substring(0,2);
                                    current_record.put(mappings.get(i).getExport_field_head().getField_name(),date);
                                }
                            }
                        }
                    }
                    records.add(current_record);
                }
                message = "Success";
                response.setMaps(records);
                response.setMessage(message);
            }
            return response;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    // For reading XMLs produced by banks (Only ADCB in our case)
    public ReadingResponse readXML(String reading_root, String fileName, List<InstitutionsConfigMapping> mappings, String input_date, String output_date) throws ParserConfigurationException {
        String message;
        ReadingResponse response = new ReadingResponse();
        try {
            List<Map> maps = new ArrayList<>();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(fileName));
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName(reading_root);
            if (list.getLength() == 0) {
                message = "Input file is empty";
                response.setMessage(message);
            } else {
                for (int temp = 0; temp < list.getLength(); temp++) {
                    Map<String, String> map = new HashMap<>();
                    Node node = list.item(temp);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        for (int i = 0; i < mappings.size(); i++) {
                            InstitutionsConfigMapping mapping = mappings.get(i);
                            String type = mapping.getImport_field_type();
                            if (element.getElementsByTagName(mapping.getImport_field().getField_name()).getLength() != 0) {
                                if (type.equals("Date")) {
                                    String day = "";
                                    String month = "";
                                    String year = "";
                                    String date = "";
                                    if (input_date.equals(output_date)) {
                                        map.put(mappings.get(i).getExport_field_head().getField_name(), element.getElementsByTagName(mapping.getImport_field().getField_name()).item(0).getTextContent());
                                    }
                                    else {
                                        String date_on_hand = element.getElementsByTagName(mapping.getImport_field().getField_name()).item(0).getTextContent();
                                        if ( input_date.equals("DD/MM/YYYY") || input_date.equals("DD-MM-YYYY") ) {
                                            day = date_on_hand.substring(0, 2);
                                            month = date_on_hand.substring(3, 5);
                                            year = date_on_hand.substring(6);
                                        }
                                        else if ( input_date.equals("MM/DD/YYYY") || input_date.equals("MM-DD-YYYY") ) {
                                            month = date_on_hand.substring(0, 2);
                                            day = date_on_hand.substring(3, 5);
                                            year = date_on_hand.substring(6);
                                        }
                                        else if ( input_date.equals("MM/DD/YY") || input_date.equals("MM-DD-YY") ) {
                                            month = date_on_hand.substring(0, 2);
                                            day = date_on_hand.substring(3, 5);
                                            year = "20"+date_on_hand.substring(6);
                                        }
                                        else if ( input_date.equals("DD/MM/YY") || input_date.equals("DD-MM-YY") ) {
                                            day = date_on_hand.substring(0, 2);
                                            month = date_on_hand.substring(3, 5);
                                            year = "20"+date_on_hand.substring(6);
                                        }
                                        if (output_date.equals("DD/MM/YYYY")) date = day+"/"+month+"/"+year;
                                        if (output_date.equals("DD-MM-YYYY")) date = day+"-"+month+"-"+year;
                                        if (output_date.equals("MM/DD/YYYY")) date = month+"/"+day+"/"+year;
                                        if (output_date.equals("MM-DD-YYYY")) date = month+"-"+day+"-"+year;
                                        if (output_date.equals("DD/MM/YY")) date = day+"/"+month+"/"+year.substring(0,2);
                                        if (output_date.equals("DD-MM-YY")) date = day+"-"+month+"-"+year.substring(0,2);
                                        if (output_date.equals("MM/DD/YY")) date = month+"/"+day+"/"+year.substring(0,2);
                                        if (output_date.equals("MM-DD-YY")) date = month+"-"+day+"-"+year.substring(0,2);
                                        map.put(mappings.get(i).getExport_field_head().getField_name(),date);
                                    }
                                }
                                else map.put(mapping.getExport_field_head().getField_name(), element.getElementsByTagName(mapping.getImport_field().getField_name()).item(0).getTextContent());
                            }
                        }
                    }
                    maps.add(map);
                }
                message = "Success";
                response.setMessage(message);
                response.setMaps(maps);
            }
            return response;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    // For reading XMLs produced by Smart Vista
    public ReadingResponse readXMLNested(String reading_root, String fileName) {
        String message;
        ReadingResponse response = new ReadingResponse();
        try {
            List<Map> maps = new ArrayList<>();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(fileName));
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName(reading_root);
            if (list.getLength() == 0) {
                message = "Input file is empty";
                response.setMessage(message);
            } else {
                for (int i = 0; i < list.getLength(); i++) {
                    Map<String, String> map = new HashMap<>();
                    Node node = list.item(i);
                    String path = "";
                    readChildren(node, map, path);
                    maps.add(map);
                }
                message = "Success";
                response.setMessage(message);
                response.setMaps(maps);

            }
            return response;
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    // Helper for readXMLNested
    public void readChildren(Node node, Map<String, String> map, String path) {
        if (node.hasChildNodes()) {
            NodeList nodes = node.getChildNodes();
            path += "/" + node.getNodeName();
            for (int i = 0; i < nodes.getLength(); i++) readChildren(nodes.item(i), map, path);
        } else {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                if (node.getTextContent().trim().length() > 0) {
                    path += "/" + node.getNodeName();
                    Element current_node = (Element) node;
                    String value = current_node.getTextContent();
                    map.put(path, value);
                }
            } else {
                if (node.getTextContent().trim().length() > 0) {
                    path += "/" + node.getParentNode().getNodeName();
                    map.put(path, node.getTextContent());
                }
            }
        }
    }
//        Displays output maps for testing purposes
//    public void displayListOfMaps(List<Map> maps) {
//        for (Map map : maps) {
//            Map<String, String> current = map;
//            for (Map.Entry<String, String> entry : current.entrySet()) {
//                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//            }
//            System.out.println("----------------------------------------------------------------------------------------");
//        }
//    }

    // Read CSV Files
    public ReadingResponse readCSV(int reading_line, String path, List<InstitutionsConfigMapping> mappings, String input_date, String output_date) {
        List<Map> maps = new ArrayList<>();
        ReadingResponse response = new ReadingResponse();
        String message;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            if (br.readLine() == null) {
                message = "Input file is empty";
                response.setMessage(message);
            } else {
                String line;
                int counter = 0;
                while ((line = br.readLine()) != null) {
                    if (counter >= reading_line) {
                        Map<String, String> map = new HashMap<>();
                        String[] values = line.split(",");
                        for (int j = 0; j < mappings.size(); j++) {
                            String type = mappings.get(j).getImport_field_type();
                            if (type.equals("Date")) {
                                String day = "";
                                String month = "";
                                String year = "";
                                String date = "";
                                if (input_date.equals(output_date)) {
                                    map.put(mappings.get(j).getExport_field_head().getField_name(), values[mappings.get(j).getImport_field_index() - 1]);
                                }
                                else {
                                    String date_on_hand = values[mappings.get(j).getImport_field_index() - 1];
                                    if ( input_date.equals("DD/MM/YYYY") || input_date.equals("DD-MM-YYYY") ) {
                                        day = date_on_hand.substring(0, 2);
                                        month = date_on_hand.substring(3, 5);
                                        year = date_on_hand.substring(6);
                                    }
                                    else if ( input_date.equals("MM/DD/YYYY") || input_date.equals("MM-DD-YYYY") ) {
                                        month = date_on_hand.substring(0, 2);
                                        day = date_on_hand.substring(3, 5);
                                        year = date_on_hand.substring(6);
                                    }
                                    else if ( input_date.equals("MM/DD/YY") || input_date.equals("MM-DD-YY") ) {
                                        month = date_on_hand.substring(0, 2);
                                        day = date_on_hand.substring(3, 5);
                                        year = "20"+date_on_hand.substring(6);
                                    }
                                    else if ( input_date.equals("DD/MM/YY") || input_date.equals("DD-MM-YY") ) {
                                        day = date_on_hand.substring(0, 2);
                                        month = date_on_hand.substring(3, 5);
                                        year = "20"+date_on_hand.substring(6);
                                    }
                                    if (output_date.equals("DD/MM/YYYY")) date = day+"/"+month+"/"+year;
                                    if (output_date.equals("DD-MM-YYYY")) date = day+"-"+month+"-"+year;
                                    if (output_date.equals("MM/DD/YYYY")) date = month+"/"+day+"/"+year;
                                    if (output_date.equals("MM-DD-YYYY")) date = month+"-"+day+"-"+year;
                                    if (output_date.equals("DD/MM/YY")) date = day+"/"+month+"/"+year.substring(0,2);
                                    if (output_date.equals("DD-MM-YY")) date = day+"-"+month+"-"+year.substring(0,2);
                                    if (output_date.equals("MM/DD/YY")) date = month+"/"+day+"/"+year.substring(0,2);
                                    if (output_date.equals("MM-DD-YY")) date = month+"-"+day+"-"+year.substring(0,2);
                                    map.put(mappings.get(j).getExport_field_head().getField_name(),date);
                                }
                            }
                            else map.put(mappings.get(j).getExport_field_head().getField_name(), values[mappings.get(j).getImport_field_index() - 1]);
                        }
                        maps.add(map);
                    } else counter++;
                }
                message = "Success";
                response.setMessage(message);
                response.setMaps(maps);
            }
            return response;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    // Read Text Files
    public ReadingResponse readText(int reading_line, String path, List<InstitutionsConfigMapping> mappings, int last_lines, String input_date, String output_date) {
        List<Map> maps = new ArrayList<>();
        ReadingResponse response = new ReadingResponse();
        String message;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            if (br.readLine() == null) {
                message = "Input file is empty";
                response.setMessage(message);
            } else {
                String line;
                int counter = 0;
                while ((line = br.readLine()) != null) {
                    if (counter >= reading_line) {
                        Map<String, String> map = new HashMap<>();
                        for (int j = 0; j < mappings.size(); j++) {
                            String type = mappings.get(j).getImport_field_type();
                            if (type.equals("Date")) {
                                String day = "";
                                String month = "";
                                String year = "";
                                String date = "";
                                if (input_date.equals(output_date)) {
                                    map.put(mappings.get(j).getExport_field_head().getField_name(), line.substring(mappings.get(j).getStart_index(), mappings.get(j).getLast_index()));
                                }
                                else {
                                    String date_on_hand = line.substring(mappings.get(j).getStart_index(), mappings.get(j).getLast_index());
                                    if ( input_date.equals("DD/MM/YYYY") || input_date.equals("DD-MM-YYYY") ) {
                                        day = date_on_hand.substring(0, 2);
                                        month = date_on_hand.substring(3, 5);
                                        year = date_on_hand.substring(6);
                                    }
                                    else if ( input_date.equals("MM/DD/YYYY") || input_date.equals("MM-DD-YYYY") ) {
                                        month = date_on_hand.substring(0, 2);
                                        day = date_on_hand.substring(3, 5);
                                        year = date_on_hand.substring(6);
                                    }
                                    else if ( input_date.equals("MM/DD/YY") || input_date.equals("MM-DD-YY") ) {
                                        month = date_on_hand.substring(0, 2);
                                        day = date_on_hand.substring(3, 5);
                                        year = "20"+date_on_hand.substring(6);
                                    }
                                    else if ( input_date.equals("DD/MM/YY") || input_date.equals("DD-MM-YY") ) {
                                        day = date_on_hand.substring(0, 2);
                                        month = date_on_hand.substring(3, 5);
                                        year = "20"+date_on_hand.substring(6);
                                    }
                                    if (output_date.equals("DD/MM/YYYY")) date = day+"/"+month+"/"+year;
                                    if (output_date.equals("DD-MM-YYYY")) date = day+"-"+month+"-"+year;
                                    if (output_date.equals("MM/DD/YYYY")) date = month+"/"+day+"/"+year;
                                    if (output_date.equals("MM-DD-YYYY")) date = month+"-"+day+"-"+year;
                                    if (output_date.equals("DD/MM/YY")) date = day+"/"+month+"/"+year.substring(0,2);
                                    if (output_date.equals("DD-MM-YY")) date = day+"-"+month+"-"+year.substring(0,2);
                                    if (output_date.equals("MM/DD/YY")) date = month+"/"+day+"/"+year.substring(0,2);
                                    if (output_date.equals("MM-DD-YY")) date = month+"-"+day+"-"+year.substring(0,2);
                                    map.put(mappings.get(j).getExport_field_head().getField_name(),date);
                                }
                            }
                            else map.put(mappings.get(j).getExport_field_head().getField_name(), line.substring(mappings.get(j).getStart_index(), mappings.get(j).getLast_index()));
                        }
                        maps.add(map);
                    } else counter++;
                }
                message = "Success";
                response.setMaps(maps);
                response.setMessage(message);
            }
            return response;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
