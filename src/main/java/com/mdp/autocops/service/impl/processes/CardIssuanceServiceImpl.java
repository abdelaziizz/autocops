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
import java.io.File;
import java.util.List;

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
            NodeList list = doc.getElementsByTagName("Record");
            org.dom4j.Document document = DocumentHelper.createDocument();
            org.dom4j.Element applications = document.addElement("APPLICATIONS");
            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);
                String uid, customer_category, card_prefix, fio, sex, id_number, id_type, id_series, country, address_type, region,
                        city, street, house, apartment, cellphone, brpart, name_on_card, nationality, martial_status, education,
                        business_nature, birthday, place_of_birth, receive_sms, cms_account, gender;

                uid = customer_category = card_prefix = fio = sex = id_number = id_type = id_series = country = address_type = region = city =
                        street = house = apartment = cellphone = brpart = name_on_card = nationality = martial_status = education =
                                business_nature = birthday = place_of_birth = receive_sms = cms_account = gender = null;

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    // String id = element.getAttribute("id");
                    if (element.getElementsByTagName("UID").getLength() != 0)
                        uid = element.getElementsByTagName("UID").item(0).getTextContent();
                    if (element.getElementsByTagName("CUSTOMER.CATEGORY").getLength() != 0)
                        customer_category = element.getElementsByTagName("CUSTOMER.CATEGORY").item(0).getTextContent();
                    if (element.getElementsByTagName("CARDPREFIX").getLength() != 0)
                        card_prefix = element.getElementsByTagName("CARDPREFIX").item(0).getTextContent();
                    if (element.getElementsByTagName("FIO").getLength() != 0)
                        fio = element.getElementsByTagName("FIO").item(0).getTextContent();
                    if (element.getElementsByTagName("SEX").getLength() != 0) {
                        sex = element.getElementsByTagName("SEX").item(0).getTextContent();
                        if (sex.equals("M"))
                            gender = "GNDRMALE";
                        else if (sex.equals("F"))
                            gender = "GNDRFEMALE";
                    }
                    if (element.getElementsByTagName("ID.NUMBER").getLength() != 0)
                        id_number = element.getElementsByTagName("ID.NUMBER").item(0).getTextContent();
                    if (element.getElementsByTagName("ID.TYPE").getLength() != 0)
                        id_type = element.getElementsByTagName("ID.TYPE").item(0).getTextContent();
                    if (element.getElementsByTagName("ID.SERIES").getLength() != 0)
                        id_series = element.getElementsByTagName("ID.SERIES").item(0).getTextContent();
                    if (element.getElementsByTagName("COUNTRY").getLength() != 0)
                        country = element.getElementsByTagName("COUNTRY").item(0).getTextContent();
                    if (element.getElementsByTagName("ADDRESS.TYPE").getLength() != 0)
                        address_type = element.getElementsByTagName("ADDRESS.TYPE").item(0).getTextContent();
                    if (element.getElementsByTagName("REGION").getLength() != 0)
                        region = element.getElementsByTagName("REGION").item(0).getTextContent();
                    if (element.getElementsByTagName("CITY").getLength() != 0)
                        city = element.getElementsByTagName("CITY").item(0).getTextContent();
                    if (element.getElementsByTagName("STREET").getLength() != 0)
                        street = element.getElementsByTagName("STREET").item(0).getTextContent();
                    if (element.getElementsByTagName("HOUSE").getLength() != 0)
                        house = element.getElementsByTagName("HOUSE").item(0).getTextContent();
                    if (element.getElementsByTagName("APARTMENT").getLength() != 0)
                        apartment = element.getElementsByTagName("APARTMENT").item(0).getTextContent();
                    if (element.getElementsByTagName("CELLPHONE").getLength() != 0)
                        cellphone = element.getElementsByTagName("CELLPHONE").item(0).getTextContent();
                    if (element.getElementsByTagName("BRPART").getLength() != 0)
                        brpart = element.getElementsByTagName("BRPART").item(0).getTextContent();
                    if (element.getElementsByTagName("NAMEONCARD").getLength() != 0)
                        name_on_card = element.getElementsByTagName("NAMEONCARD").item(0).getTextContent();
                    if (element.getElementsByTagName("NATIONALITY").getLength() != 0)
                        nationality = element.getElementsByTagName("NATIONALITY").item(0).getTextContent();
                    if (element.getElementsByTagName("MARTIAL.STATUS").getLength() != 0)
                        martial_status = element.getElementsByTagName("MARTIAL.STATUS").item(0).getTextContent();
                    if (element.getElementsByTagName("EDUCATION").getLength() != 0)
                        education = element.getElementsByTagName("EDUCATION").item(0).getTextContent();
                    if (element.getElementsByTagName("BUSINESS.NATURE").getLength() != 0)
                        business_nature = element.getElementsByTagName("BUSINESS.NATURE").item(0).getTextContent();
                    if (element.getElementsByTagName("BIRTHDAY").getLength() != 0)
                        birthday = element.getElementsByTagName("BIRTHDAY").item(0).getTextContent();
                    if (element.getElementsByTagName("PLACE.OF.BIRTH").getLength() != 0)
                        place_of_birth = element.getElementsByTagName("PLACE.OF.BIRTH").item(0).getTextContent();
                    if (element.getElementsByTagName("RECEIVE.SMS").getLength() != 0)
                        receive_sms = element.getElementsByTagName("RECEIVE.SMS").item(0).getTextContent();
                    if (element.getElementsByTagName("CMSACCOUNT").getLength() != 0)
                        cms_account = element.getElementsByTagName("CMSACCOUNT").item(0).getTextContent();

                }

                org.dom4j.Element application = applications.addElement("application").addAttribute("xmlns:xsd", "http://sv.bpc.in/SVAP");
                application.addElement("application_type").addText("1");
                application.addElement("application_flow_id").addText("2");
                application.addElement("application_status").addText("3");
                application.addElement("operator_id").addText("Auto");
                application.addElement("institution_id ");
                application.addElement("agent_id").addText(brpart);
                application.addElement("customer_type ");
                application.addElement("appl_prioritized ");

                org.dom4j.Element customer = application.addElement("customer").addAttribute("id", "customer_1");
                customer.addAttribute("id", "customer_1");
                customer.addElement("command ");
                customer.addElement("customer_relation ");
                customer.addElement("customer_category").addText("OCTGFULL");
                customer.addElement("marital_status").addText(martial_status);
                customer.addElement("nationality").addText(nationality);

                org.dom4j.Element contract = customer.addElement("contract");
                contract.addElement("command ");
                contract.addElement("contract_type ");
                contract.addElement("product_id").addText(card_prefix);
                contract.addElement("start_date").addText("2021-07-03");

                org.dom4j.Element card = contract.addElement("card");
                card.addAttribute("id", "card_1");
                card.addAttribute("value", "card_1");
                card.addElement("command ");
                card.addElement("card_type ");

                org.dom4j.Element cardholder = card.addElement("cardholder");
                cardholder.addElement("cardholder_name").addText(name_on_card);
                cardholder.addElement("command ");

                org.dom4j.Element person = cardholder.addElement("person");
                person.addElement("command ");
                person.addElement("birthday").addText(birthday);
                person.addElement("place_of_birth").addText(place_of_birth);
                person.addElement("gender").addText(gender);

                org.dom4j.Element identity_card = person.addElement("identity_card");
                identity_card.addElement("command ");
                identity_card.addElement("id_type ");
                identity_card.addElement("id_series ");
                identity_card.addElement("id_number").addText(id_number);

                org.dom4j.Element contact = cardholder.addElement("contact");
                contact.addElement("command ");
                contact.addElement("contact_type ");

                org.dom4j.Element contact_data = contact.addElement("contact_data");
                contact_data.addElement("commun_method ");
                contact_data.addElement("commun_address").addText(cellphone);
                contact_data.addElement("start_date").addText("2021-07-03T00:00:00");

                org.dom4j.Element contact2 = cardholder.addElement("contact");
                contact2.addElement("command ");
                contact2.addElement("contact_type ");

                org.dom4j.Element contact_data2 = contact2.addElement("contact_data");
                contact_data2.addElement("commun_method ");
                contact_data2.addElement("commun_address").addText(cellphone);
                contact_data2.addElement("start_date").addText("2021-07-03T00:00:00");

                org.dom4j.Element address = cardholder.addElement("address");
                address.addElement("command ");
                address.addElement("address_type").addText(address_type);
                address.addElement("country").addText(country);

                org.dom4j.Element address_name = address.addElement("address_name");
                address_name.addElement("region").addText(region);
                address_name.addElement("city").addText(city);
                address_name.addElement("street").addText(street);
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
                person1.addElement("birthday").addText(birthday);
                person1.addElement("place_of_birth").addText(place_of_birth);
                person1.addElement("gender").addText(gender);

                org.dom4j.Element identity_card2 = person1.addElement("identity_card");
                identity_card2.addElement("command ");
                identity_card2.addElement("id_type ");
                identity_card2.addElement("id_series ");
                identity_card2.addElement("id_number").addText(id_number);

                org.dom4j.Element contact3 = customer.addElement("contact");
                contact3.addElement("command ");
                contact3.addElement("contact_type ");
                org.dom4j.Element contact_data3 = contact3.addElement("contact_data");
                contact_data3.addElement("commun_method ");
                contact_data3.addElement("commun_address").addText(cellphone);
                contact_data3.addElement("start_date").addText("2021-07-03T00:00:00");

                org.dom4j.Element contact4 = customer.addElement("contact");
                contact4.addElement("command ");
                contact4.addElement("contact_type ");
                org.dom4j.Element contact_data4 = contact4.addElement("contact_data");
                contact_data4.addElement("commun_method ");
                contact_data4.addElement("commun_address").addText(cellphone);
                contact_data4.addElement("start_date").addText("2021-07-03T00:00:00");

                org.dom4j.Element address2 = customer.addElement("address");
                address2.addElement("command ");
                address2.addElement("address_type").addText(address_type);
                address2.addElement("country").addText(country);
                org.dom4j.Element address_name2 = address2.addElement("address_name");
                address_name2.addElement("region").addText(region);
                address_name2.addElement("city").addText(city);
                address_name2.addElement("street").addText(street);
                address2.addElement("house").addText("");
            }

            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer;
            writer = new XMLWriter(System.out, format);
            writer.write(document);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
}