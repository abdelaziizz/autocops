package com.mdp.autocops.service.impl.processes;

import com.mdp.autocops.model.entity.InstitutionConfig;
import com.mdp.autocops.model.entity.InstitutionsConfigMapping;
import com.mdp.autocops.service.framework.InstitutionConfigMappingService;
import com.mdp.autocops.service.framework.InstitutionConfigService;
import com.mdp.autocops.service.framework.processes.CardIssuanceService;
import lombok.extern.log4j.Log4j2;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class CardIssuanceServiceImpl implements CardIssuanceService {

    @Autowired
    InstitutionConfigMappingService mappingService;

    @Autowired
    InstitutionConfigService configService;

    @Autowired
    Read read;

    @Override
    public String issueCardADCB() {

        try {
            List<InstitutionsConfigMapping> mappings = mappingService.findByInstConfig(2);
            InstitutionConfig config = configService.getById(2);
            String fileName = config.getImport_path();
            List<Map> maps = read.readXML(fileName, mappings);
            org.dom4j.Document document = DocumentHelper.createDocument();
            org.dom4j.Element applications = document.addElement("APPLICATIONS");
            for (Map map_cur : maps) {
                Map<String, String> map = map_cur;
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

                if (map.get("agent_id") != null) {
                    agent_id.addText(map.get("agent_id"));
                }
                if (map.get("marital_status") != null) {
                    marital_status.addText(map.get("marital_status"));
                }
                if (map.get("nationality") != null) {
                    nationality.addText(map.get("nationality"));
                }
                if (map.get("product_id") != null) {
                    product_id.addText(map.get("product_id"));
                }
                if (map.get("cardholder_name") != null) {
                    cardholder_name.addText(map.get("cardholder_name"));
                }
                if (map.get("birthday") != null) {
                    birthday.addText(map.get("birthday"));
                    birthday1.addText(map.get("birthday"));
                }
                if (map.get("place_of_birth") != null) {
                    place_of_birth.addText(map.get("place_of_birth"));
                }
                if (map.get("gender") != null) {
                    gender.addText(map.get("gender"));
                    gender1.addText(map.get("gender"));
                }
                if (map.get("id_number") != null) {
                    id_number.addText(map.get("id_number"));
                    id_number1.addText(map.get("id_number"));
                }
                if (map.get("commun_address") != null) {
                    commun_address.addText(map.get("commun_address"));
                    commun_address1.addText(map.get("commun_address"));
                    commun_address2.addText(map.get("commun_address"));
                    cellphone.addText(map.get("commun_address"));
                }
                if (map.get("address_type") != null) {
                    address_type.addText(map.get("address_type"));
                    address_type1.addText(map.get("address_type"));
                }
                if (map.get("country") != null) {
                    country.addText(map.get("country"));
                    country1.addText(map.get("country"));
                }
                if (map.get("region") != null) {
                    region.addText(map.get("region"));
                    region1.addText(map.get("region"));
                }
                if (map.get("city") != null) {
                    city.addText(map.get("city"));
                    city1.addText(map.get("city"));
                }
                if (map.get("street") != null) {
                    street.addText(map.get("street"));
                    street1.addText(map.get("street"));
                }
                if (map.get("place_of_birth") != null) {
                    place_of_birth.addText(map.get("place_of_birth"));
                    place_of_birth1.addText(map.get("place_of_birth"));
                }
                if (map.get("agent_id") != null) {
                    agent_id.addText(map.get("agent_id"));
                }
                if (map.get("agent_id") != null) {
                    agent_id.addText(map.get("agent_id"));
                }
                if (map.get("agent_id") != null) {
                    agent_id.addText(map.get("agent_id"));
                }
                if (map.get("agent_id") != null) {
                    agent_id.addText(map.get("agent_id"));
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

    @Override
    public String issueInstanceADIB() {
        try {
            List<InstitutionsConfigMapping> mappings = mappingService.findByInstConfig(1);
            InstitutionConfig config = configService.getById(1);
            if (!config.getImport_File_format().getFormat_type().equals("Excel")) {
                return "Incompatible input file";
            } else {
                List<Map> maps = read.readExcel(config.getReading_line(), config.getImport_path(), mappings);
                Document document = DocumentHelper.createDocument();
                Element applications = document.addElement("APPLICATIONS");
                for (int i = 0; i < maps.size(); i++) {
                    Map<String, String> current_map = maps.get(i);
                    Element application = applications.addElement("application").addAttribute("xmlns:xsd", "http://sv.bpc.in/SVAP");
                    application.addElement("application_type");
                    application.addElement("application_flow_id");
                    application.addElement("application_status");
                    application.addElement("operator_id");
                    application.addElement("institution_id");
                    application.addElement("agent_id");
                    application.addElement("customer_type");
                    application.addElement("appl_prioritized");

                    Element customer = application.addElement("customer").addAttribute("id", "customer_1");
                    customer.addElement("command");
                    customer.addElement("customer_relation");
                    customer.addElement("customer_number");
                    customer.addElement("customer_category");
                    customer.addElement("marital_status");
                    customer.addElement("nationality");
                    customer.addElement("ADIB_CUSTOMER");
                    customer.addElement("customer_category");
                    customer.addElement("marital_status");
                    customer.addElement("nationality");
                    customer.addElement("ADIB_CUSTOMER");
                    customer.addElement("RIM_NUMBER");
                    customer.addElement("marital_status");
                    customer.addElement("EDUCATION");
                    customer.addElement("BUSINESS_NATURE");
                    customer.addElement("ADIB_SECURITY_QUESTION");
                    customer.addElement("ADIB_SECURITY_ANSWER");

                    Element contract = customer.addElement("contract");
                    contract.addElement("command");
                    contract.addElement("contract_type");
                    contract.addElement("product_id");
                    contract.addElement("start_date");
                    contract.addElement("contract_type");
                    contract.addElement("contract_type");
                    contract.addElement("contract_type");

                    Element card = contract.addElement("card").addAttribute("id", "card_1");
                    card.addElement("command");
                    card.addElement("card_type");

                    Element cardholder = card.addElement("cardholder");
                    cardholder.addElement("cardholder_name");
                    cardholder.addElement("command");
                    cardholder.addElement("Embossed_first_name");
                    cardholder.addElement("embossed_surname");

                    Element person1 = cardholder.addElement("person");
                    person1.addElement("command");

                    Element person_name1 = person1.addElement("person_name");
                    person_name1.addElement("surname");
                    person_name1.addElement("first_name");

                    person1.addElement("birthday");
                    person1.addElement("place_of_birth");
                    person1.addElement("gender");

                    Element identity_card = person1.addElement("identity_card");
                    identity_card.addElement("command");
                    identity_card.addElement("id_type");
                    identity_card.addElement("id_series");
                    identity_card.addElement("id_number");
                    identity_card.addElement("id_expire_date");

                    Element contact1 = cardholder.addElement("contact");
                    contact1.addElement("command");
                    contact1.addElement("contact_type");
                    contact1.addElement("preferred_lang");

                    Element contact_data1 = contact1.addElement("contact_data");
                    contact_data1.addElement("commun_method");
                    contact_data1.addElement("commun_address");
                    contact_data1.addElement("start_date");

                    Element contact_data_ext1 = contact1.addElement("contact_data");
                    contact_data_ext1.addElement("commun_method");
                    contact_data_ext1.addElement("preferred_lang");
                    contact_data_ext1.addElement("commun_address");
                    contact_data_ext1.addElement("start_date");

                    Element contact2 = cardholder.addElement("contact");
                    contact2.addElement("command");
                    contact2.addElement("contact_type");
                    contact2.addElement("preferred_lang");

                    Element contact_data2 = contact2.addElement("contact_data");
                    contact_data2.addElement("commun_method");
                    contact_data2.addElement("commun_address");
                    contact_data2.addElement("start_date");

                    Element contact_data_ext2 = contact2.addElement("contact_data");
                    contact_data_ext2.addElement("commun_method");
                    contact_data_ext2.addElement("preferred_lang");
                    contact_data_ext2.addElement("commun_address");
                    contact_data_ext2.addElement("start_date");

                    Element address1 = cardholder.addElement("address");
                    address1.addElement("command");
                    address1.addElement("address_type");
                    address1.addElement("country");

                    Element address_name1 = address1.addElement("address_name");
                    address_name1.addElement("region");
                    address_name1.addElement("city");
                    address_name1.addElement("street");

                    address1.addElement("house");

                    Element address2 = cardholder.addElement("address");
                    address2.addElement("command");
                    address2.addElement("address_type");
                    address2.addElement("country");

                    Element address_name2 = address2.addElement("address_name");
                    address_name2.addElement("region");
                    address_name2.addElement("city");
                    address_name2.addElement("street");

                    address2.addElement("house");

                    Element account = contract.addElement("account");
                    account.addAttribute("id","account_1");
                    account.addAttribute("value","account1");
                    account.addElement("command");
                    account.addElement("currency");
                    account.addElement("account_type");
                    account.addElement("account_status");

                    Element account_object = account.addElement("account_object");
                    account_object.addElement("account_link_flag");
                    account_object.addElement("is_pos_default");
                    account_object.addElement("is_atm_default");

                    Element service1 = contract.addElement("service");
                    service1.addAttribute("value","70000064");
                    Element service_object1 = service1.addElement("service_object");
                    service_object1.addElement("start_date");

                    Element service2 = contract.addElement("service");
                    service2.addAttribute("value","70000064");
                    Element service_object2 = service2.addElement("service_object");
                    service_object2.addElement("start_date");

                    Element service3 = contract.addElement("service");
                    service3.addAttribute("value","70000064");
                    Element service_object3 = service3.addElement("service_object");
                    service_object3.addElement("start_date");

                    Element service4 = contract.addElement("service");
                    service4.addAttribute("value","70000064");
                    Element service_object4 = service4.addElement("service_object");
                    service_object4.addElement("start_date");

                    Element service5 = contract.addElement("service");
                    service5.addAttribute("value","70000064");
                    Element service_object5 = service5.addElement("service_object");
                    service_object5.addElement("start_date");

                    Element person2 = customer.addElement("person");
                    person2.addElement("command");
                    person2.addElement("person_title");

                    Element person_name2 = person2.addElement("person_name");
                    person_name2.addAttribute("language","LANGENG");
                    person_name2.addElement("surname");
                    person_name2.addElement("first_name");
                    person_name2.addElement("second_name");

                    person2.addElement("birthday");
                    person2.addElement("place_of_birth");
                    person2.addElement("gender");

                    Element identity_card2 = person2.addElement("identity_card");
                    identity_card2.addElement("command");
                    identity_card2.addElement("id_type");
                    identity_card2.addElement("id_series");
                    identity_card2.addElement("id_number");
                    identity_card2.addElement("id_expire_date");

                    Element contact3 = customer.addElement("contact");
                    contact3.addElement("command");
                    contact3.addElement("contact_type");
                    contact3.addElement("preferred_lang");

                    Element contact_data3 = contact3.addElement("contact_data");
                    contact_data3.addElement("commun_method");
                    contact_data3.addElement("commun_address");
                    contact_data3.addElement("start_date");

                    Element contact_data_ext3 = contact3.addElement("contact_data");
                    contact_data_ext3.addElement("commun_method");
                    contact_data_ext3.addElement("preferred_lang");
                    contact_data_ext3.addElement("commun_address");
                    contact_data_ext3.addElement("start_date");

                    Element contact4 = customer.addElement("contact");
                    contact4.addElement("command");
                    contact4.addElement("contact_type");
                    contact4.addElement("preferred_lang");

                    Element contact_data4 = contact4.addElement("contact_data");
                    contact_data4.addElement("commun_method");
                    contact_data4.addElement("commun_address");
                    contact_data4.addElement("start_date");

                    Element contact_data_ext4 = contact4.addElement("contact_data");
                    contact_data_ext4.addElement("commun_method");
                    contact_data_ext4.addElement("preferred_lang");
                    contact_data_ext4.addElement("commun_address");
                    contact_data_ext4.addElement("start_date");

                    Element address3 = customer.addElement("address");
                    address3.addElement("command");
                    address3.addElement("address_type");
                    address3.addElement("country");

                    Element address_name3 = address3.addElement("address_name");
                    address_name3.addElement("region");
                    address_name3.addElement("city");
                    address_name3.addElement("street");

                    address3.addElement("house");

                    Element address4 = customer.addElement("address");
                    address4.addElement("command");
                    address4.addElement("address_type");
                    address4.addElement("country");

                    Element address_name4 = address4.addElement("address_name");
                    address_name4.addElement("region");
                    address_name4.addElement("city");
                    address_name4.addElement("street");

                    address4.addElement("house");
                }
                OutputFormat format = OutputFormat.createPrettyPrint();
                XMLWriter writer;
                writer = new XMLWriter(System.out, format);
                writer.write(document);
                return "success";
            }

        } catch (Exception e) {
            log.error(e.getStackTrace());
            return "fail";
        }
    }


}