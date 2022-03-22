package com.mdp.autocops.controller;

import generatedSources.cxf.ru.bpc.svxp.omnichannels.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/integration")
public class IntegrationController {

    private final OmniChannelsPort omniChannelsPort;

    @ResponseBody
    @GetMapping("/fetch")
    public String fetchData () throws OmniChannelsException {
        RequestProducts products = new RequestProducts();
        products.setInstId(8888);
        products.setOmniVersion("2.0");
        products.setLang("LANGENG");
        Products list = omniChannelsPort.products(products);
        for (int i = 0 ; i < list.getProduct().size() ; i++) {
            System.out.println("product number is : " + list.getProduct().get(i).getProductNumber());
            System.out.println("product name is : " + list.getProduct().get(i).getProductName().getName() + " ; description is : " +list.getProduct().get(i).getProductName().getDescription() + " ; language is : " + list.getProduct().get(i).getProductName().getLanguage());
            System.out.println("product status is : " + list.getProduct().get(i).getProductStatus());
            System.out.println("product type is : " + list.getProduct().get(i).getProductType());
            System.out.println("contract type is : " + list.getProduct().get(i).getContractType());


            CustomerTypes types = list.getProduct().get(i).getCustomerTypes();
            System.out.print("customer types are : ");
            for (int j = 0 ; j < types.getCustomerType().size() ; j++) {
                System.out.print(types.getCustomerType().get(j) +"  ");
            }
            System.out.println("  ");

            List<ProductAccounts> accounts = list.getProduct().get(i).getProductAccounts();
            System.out.println("product accounts are : ");
            for (int k = 0 ; k < accounts.size() ; k++) {
                System.out.println("account type is : " +accounts.get(k).getAccountType() + " ; account currency is : " + accounts.get(k).getCurrency() + " ; service number is : " + accounts.get(k).getServiceNumber());
            }
            List<ProductCards> cards = list.getProduct().get(i).getProductCards();
            System.out.println("product cards are : ");
            for (int l = 0 ; l < cards.size() ; l++) {
                System.out.println("card type id is : " +cards.get(l).getCardTypeId() + " ; service number is : " + cards.get(l).getServiceNumber());
            }
            List<ProductService> services = list.getProduct().get(i).getProductService();
            System.out.println("product service is : " + list.getProduct().get(i).getProductService());
            for (int m = 0 ; m < services.size() ; m++) {
                System.out.println("service number : "+services.get(m).getServiceNumber());
                System.out.println("service status : "+services.get(m).getServiceStatus());
                System.out.println("service type id : "+services.get(m).getServiceTypeId());
                System.out.println("service max count : "+services.get(m).getMaxCount());
                System.out.println("service min count : "+services.get(m).getMinCount());
                System.out.println("service attribute values : ");
                for (int n = 0 ; n < services.get(m).getAttributeValue().size() ; n++) {
                    System.out.println(services.get(m).getAttributeValue().get(n).getAttributeName());
                    System.out.println(services.get(m).getAttributeValue().get(n).getDefinitionLevel());
                    System.out.println(services.get(m).getAttributeValue().get(n).getStartDate());
                    System.out.println(services.get(m).getAttributeValue().get(n).getEndDate());
                    System.out.println(services.get(m).getAttributeValue().get(n).getValueChar());
                    System.out.println(services.get(m).getAttributeValue().get(n).getEntityType());
                    System.out.println(services.get(m).getAttributeValue().get(n).getModifierFlag());
                    System.out.println(services.get(m).getAttributeValue().get(n).getObjectId());
                    System.out.println(services.get(m).getAttributeValue().get(n).getObjectNumber());
                    System.out.println(services.get(m).getAttributeValue().get(n).getValueCycle());
                    System.out.println(services.get(m).getAttributeValue().get(n).getValueDate());
                    System.out.println(services.get(m).getAttributeValue().get(n).getValueFee());
                    System.out.println(services.get(m).getAttributeValue().get(n).getValueChar());
                }
                System.out.println("service names : ");
                for (int o = 0 ; o < services.get(m).getServiceName().size() ; o++) {
                    System.out.println(services.get(m).getServiceName().get(o).getName() + " ; " + services.get(m).getServiceName().get(o).getLanguage() + " ; " +services.get(m).getServiceName().get(o).getDescription());
                }
            }
            System.out.println("-----------------------------------------------------------------------");
        }
        return "success";
    }

}
