package com.mdp.autocops.service.impl.processes;

import com.mdp.autocops.model.entity.InstitutionConfig;
import com.mdp.autocops.model.entity.ResponseObject;
import com.mdp.autocops.service.framework.InstitutionConfigMappingService;
import com.mdp.autocops.service.framework.InstitutionConfigService;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class Process {

    @Autowired
    InstitutionConfigMappingService mappingService;

    @Autowired
    InstitutionConfigService configService;

    @Autowired
    Reading reading;

    public List<String> runConfig(long configId) {
        List<String> responses = new ArrayList<>();
        try {
            InstitutionConfig config = configService.getById(configId);
            List<String> import_paths = new ArrayList<>();
            File folder = new File(config.getImport_path());
            File[] listOfFiles = folder.listFiles();
            if (listOfFiles.length == 0) {
                String res = "folder is empty";
                responses.add(res);
                return responses;
            }
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    import_paths.add(config.getImport_path()+"\\\\"+file.getName());
                }
            }

            for (String file : import_paths) {
                String[] fileNameParsed = file.split("\\\\");
                String config_response = "For configuration with id : " + configId + ", the response for file : " + fileNameParsed[fileNameParsed.length - 1] + " is ---> ";
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                InputStream is = new FileInputStream(config.getTemplate_path());
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(is);
                NodeList applications = doc.getElementsByTagName(config.getWriting_root());
                int app_length = applications.getLength();
                int result_length = 0;
                String res = "";
                ResponseObject response;
                if (config.getImport_File_format().equals("XML")) {

                    response = reading.readXML(file, config.getReading_root());
                    result_length = response.getList().size();
                } else if (config.getImport_File_format().equals("Excel")) {
                    response = reading.readExcel(file, config.getReading_line());
                    result_length = response.getList().size();
                } else {
                    response = reading.readCSVText(file, config.getReading_line());
                    result_length = response.getList().size();
                }
                for (int j = app_length; j < result_length; j++) {
                    Node extra_app = applications.item(0);
                    Node extra_app_ext = extra_app.cloneNode(true);
                    doc.getDocumentElement().appendChild(extra_app_ext);
                }
                applications = doc.getElementsByTagName(config.getWriting_root());
                if (response.getMessage().equals("success")) {
                    if (config.getImport_File_format().equals("XML")) {
                        if (file.endsWith(".xml")) {
                            List<Map> maps = response.getList();
                            for (int i = 0; i < maps.size(); i++)
                                res = assignChildrenXML(applications.item(i), maps.get(i), config);
                        } else res += "wrong file type";
                    } else if (config.getImport_File_format().equals("Text")) {
                        if (file.endsWith(".txt")) {
                            List<String> lines = response.getList();
                            lines = lines.subList(0, lines.size() - config.getLast_lines());
                            for (int i = 0; i < lines.size(); i++)
                                res = assignChildrenText(applications.item(i), lines.get(i), config);
                        } else res += "wrong file type";
                    } else if (config.getImport_File_format().equals("CSV")) {
                        if (file.endsWith(".csv")) {
                            List<String> lines = response.getList();
                            for (int i = 0; i < lines.size(); i++)
                                res = assignChildrenCSV(applications.item(i), lines.get(i), config);
                        } else res += "wrong file type";
                    } else if (config.getImport_File_format().equals("Excel")) {
                        if (file.endsWith(".xlsx")) {
                            List<Row> rows = response.getList();
                            for (int i = 0; i < rows.size(); i++)
                                res = assignChildrenExcel(applications.item(i), rows.get(i), config);
                        } else res += "wrong file type";
                    }
                    if (res.equals("success")) {
                        NodeList test = doc.getElementsByTagName(config.getWriting_root());
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        doc.normalize();
                        DOMSource source = new DOMSource(doc);
                        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                        // change to "\\\\" or "/" depends on windows and linux
                        StreamResult result = new StreamResult(new File(config.getExport_path() + "\\\\" + config.getFile_prefix() + timeStamp + ".xml"));
                        transformer.transform(source, result);
                        responses.add(config_response+res);
                        File file1 = new File(file);
                        File file2 = new File(config.getExport_path()+"\\\\"+"done"+timeStamp+fileNameParsed[fileNameParsed.length - 1]);
                        file1.renameTo(file2);
                    } else responses.add(config_response+res);
                } else responses.add(config_response+response.getMessage());
            }
        } catch (Exception e) {
            responses.add(e.getMessage());
            return responses;
        }
        return null;
    }

    public String assignChildrenXML(Node node, Map<String, String> map, InstitutionConfig config) {
        if (node.hasChildNodes()) {
            NodeList nodes = node.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) assignChildrenXML(nodes.item(i), map, config);
        } else if (node.getTextContent().trim().length() > 0) {
            String [] parsed = node.getTextContent().split(",");
            String type = parsed[0];
            String key = parsed[parsed.length-1];
            if (type.equals("Text") || type.equals("Number")) {
                if (map.containsKey(key)) {
                    node.setTextContent(map.get(key));
                } else return "The value corresponding to the key : " + key + "does not exist in input file";
            }
            else if (type.equals("Date")) {
                if (map.containsKey(key)) {
                    String date_on_hand = map.get(key);
                    String new_date = reading.adjustDateFormat(config.getImport_date(), config.getExport_date(), date_on_hand);
                    node.setTextContent(new_date);
                } else return "The value corresponding to the key : " + key + "does not exist in input file";
            }

        }
        return "success";
    }

    public String assignChildrenText(Node node, String line, InstitutionConfig config) {
        if (node.hasChildNodes()) {
            NodeList nodes = node.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) assignChildrenText(nodes.item(i), line, config);
        } else if (node.getTextContent().trim().length() > 0) {
            String[] mapping = node.getTextContent().split(",");
            String type = mapping[0];
            int start_index = Integer.valueOf(mapping[1]);
            int end_index = Integer.valueOf(mapping[mapping.length - 1]);
            if (type.equals("Text") || type.equals("Number")) {
                if (start_index > line.length() && end_index > line.length()) {
                    node.setTextContent(line.substring(start_index, end_index));
                } else return "The required indices for the tag "+ node.getNodeName() + "are beyond the limits of the input line";
            }
            else if (type.equals("Date")) {
                if (start_index > line.length() && end_index > line.length()) {
                    String date_on_hand = line.substring(start_index, end_index);
                    String new_date = reading.adjustDateFormat(config.getImport_date(), config.getExport_date(), date_on_hand);
                    node.setTextContent(new_date);
                } else return "The required indices for the tag "+ node.getNodeName() + "are beyond the limits of the input line";
            }
        }
        return "success";
    }

    public String assignChildrenCSV(Node node, String line, InstitutionConfig config) {
        if (node.hasChildNodes()) {
            NodeList nodes = node.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) assignChildrenCSV(nodes.item(i), line, config);
        } else if (node.getTextContent().trim().length() > 0) {
            String[] values = line.split(",");
            String value = node.getTextContent();
            String[] parsed = value.split(",");
            String type = parsed[0];
            int mapping = Integer.valueOf(parsed[parsed.length-1]);
            if (type.equals("Text") || type.equals("Number")) {
                if (mapping > parsed.length) node.setTextContent(values[mapping]);
                else return "The required index for the tag : " + node.getNodeName() + "is beyond the limits of the input record";
            }
            else if (type.equals("Date")) {
                if (mapping > parsed.length) {
                    String date_on_hand = values[mapping];
                    String new_date = reading.adjustDateFormat(config.getImport_date(), config.getExport_date(), date_on_hand);
                    node.setTextContent(new_date);
                }
                else return "The required index for the tag : " + node.getNodeName() + "is beyond the limits of the input record";
            }

        }
        return "success";
    }

    public String assignChildrenExcel(Node node, Row row, InstitutionConfig config) {
        if (node.hasChildNodes()) {
            NodeList nodes = node.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) assignChildrenExcel(nodes.item(i), row, config);
        } else if (node.getTextContent().trim().length() > 0) {
            String value = node.getTextContent();
            String[] parsed = value.split(",");
            String type = parsed[0];
            int mapping = Integer.valueOf(parsed[parsed.length - 1]);
            if (type.equals("Text")) {
                try {if (row.getCell(mapping) == null) return "Missing field in row : " + row.getRowNum() + " at index : " + mapping;
                else node.setTextContent(row.getCell(mapping).getStringCellValue());
                } catch (Exception e) {
                    return "This cell's type is not Text  ;  " + e.getMessage();
                }

            } else if (type.equals("Number")) {
                try {
                    if (row.getCell(mapping) == null) return "Missing field in row : " + row.getRowNum() + " at index : " + mapping;
                    else node.setTextContent(String.valueOf(row.getCell(mapping).getNumericCellValue()));
                } catch (Exception e) {
                    return "This cell's type is not Number  ;  " + e.getMessage();
                }
            } else if (type.equals("Date")) {
                try {
                    if (row.getCell(mapping) == null) return "Missing field in row : " + row.getRowNum() + " at index : " + mapping;
                    else {
                        String date_on_hand = String.valueOf(row.getCell(mapping).getDateCellValue());
                        String new_date = reading.adjustDateFormat(config.getImport_date(), config.getExport_date(), date_on_hand);
                        node.setTextContent(new_date);
                    }
                } catch (Exception e) {
                return "This cell's type is not Date  ;  " + e.getMessage();
                }
            }
        }
        return "success";
    }

}
