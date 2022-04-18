package com.mdp.autocops.service.impl.processes;

import com.mdp.autocops.model.entity.ResponseObject;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class Reading {

    public ResponseObject readExcel (String path, int reading_line) {
        List<Row> rows = new ArrayList<>();
        ResponseObject response = new ResponseObject();
        try {
            FileInputStream file = new FileInputStream(path);
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet.getLastRowNum() == 0) response.setMessage("Excel file is empty");
            else {
                for (int j = reading_line; j <= sheet.getLastRowNum(); j++) {
                    Row row = sheet.getRow(j);
                    rows.add(row);
                }
            }
            response.setList(rows);
            response.setMessage("success");
            return response;
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            return response;
        }
    }

    public ResponseObject readCSVText (String path, int reading_line) {
        List<String> lines = new ArrayList<>();
        ResponseObject response = new ResponseObject();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            if (br.readLine() == null) response.setMessage("Input file is empty");
            else {
                String line;
                int counter = 0;
                while ((line = br.readLine()) != null) {
                    if (counter >= reading_line) lines.add(line);
                    else counter++;
                }}
            response.setMessage("success");
            response.setList(lines);
            return response;
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            return response;
        }
    }

    public ResponseObject readXML (String path, String reading_root) {
        List<Map> maps = new ArrayList<>();
        ResponseObject response = new ResponseObject();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(path));
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName(reading_root);
            if (list.getLength() == 0) {
                response.setMessage("Input file is empty");
                return response;
            }
            else {
                for (int i = 0 ; i < list.getLength() ; i++) {
                    Map<String, String> map = new HashMap<>();
                    Node node = list.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        if (node.getTextContent().trim().length() > 0) {
                            Element current_node = (Element) node;
                            String value = current_node.getTextContent();
                            map.put(node.getNodeName(), value);
                        }
                    } else if (node.getTextContent().trim().length() > 0) map.put(node.getNodeName(), node.getTextContent());
                    maps.add(map);
                }
                response.setMessage("success");
                response.setList(maps);
                return response;
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            return response;
        }
    }

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
