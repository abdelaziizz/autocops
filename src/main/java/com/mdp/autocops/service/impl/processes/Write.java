package com.mdp.autocops.service.impl.processes;

import com.mdp.autocops.model.entity.InstitutionsConfigMapping;
import lombok.extern.log4j.Log4j2;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
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

    public String writeXML (String writing_root, String template_path , List<Map> maps){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try(InputStream is = new FileInputStream(template_path)) {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(is);
            NodeList applications = doc.getElementsByTagName(writing_root);
            System.out.println(applications.getLength());
            for ( int i = 0 ; i < maps.size() ; i++ ) {
                assignChildren(applications.item(0), maps.get(i));
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer= transformerFactory.newTransformer();
            doc.normalize();
            DOMSource source = new DOMSource(doc);
            StreamResult result=new StreamResult(new File("C:\\Users\\ab.ashraf\\Desktop\\Result Out.xml"));
            transformer.transform(source, result);
            //For console Output.
            //StreamResult consoleResult = new StreamResult(System.out);
            //transformer.transform(source, consoleResult);
            return "success";
        } catch (Exception e) {
            log.error(e.getStackTrace());
            return "fail";
        }
    }
    public void assignChildren (Node node, Map<String, String> map) {
        if (node.hasChildNodes()) {
            NodeList nodes = node.getChildNodes();
            for ( int i = 0 ; i < nodes.getLength() ; i++ ) {
                assignChildren(nodes.item(i), map);
            }
        }
        else {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
               if (map.get(node.getNodeName()) != null) {
                   node.setTextContent(map.get(node.getNodeName()));
               }
            }
        }
    }
}
