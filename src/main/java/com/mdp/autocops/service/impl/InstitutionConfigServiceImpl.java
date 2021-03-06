package com.mdp.autocops.service.impl;

import com.mdp.autocops.dao.InstitutionsConfigDao;
import com.mdp.autocops.dao.integration.ProductDao;
import com.mdp.autocops.model.entity.Institution;
import com.mdp.autocops.model.entity.InstitutionConfig;
import com.mdp.autocops.model.entity.ServiceEntity;
import com.mdp.autocops.model.integration.Product;
import com.mdp.autocops.service.framework.*;
import generatedSources.cxf.ru.bpc.svxp.omnichannels.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class InstitutionConfigServiceImpl implements InstitutionConfigService {

    @Autowired
    InstitutionsConfigDao institutionsConfigDao;

    @Autowired
    InstitutionConfigMappingService institutionConfigMappingService;

    @Autowired
    InstitutionService institutionService;

    @Autowired
    ServiceService serviceService;

    @Autowired
    ProductDao productDao;

    @Override
    public InstitutionConfig create(long instId, Integer reading_line, String import_format, String export_format, Boolean fail_on_error, Boolean active,
                                    long service_id, String import_path, String export_path, String template_path, String reading_root, String writing_root,
                                    Integer last_lines, String import_date, String export_date, String product_id, String file_prefix) {
        InstitutionConfig institutionConfigNew = new InstitutionConfig();
        Institution inst = new Institution();
        ServiceEntity service = new ServiceEntity();
        try {
            inst = institutionService.getById(instId);
            service = serviceService.getById(service_id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        institutionConfigNew.setInstitution(inst);
        institutionConfigNew.setReading_line(reading_line);
        institutionConfigNew.setLast_lines(last_lines);
        institutionConfigNew.setImport_File_format(import_format);
        institutionConfigNew.setExport_File_format(export_format);
        institutionConfigNew.setImport_path(import_path);
        institutionConfigNew.setExport_path(export_path);
        institutionConfigNew.setFail_on_error(fail_on_error);
        institutionConfigNew.setService(service);
        institutionConfigNew.setActive(active);
        institutionConfigNew.setTemplate_path(template_path);
        institutionConfigNew.setReading_root(reading_root);
        institutionConfigNew.setExport_date(export_date);
        institutionConfigNew.setImport_date(import_date);
        institutionConfigNew.setWriting_root(writing_root);
        institutionConfigNew.setProduct_id(product_id);
        institutionConfigNew.setFile_prefix(file_prefix);
        try {
            if (getAvailableServices(institutionConfigNew.getInstitution().getInst_id()).contains(institutionConfigNew.getService())) {
                institutionsConfigDao.save(institutionConfigNew);
                return institutionConfigNew;
            } else {
                log.info("There is already a configuration with this service for this institution");
                return null;
            }

        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    @Override
    public List<InstitutionConfig> getAll() {
        List<InstitutionConfig> institutionConfigs = new ArrayList<>();
        try {
            institutionConfigs = institutionsConfigDao.findAll();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return institutionConfigs;
    }

    @Override
    public InstitutionConfig getById(long id) {
        Optional<InstitutionConfig> institutionConfig = null;
        try {
            institutionConfig = institutionsConfigDao.findById(id);
            if (!institutionConfig.isPresent()) log.info("Error retrieving institution configuration");
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return institutionConfig.get();
    }

    @Override
    public InstitutionConfig delete(long id) {
        Optional<InstitutionConfig> institutionConfig = null;
        try {
            institutionConfig = institutionsConfigDao.findById(id);
            if (!institutionConfig.isPresent()) {
                log.info("Error retrieving institution configuration");
                return null;
            } else {
                if (institutionConfigMappingService.findByInstConfig(id).size() == 0) {
                    institutionsConfigDao.delete(institutionConfig.get());
                    return institutionConfig.get();
                } else {
                    log.info("There are dependants on this Institution's Configuration");
                    return null;
                }

            }
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    @Override
    public InstitutionConfig put(long id, Integer reading_line, String import_format, String export_format, Boolean fail_on_error, Boolean active,
                                 long service_id, String import_path, String export_path, String template_path, String reading_root, String writing_root,
                                 Integer last_lines, String import_date, String export_date, String product_id, String file_prefix) {
        Optional<InstitutionConfig> institutionConfigUpdate = null;
        ServiceEntity service = serviceService.getById(service_id);

        try {
            institutionConfigUpdate = institutionsConfigDao.findById(id);
            if (!institutionConfigUpdate.isPresent()) log.info("Error retrieving institution configuration");
            else {
                if (reading_line != null) institutionConfigUpdate.get().setReading_line(reading_line);
                if (last_lines != null) institutionConfigUpdate.get().setLast_lines(last_lines);
                if (import_format != null) institutionConfigUpdate.get().setImport_File_format(import_format);
                if (export_format != null) institutionConfigUpdate.get().setExport_File_format(export_format);
                if (fail_on_error != null) institutionConfigUpdate.get().setFail_on_error(fail_on_error);
                if (active != null) institutionConfigUpdate.get().setActive(active);
                if (service != null) institutionConfigUpdate.get().setService(service);
                if (import_path != null) institutionConfigUpdate.get().setImport_path(import_path);
                if (export_path != null) institutionConfigUpdate.get().setExport_path(export_path);
                if (template_path != null) institutionConfigUpdate.get().setTemplate_path(template_path);
                if (reading_root != null) institutionConfigUpdate.get().setReading_root(reading_root);
                if (import_date != null) institutionConfigUpdate.get().setImport_date(import_date);
                if (export_date != null) institutionConfigUpdate.get().setExport_date(export_date);
                if (writing_root != null) institutionConfigUpdate.get().setWriting_root(writing_root);
                if (product_id != null) institutionConfigUpdate.get().setProduct_id(product_id);
                if (file_prefix != null) institutionConfigUpdate.get().setFile_prefix(file_prefix);
                institutionsConfigDao.save(institutionConfigUpdate.get());

            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return institutionConfigUpdate.get();
    }

    @Override
    public List<InstitutionConfig> getByInst(long id) {
        List<InstitutionConfig> configs = getAll();
        List<InstitutionConfig> instConfigs = new ArrayList<>();
        for (int i = 0; i < configs.size(); i++) {
            if (configs.get(i).getInstitution().getInst_id() == id) instConfigs.add(configs.get(i));
        }
        return instConfigs;
    }

    @Override
    public List<ServiceEntity> getAvailableServices(long id) {
        List<ServiceEntity> services = serviceService.getAll();
        List<InstitutionConfig> configs = getByInst(id);
        for (int i = 0; i < configs.size(); i++) {
            services.remove(configs.get(i).getService());
        }
        return services;
    }

    @Override
    public List<Product> getInstProducts(long inst_id) {
        try {
            List<Product> products = productDao.findAll();
            for (int i = 0 ; i < products.size() ; i++) {
                if (products.get(i).getInstitution_id() != inst_id) products.remove(i);
            }
            return products;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
