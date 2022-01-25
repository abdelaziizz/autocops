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
//            System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
//            System.out.println("------");
            NodeList list = doc.getElementsByTagName("Record");
            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);
                String uid = null; String customer_category = null; String card_prefix = null; String fio = null;
                String sex = null; String id_number = null; String id_type = null; String id_series = null;
                String country = null; String address_type = null; String region = null; String city = null; String street = null;
                String house = null; String apartment = null; String cellphone = null; String brpart = null; String name_on_card = null;
                String nationality = null; String martial_status = null; String education = null; String business_nature = null;
                String birthday = null; String place_of_birth = null; String receive_sms = null; String cms_account = null;
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    // String id = element.getAttribute("id");
                    if (element.getElementsByTagName("UID").getLength() != 0) uid = element.getElementsByTagName("UID").item(0).getTextContent();
                    if (element.getElementsByTagName("CUSTOMER.CATEGORY").getLength() != 0) customer_category = element.getElementsByTagName("CUSTOMER.CATEGORY").item(0).getTextContent();
                    if (element.getElementsByTagName("CARDPREFIX").getLength() != 0 ) card_prefix = element.getElementsByTagName("CARDPREFIX").item(0).getTextContent();
                    if (element.getElementsByTagName("FIO").getLength() != 0 ) fio = element.getElementsByTagName("FIO").item(0).getTextContent();
                    if (element.getElementsByTagName("SEX").getLength() != 0 ) sex = element.getElementsByTagName("SEX").item(0).getTextContent();
                    if (element.getElementsByTagName("ID.NUMBER").getLength() != 0 ) id_number = element.getElementsByTagName("ID.NUMBER").item(0).getTextContent();
                    if (element.getElementsByTagName("ID.TYPE").getLength() != 0 ) id_type = element.getElementsByTagName("ID.TYPE").item(0).getTextContent();
                    if (element.getElementsByTagName("ID.SERIES").getLength() != 0 ) id_series = element.getElementsByTagName("ID.SERIES").item(0).getTextContent();
                    if (element.getElementsByTagName("COUNTRY").getLength() != 0 ) country = element.getElementsByTagName("COUNTRY").item(0).getTextContent();
                    if (element.getElementsByTagName("ADDRESS.TYPE").getLength() != 0 ) address_type = element.getElementsByTagName("ADDRESS.TYPE").item(0).getTextContent();
                    if (element.getElementsByTagName("REGION").getLength() != 0 ) region = element.getElementsByTagName("REGION").item(0).getTextContent();
                    if (element.getElementsByTagName("CITY").getLength() != 0 ) city = element.getElementsByTagName("CITY").item(0).getTextContent();
                    if (element.getElementsByTagName("STREET").getLength() != 0 ) street = element.getElementsByTagName("STREET").item(0).getTextContent();
                    if (element.getElementsByTagName("HOUSE").getLength() != 0 ) house = element.getElementsByTagName("HOUSE").item(0).getTextContent();
                    if (element.getElementsByTagName("APARTMENT").getLength() != 0 ) apartment = element.getElementsByTagName("APARTMENT").item(0).getTextContent();
                    if (element.getElementsByTagName("CELLPHONE").getLength() != 0 ) cellphone = element.getElementsByTagName("CELLPHONE").item(0).getTextContent();
                    if (element.getElementsByTagName("BRPART").getLength() != 0 ) brpart = element.getElementsByTagName("BRPART").item(0).getTextContent();
                    if (element.getElementsByTagName("NAMEONCARD").getLength() != 0 ) name_on_card = element.getElementsByTagName("NAMEONCARD").item(0).getTextContent();
                    if (element.getElementsByTagName("NATIONALITY").getLength() != 0 ) nationality = element.getElementsByTagName("NATIONALITY").item(0).getTextContent();
                    if (element.getElementsByTagName("MARTIAL.STATUS").getLength() != 0 ) martial_status = element.getElementsByTagName("MARTIAL.STATUS").item(0).getTextContent();
                    if (element.getElementsByTagName("EDUCATION").getLength() != 0 ) education = element.getElementsByTagName("EDUCATION").item(0).getTextContent();
                    if (element.getElementsByTagName("BUSINESS.NATURE").getLength() != 0 ) business_nature = element.getElementsByTagName("BUSINESS.NATURE").item(0).getTextContent();
                    if (element.getElementsByTagName("BIRTHDAY").getLength() != 0 ) birthday = element.getElementsByTagName("BIRTHDAY").item(0).getTextContent();
                    if (element.getElementsByTagName("PLACE.OF.BIRTH").getLength() != 0 ) place_of_birth = element.getElementsByTagName("PLACE.OF.BIRTH").item(0).getTextContent();
                    if (element.getElementsByTagName("RECEIVE.SMS").getLength() != 0 ) receive_sms = element.getElementsByTagName("RECEIVE.SMS").item(0).getTextContent();
                    if (element.getElementsByTagName("CMSACCOUNT").getLength() != 0 ) cms_account = element.getElementsByTagName("CMSACCOUNT").item(0).getTextContent();

                    System.out.println("uid : "+uid);
                    System.out.println("customer category : "+customer_category);
                    System.out.println("card prefix : "+card_prefix);
                    System.out.println("fio : "+fio);
                    System.out.println("sex : "+sex);
                    System.out.println("id number : "+id_number);
                    System.out.println("id type : "+id_type);
                    System.out.println("id series : "+id_series);
                    System.out.println("country : "+country);
                    System.out.println("address type : "+address_type);
                    System.out.println("region : "+region);
                    System.out.println("city : "+city);
                    System.out.println("street : "+street);
                    System.out.println("house : "+house);
                    System.out.println("apartment : "+apartment);
                    System.out.println("cellphone : "+cellphone);
                    System.out.println("brpart : "+brpart);
                    System.out.println("name on card : "+name_on_card);
                    System.out.println("nationality : "+nationality);
                    System.out.println("martial status : "+martial_status);
                    System.out.println("education : "+education);
                    System.out.println("business nature : "+business_nature);
                    System.out.println("birthday : "+birthday);
                    System.out.println("place of birth : "+place_of_birth);
                    System.out.println("receive sms : "+receive_sms);
                    System.out.println("cms account : "+cms_account);
                }

                System.out.println("------------");
            }


            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
}
