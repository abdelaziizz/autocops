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
                            System.out.println(message);
                            return response;
                        } else {
                            String type = mappings.get(i).getImport_field_type();
//                            System.out.println(row.getCell(mappings.get(i).getImport_field_index()).getCellFormula());
                            if (type.equals("Number")) {
                                try {
                                    //System.out.println(row.getCell(mappings.get(i).getImport_field_index()).getCellFormula());
                                    current_record.put(mappings.get(i).getExport_field_head().getField_name(), String.valueOf((long) row.getCell(mappings.get(i).getImport_field_index()).getNumericCellValue()));
                                } catch (Exception e) {
                                    message = "Cannot get a NUMERIC value from a STRING cell in row : " + row.getRowNum() + " at index : " + mappings.get(i).getImport_field_index();
                                    response.setMessage(message);
                                    System.out.println(message);
                                    System.out.println(e.getMessage());
                                    return response;
                                } }
                            if (type.equals("String")) {
                                try {
                                    current_record.put(mappings.get(i).getExport_field_head().getField_name(), row.getCell(mappings.get(i).getImport_field_index()).getStringCellValue());
                                } catch (Exception e) {
                                    message = "Cannot get a STRING value from a NUMERIC cell in row : " + row.getRowNum() + " at index : " + mappings.get(i).getImport_field_index();
                                    response.setMessage(message);
                                    System.out.println(message);
                                    System.out.println(e.getMessage());
                                    return response;
                                }}
                            if (type.equals("Date")) {
                                try {
                                    String date_on_hand = String.valueOf(row.getCell(mappings.get(i).getImport_field_index()).getDateCellValue());
                                    String date = adjustDateFormat(input_date, output_date, date_on_hand);
                                    current_record.put(mappings.get(i).getExport_field_head().getField_name(), date );
                                } catch (Exception e) {
                                    message = "Cannot get a Date value from this cell in row : " + row.getRowNum() + " at index : " + mappings.get(i).getImport_field_index();
                                    response.setMessage(message);
                                    System.out.println(message);
                                    System.out.println(e.getMessage());
                                    return response;
                                } } }
                        if ( mappings.get(i).isRequired() && !current_record.containsKey(mappings.get(i).getExport_field_head().getField_name())) {
                            response.setMessage("Mandatory Field Missing");
                            response.setMaps(null);
                            System.out.println(response.getMessage());
                            return response;
                        } } records.add(current_record);
                }
                message = "Success";
                System.out.println(message);
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
                                    String date_on_hand = element.getElementsByTagName(mapping.getImport_field().getField_name()).item(0).getTextContent();
                                    String date = adjustDateFormat(input_date, output_date, date_on_hand);
                                    map.put(mappings.get(i).getExport_field_head().getField_name(), date);
                                }
                                else map.put(mapping.getExport_field_head().getField_name(), element.getElementsByTagName(mapping.getImport_field().getField_name()).item(0).getTextContent());
                            }
                            if ( mapping.isRequired() && !map.containsKey(mapping.getExport_field_head().getField_name())) {
                                response.setMessage("Mandatory Field Missing");
                                response.setMaps(null);
                                return response;
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
                                String date_on_hand = values[mappings.get(j).getImport_field_index()];
                                String date = adjustDateFormat(input_date, output_date, date_on_hand);
                                map.put(mappings.get(j).getExport_field_head().getField_name(), date);
                            }
                            else map.put(mappings.get(j).getExport_field_head().getField_name(), values[mappings.get(j).getImport_field_index()]);
                            if ( mappings.get(j).isRequired() && !map.containsKey(mappings.get(j).getExport_field_head().getField_name())) {
                                response.setMessage("Mandatory Field Missing");
                                response.setMaps(null);
                                return response;
                            }
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
                                String date_on_hand = line.substring(mappings.get(j).getStart_index(), mappings.get(j).getLast_index());
                                String date = adjustDateFormat(input_date, output_date, date_on_hand);
                                    map.put(mappings.get(j).getExport_field_head().getField_name(), date);
                            }
                            else map.put(mappings.get(j).getExport_field_head().getField_name(), line.substring(mappings.get(j).getStart_index(), mappings.get(j).getLast_index()));
                            if ( mappings.get(j).isRequired() && !map.containsKey(mappings.get(j).getExport_field_head().getField_name())) {
                                response.setMessage("Mandatory Field Missing");
                                response.setMaps(null);
                                return response;
                            }
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

    // Adjust date format in input file to required format in output file
    public String adjustDateFormat (String input_date, String output_date, String date_on_hand) {
        String date = "";
        if (input_date.equals(output_date)) date = input_date;
        else {
            String day = "";
            String month = "";
            String year = "";
            if (input_date.equals("YYYY/MM/DD") || input_date.equals("YYYY-MM-DD")) {
                year = date_on_hand.substring(0, 4);
                month = date_on_hand.substring(5, 7);
                day = date_on_hand.substring(8);
            } else if (input_date.equals("YYYY/DD/MM") || input_date.equals("YYYY-DD-MM")) {
                year = date_on_hand.substring(0, 4);
                day = date_on_hand.substring(5, 7);
                month = date_on_hand.substring(8);
            }
            if (output_date.equals("YYYY-MM-DD")) date = year + "-" + month + "-" + day;
            if (output_date.equals("YYYY/MM/DD")) date = year + "/" + month + "/" + day;
            if (output_date.equals("YYYY-DD-MM")) date = year + "-" + day + "-" + month;
            if (output_date.equals("YYYY/DD/MM")) date = year + "/" + day + "/" + month;
        }
        return date;
    }

}
