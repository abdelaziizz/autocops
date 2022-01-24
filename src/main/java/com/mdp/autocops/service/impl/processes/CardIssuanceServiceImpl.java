package com.mdp.autocops.service.impl.processes;

import com.mdp.autocops.model.entity.InstitutionConfig;
import com.mdp.autocops.model.entity.InstitutionsConfigMapping;
import com.mdp.autocops.service.framework.InstitutionConfigMappingService;
import com.mdp.autocops.service.framework.InstitutionConfigService;
import com.mdp.autocops.service.framework.processes.CardIssuanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
public class CardIssuanceServiceImpl implements CardIssuanceService {

    @Autowired
    InstitutionConfigMappingService mappingService;

    @Autowired
    InstitutionConfigService configService;


    @Override
    public String issueCardADCB() {

        try {
            List<InstitutionsConfigMapping> mappings = mappingService.findByInstConfig(2);
            InstitutionConfig config = configService.getById(2);
            String fileName = config.getImport_path();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(fileName));
            doc.getDocumentElement().normalize();
            System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
            System.out.println("------");
            NodeList list = doc.getElementsByTagName("Record");
            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                   // String id = element.getAttribute("id");
                    String uid = element.getElementsByTagName("UID").item(0).getTextContent();
                    String lastname = element.getElementsByTagName("lastname").item(0).getTextContent();
                    String nickname = element.getElementsByTagName("nickname").item(0).getTextContent();
                    NodeList salaryNodeList = element.getElementsByTagName("salary");
                    String salary = salaryNodeList.item(0).getTextContent();
                    // get salary's attribute
                    String currency = salaryNodeList.item(0).getAttributes().getNamedItem("currency").getTextContent();
                }
            }


            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
}
