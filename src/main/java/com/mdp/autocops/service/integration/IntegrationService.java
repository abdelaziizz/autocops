package com.mdp.autocops.service.integration;


import com.mdp.autocops.dao.integration.*;
import com.mdp.autocops.model.integration.*;
import com.mdp.autocops.model.integration.Product;
import generatedSources.cxf.ru.bpc.svxp.omnichannels.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class IntegrationService {

    @Autowired
    OmniChannelsPort omniChannelsPort;
    @Autowired
    ProductDao productDao;
    @Autowired
    CustomerTypeDao customerTypeDao;
    @Autowired
    ProductAccountDao productAccountDao;
    @Autowired
    ProductCardDao productCardDao;
    @Autowired
    ProductNameDao productNameDao;
    @Autowired
    ServiceDao serviceDao;
    @Autowired
    ServiceNameDao serviceNameDao;

    public String fetch(int id, String version, String lang) throws OmniChannelsException {

        try {
            RequestProducts products = new RequestProducts();
            products.setInstId(id);
            products.setOmniVersion(version);
            products.setLang(lang);
            Products list = omniChannelsPort.products(products);
            for (int i = 0; i < list.getProduct().size(); i++) {
                generatedSources.cxf.ru.bpc.svxp.omnichannels.Product current = list.getProduct().get(i);
                Product new_product = new Product();
                new_product.setInstitution_id(id);
                new_product.setProductNumber(current.getProductNumber());
                new_product.setProductStatus(current.getProductStatus());
                ProductName name = new ProductName();
                name.setName(current.getProductName().getName());
                name.setDescription(current.getProductName().getDescription());
                name.setLang(current.getProductName().getLanguage());
                //productNameDao.save(name);
                new_product.setProductName(name);
                new_product.setContractType(current.getProductType());
                new_product.setContractType(current.getContractType());
                CustomerTypes types = current.getCustomerTypes();
                for (int j = 0; j < types.getCustomerType().size(); j++) {
                    CustomerType new_type = new CustomerType();
                    new_type.setCustomerType(types.getCustomerType().get(j));
                    new_product.addCustomerType(new_type);
                    //customerTypeDao.save(new_type);
                }
                List<ProductAccounts> accounts = list.getProduct().get(i).getProductAccounts();
                for (int k = 0; k < accounts.size(); k++) {
                    ProductAccount new_account = new ProductAccount();
                    new_account.setAccountType(accounts.get(k).getAccountType());
                    new_account.setCurrency(accounts.get(k).getCurrency());
                    new_account.setServiceNumber(accounts.get(k).getServiceNumber());
                    new_product.addProductAccount(new_account);
                    //productAccountDao.save(new_account);
                }
                List<ProductCards> cards = list.getProduct().get(i).getProductCards();
                for (int l = 0; l < cards.size(); l++) {
                    ProductCard new_card = new ProductCard();
                    new_card.setCardTypeId(cards.get(l).getCardTypeId());
                    new_product.addProductCard(new_card);
                    //productCardDao.save(new_card);
                }
                List<ProductService> services = list.getProduct().get(i).getProductService();
                for (int m = 0; m < services.size(); m++) {
                    com.mdp.autocops.model.integration.Service new_service = new com.mdp.autocops.model.integration.Service();
                    new_service.setServiceNumber(services.get(m).getServiceNumber());
                    new_service.setServiceStatus(services.get(m).getServiceStatus());
                    new_service.setServiceTypeId(services.get(m).getServiceTypeId());
                    new_service.setMaxCount(services.get(m).getMaxCount());
                    new_service.setMinCount(services.get(m).getMinCount());
                    for (int o = 0; o < services.get(m).getServiceName().size(); o++) {
                        ServiceName new_service_name = new ServiceName();
                        new_service_name.setName(services.get(m).getServiceName().get(o).getName());
                        new_service_name.setDescription(services.get(m).getServiceName().get(o).getDescription());
                        new_service_name.setLang(services.get(m).getServiceName().get(o).getLanguage());
                        new_service.addServiceName(new_service_name);
                        //serviceNameDao.save(new_service_name);
                    }
                    //serviceDao.save(new_service);
                    new_product.addService(new_service);
                }
                if (!productDao.findById(new_product.getProductNumber()).isPresent()) {
                    productDao.save(new_product);
                    log.info("Product with id : {} saved successfully in database",new_product.getProductNumber());
                }
                else {
                    log.info("Product with id : {} already exists in database",new_product.getProductNumber());
                }
            } return "success";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "fail";
        }
    }
}