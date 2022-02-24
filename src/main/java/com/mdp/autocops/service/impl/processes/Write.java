package com.mdp.autocops.service.impl.processes;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class Write {

    // Writes data in maps to XML format provided by the template
    public String writeXML (String writing_root, String template_path, String output_path , List<Map> maps){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try(InputStream is = new FileInputStream(template_path)) {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(is);
            NodeList applications = doc.getElementsByTagName(writing_root);
            int app_length = applications.getLength();
            int map_length = maps.size();
            for ( int j = app_length ; j < map_length ; j++ ) {
                Node extra_app = applications.item(0);
                Node extra_app_ext = extra_app.cloneNode(true);
                doc.getDocumentElement().appendChild(extra_app_ext);;
            }
            applications = doc.getElementsByTagName(writing_root);
            for ( int i = 0 ; i < maps.size() ; i++ ) assignChildren(applications.item(i), maps.get(i));
            NodeList test = doc.getElementsByTagName(writing_root);
            System.out.println(test.getLength());
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer= transformerFactory.newTransformer();
            doc.normalize();
            DOMSource source = new DOMSource(doc);
            StreamResult result=new StreamResult(new File(output_path));
            transformer.transform(source, result);
            return "success";
        } catch (Exception e) {
            log.error(e);
            return "Could Not Write Document";
        }
    }

    // Helper for writeXML
    public void assignChildren (Node node, Map<String, String> map) {
        if (node.hasChildNodes()) {
            NodeList nodes = node.getChildNodes();
            for ( int i = 0 ; i < nodes.getLength() ; i++ ) assignChildren(nodes.item(i), map);
        } else {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
               if (map.get(node.getNodeName()) != null) {
                   node.setTextContent(map.get(node.getNodeName()));
               }
            } }
    }
}
