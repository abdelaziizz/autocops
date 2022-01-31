package com.mdp.autocops.service.impl.processes;

import com.mdp.autocops.model.entity.InstitutionConfig;
import com.mdp.autocops.model.entity.InstitutionsConfigMapping;
import com.mdp.autocops.service.framework.InstitutionConfigMappingService;
import com.mdp.autocops.service.framework.InstitutionConfigService;
import com.mdp.autocops.service.framework.processes.CardIssuanceService;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            List<Map> maps = readXML(fileName, mappings);
            org.dom4j.Document document = DocumentHelper.createDocument();
            org.dom4j.Element applications = document.addElement("APPLICATIONS");
            for (Map map : maps) {
                org.dom4j.Element application = applications.addElement("application").addAttribute("xmlns:xsd", "http://sv.bpc.in/SVAP");
                application.addElement("application_type").addText("1");
                application.addElement("application_flow_id").addText("2");
                application.addElement("application_status").addText("3");
                application.addElement("operator_id").addText("Auto");
                application.addElement("institution_id ");
                org.dom4j.Element agent_id = application.addElement("agent_id");
                application.addElement("customer_type ");
                application.addElement("appl_prioritized ");
                org.dom4j.Element customer = application.addElement("customer").addAttribute("id", "customer_1");
                customer.addAttribute("id", "customer_1");
                customer.addElement("command ");
                customer.addElement("customer_relation ");
                customer.addElement("customer_category").addText("OCTGFULL");
                org.dom4j.Element marital_status = customer.addElement("marital_status");
                org.dom4j.Element nationality = customer.addElement("nationality");
                org.dom4j.Element contract = customer.addElement("contract");
                contract.addElement("command ");
                contract.addElement("contract_type ");
                org.dom4j.Element product_id = contract.addElement("product_id");
                contract.addElement("start_date").addText("2021-07-03");
                org.dom4j.Element card = contract.addElement("card");
                card.addAttribute("id", "card_1");
                card.addAttribute("value", "card_1");
                card.addElement("command ");
                card.addElement("card_type ");
                org.dom4j.Element cardholder = card.addElement("cardholder");
                org.dom4j.Element cardholder_name = cardholder.addElement("cardholder_name");
                cardholder.addElement("command ");
                org.dom4j.Element person = cardholder.addElement("person");
                person.addElement("command ");
                org.dom4j.Element birthday = person.addElement("birthday");
                org.dom4j.Element place_of_birth = person.addElement("place_of_birth");
                org.dom4j.Element gender = person.addElement("gender");
                org.dom4j.Element identity_card = person.addElement("identity_card");
                identity_card.addElement("command ");
                identity_card.addElement("id_type ");
                identity_card.addElement("id_series ");
                org.dom4j.Element id_number = identity_card.addElement("id_number");
                org.dom4j.Element contact = cardholder.addElement("contact");
                contact.addElement("command ");
                contact.addElement("contact_type ");
                org.dom4j.Element contact_data = contact.addElement("contact_data");
                contact_data.addElement("commun_method ");
                org.dom4j.Element commun_address = contact_data.addElement("commun_address");
                contact_data.addElement("start_date").addText("2021-07-03T00:00:00");
                org.dom4j.Element contact2 = cardholder.addElement("contact");
                contact2.addElement("command ");
                contact2.addElement("contact_type ");
                org.dom4j.Element contact_data2 = contact2.addElement("contact_data");
                contact_data2.addElement("commun_method ");
                org.dom4j.Element commun_address2 = contact_data2.addElement("commun_address");
                contact_data2.addElement("start_date").addText("2021-07-03T00:00:00");
                org.dom4j.Element address = cardholder.addElement("address");
                address.addElement("command ");
                org.dom4j.Element address_type = address.addElement("address_type");
                org.dom4j.Element country = address.addElement("country");
                org.dom4j.Element address_name = address.addElement("address_name");
                org.dom4j.Element region = address_name.addElement("region");
                org.dom4j.Element city = address_name.addElement("city");
                org.dom4j.Element street = address_name.addElement("street");
                address.addElement("house").addText("");
                org.dom4j.Element account = contract.addElement("account");
                account.addAttribute("id", "account_1");
                account.addAttribute("value", "account1");
                account.addElement("command ");
                account.addElement("currency ");
                account.addElement("account_type ");
                account.addElement("account_status ");
                org.dom4j.Element account_object = account.addElement("account_object");
                account_object.addAttribute("ref_id", "card_1");
                account_object.addElement("account_link_flag ");
                account_object.addElement("is_pos_default ");
                account_object.addElement("is_atm_default ");
                org.dom4j.Element person1 = customer.addElement("person");
                person1.addElement("command ");
                person1.addElement("person_name ").addAttribute("language", "LANGENG");
                org.dom4j.Element birthday1 = person1.addElement("birthday");
                org.dom4j.Element place_of_birth1 = person1.addElement("place_of_birth");
                org.dom4j.Element gender1 = person1.addElement("gender");
                org.dom4j.Element identity_card2 = person1.addElement("identity_card");
                identity_card2.addElement("command ");
                identity_card2.addElement("id_type ");
                identity_card2.addElement("id_series ");
                org.dom4j.Element id_number1 = identity_card2.addElement("id_number");
                org.dom4j.Element contact3 = customer.addElement("contact");
                contact3.addElement("command ");
                contact3.addElement("contact_type ");
                org.dom4j.Element contact_data3 = contact3.addElement("contact_data");
                contact_data3.addElement("commun_method ");
                org.dom4j.Element cellphone = contact_data3.addElement("commun_address");
                contact_data3.addElement("start_date").addText("2021-07-03T00:00:00");
                org.dom4j.Element contact4 = customer.addElement("contact");
                contact4.addElement("command ");
                contact4.addElement("contact_type ");
                org.dom4j.Element contact_data4 = contact4.addElement("contact_data");
                contact_data4.addElement("commun_method ");
                org.dom4j.Element commun_address1 = contact_data4.addElement("commun_address");
                contact_data4.addElement("start_date").addText("2021-07-03T00:00:00");
                org.dom4j.Element address2 = customer.addElement("address");
                address2.addElement("command ");
                org.dom4j.Element address_type1 = address2.addElement("address_type");
                org.dom4j.Element country1 = address2.addElement("country");
                org.dom4j.Element address_name2 = address2.addElement("address_name");
                org.dom4j.Element region1 = address_name2.addElement("region");
                org.dom4j.Element city1 = address_name2.addElement("city");
                org.dom4j.Element street1 = address_name2.addElement("street");
                address2.addElement("house").addText("");

                if (map.get("agent_id") != null ) {
                    agent_id.addText(map.get("agent_id").toString());
                }
                if (map.get("marital_status") != null ) {
                    marital_status.addText(map.get("marital_status").toString());
                }
                if (map.get("nationality") != null ) {
                    nationality.addText(map.get("nationality").toString());
                }
                if (map.get("product_id") != null ) {
                    product_id.addText(map.get("product_id").toString());
                }
                if (map.get("cardholder_name") != null ) {
                    cardholder_name.addText(map.get("cardholder_name").toString());
                }
                if (map.get("birthday") != null ) {
                    birthday.addText(map.get("birthday").toString());
                    birthday1.addText(map.get("birthday").toString());
                }
                if (map.get("place_of_birth") != null ) {
                    place_of_birth.addText(map.get("place_of_birth").toString());
                }
                if (map.get("gender") != null ) {
                    gender.addText(map.get("gender").toString());
                    gender1.addText(map.get("gender").toString());
                }
                if (map.get("id_number") != null ) {
                    id_number.addText(map.get("id_number").toString());
                    id_number1.addText(map.get("id_number").toString());
                }
                if (map.get("commun_address") != null ) {
                    commun_address.addText(map.get("commun_address").toString());
                    commun_address1.addText(map.get("commun_address").toString());
                    commun_address2.addText(map.get("commun_address").toString());
                    cellphone.addText(map.get("commun_address").toString());
                }
                if (map.get("address_type") != null ) {
                    address_type.addText(map.get("address_type").toString());
                    address_type1.addText(map.get("address_type").toString());
                }
                if (map.get("country") != null ) {
                    country.addText(map.get("country").toString());
                    country1.addText(map.get("country").toString());
                }
                if (map.get("region") != null ) {
                    region.addText(map.get("region").toString());
                    region1.addText(map.get("region").toString());
                }
                if (map.get("city") != null ) {
                    city.addText(map.get("city").toString());
                    city1.addText(map.get("city").toString());
                }
                if (map.get("street") != null ) {
                    street.addText(map.get("street").toString());
                    street1.addText(map.get("street").toString());
                }
                if (map.get("place_of_birth") != null ) {
                    place_of_birth.addText(map.get("place_of_birth").toString());
                    place_of_birth1.addText(map.get("place_of_birth").toString());
                }
                if (map.get("agent_id") != null ) {
                    agent_id.addText(map.get("agent_id").toString());
                }
                if (map.get("agent_id") != null ) {
                    agent_id.addText(map.get("agent_id").toString());
                }
                if (map.get("agent_id") != null ) {
                    agent_id.addText(map.get("agent_id").toString());
                }
                if (map.get("agent_id") != null ) {
                    agent_id.addText(map.get("agent_id").toString());
                }
            }
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer;
            writer = new XMLWriter(System.out, format);
            writer.write(document);
            return "success";
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return "fail";
            }
        }

    public List<Map> readXML(String fileName, List<InstitutionsConfigMapping> mappings) throws ParserConfigurationException {
        try {
            List<Map> maps = new ArrayList<>();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(fileName));
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("Record");

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
                }
                maps.add(map);
            }
            return maps;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}