package com.mdp.autocops.service.impl;

import com.mdp.autocops.dao.InstitutionsConfigMappingDao;
import com.mdp.autocops.model.entity.ExportField;
import com.mdp.autocops.model.entity.ImportField;
import com.mdp.autocops.model.entity.InstitutionConfig;
import com.mdp.autocops.model.entity.InstitutionsConfigMapping;
import com.mdp.autocops.service.framework.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class InstitutionConfigMappingServiceImpl implements InstitutionConfigMappingService {

    @Autowired
    InstitutionsConfigMappingDao institutionsConfigMappingDao;

    @Autowired
    InstitutionConfigService institutionConfigService;

    @Autowired
    FieldTypeService fieldTypeService;

    @Autowired
    FieldFormatService fieldFormatService;

    @Autowired
    ExportFieldService exportFieldService;

    @Autowired
    ImportFieldService importFieldService;

    @Override
    public List<InstitutionsConfigMapping> getAll() {
        List<InstitutionsConfigMapping> institutionsConfigMappings = new ArrayList<>();
        try {
            institutionsConfigMappings = institutionsConfigMappingDao.findAll();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return institutionsConfigMappings;
    }

    @Override
    public InstitutionsConfigMapping getById(long id) {
        Optional<InstitutionsConfigMapping> institutionsConfigMapping = null;
        try {
            institutionsConfigMapping = institutionsConfigMappingDao.findById(id);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return institutionsConfigMapping.get();
    }

    @Override
    public InstitutionsConfigMapping create(long configId, int imp_field_index, long typeId, long format_id, long exp_field, long imp_field, Integer start_index, Integer last_index) {

        InstitutionConfig config = institutionConfigService.getById(configId);
        InstitutionsConfigMapping instConfigMapping = new InstitutionsConfigMapping();
        instConfigMapping.setInstitution_config(config);
        instConfigMapping.setImport_field_index(imp_field_index);
        try {
            ExportField exportField = exportFieldService.getById(exp_field);
            ImportField importField;
            if (imp_field != -1) {
                importField = importFieldService.getById(imp_field);
            }
            else {
                importField = null;
            }
            instConfigMapping.setExport_field_head(exportField);
            instConfigMapping.setImport_field_type(fieldTypeService.getById(typeId));
            instConfigMapping.setImport_field_format(fieldFormatService.getById(format_id));
            instConfigMapping.setImport_field(importField);
            instConfigMapping.setStart_index(start_index);
            instConfigMapping.setLast_index(last_index);
            institutionsConfigMappingDao.save(instConfigMapping);
            return instConfigMapping;
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    @Override
    public InstitutionsConfigMapping delete(long id) {
        Optional<InstitutionsConfigMapping> institutionsConfigMapping = null;
        try {
            institutionsConfigMapping = institutionsConfigMappingDao.findById(id);
            if (institutionsConfigMapping.isPresent())
                institutionsConfigMappingDao.delete(institutionsConfigMapping.get());
            else log.info("Error retrieving Institution Configuration Mapping");
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return institutionsConfigMapping.get();
    }

    @Override
    public InstitutionsConfigMapping put(long id, long configId, int imp_field_index, long typeId, long formatId, long exp_field, long imp_field, int start_index, int last_index) {

        Optional<InstitutionsConfigMapping> instConfigMapping = null;
        try {
            instConfigMapping = institutionsConfigMappingDao.findById(id);
            if (instConfigMapping.isPresent()) {
                InstitutionConfig config = institutionConfigService.getById(configId);
                if (config != null) instConfigMapping.get().setInstitution_config(config);
                Integer import_field_index = imp_field_index;
                if (import_field_index != null) instConfigMapping.get().setImport_field_index(import_field_index);
                if (fieldTypeService.getById(typeId) != null)
                    instConfigMapping.get().setImport_field_type(fieldTypeService.getById(typeId));
                if (fieldFormatService.getAllByType(formatId) != null)
                    instConfigMapping.get().setImport_field_format(fieldFormatService.getById(formatId));
                ExportField exportField = exportFieldService.getById(exp_field);
                if (exportField != null) instConfigMapping.get().setExport_field_head(exportField);
                ImportField importField = importFieldService.getById(imp_field);
                if (importField != null) instConfigMapping.get().setImport_field(importField);
                instConfigMapping.get().setStart_index(start_index);
                instConfigMapping.get().setLast_index(last_index);
                institutionsConfigMappingDao.save(instConfigMapping.get());
            } else log.info("Error retrieving Institution Configuration Mapping");
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return instConfigMapping.get();
    }

    public List<InstitutionsConfigMapping> findByInstConfig(long id) {
        List<InstitutionsConfigMapping> mappings = getAll();
        List<InstitutionsConfigMapping> mappingList = new ArrayList<>();
        for (int i = 0; i < mappings.size(); i++) {
            if (mappings.get(i).getInstitution_config().getId() == id) {
                mappingList.add(mappings.get(i));
            }
        }
        return mappingList;
    }

    public List<ExportField> getAvailableExport(long config_id) {
        InstitutionConfig config = institutionConfigService.getById(config_id);
        List<InstitutionsConfigMapping> mappings = findByInstConfig(config_id);
        List<ExportField> fields = exportFieldService.getAllByService(config.getService().getService_id());
        for (int j = 0; j < mappings.size(); j++) {
            for (int i = 0; i < fields.size(); i++) {
                if (mappings.get(j).getExport_field_head().getField_name().equals(fields.get(i).getField_name())) {
                    fields.remove(i);
                }
            }
        }
        return fields;
    }

    public List<ImportField> getAvailableImport(long config_id) {
        InstitutionConfig config = institutionConfigService.getById(config_id);
        List<InstitutionsConfigMapping> mappings = findByInstConfig(config_id);
        List<ImportField> fields = importFieldService.getAllByService(config.getService().getService_id());
        for (int j = 0; j < mappings.size(); j++) {
            for (int i = 0; i < fields.size(); i++) {
                if (mappings.get(j).getImport_field().getField_name().equals(fields.get(i).getField_name())) {
                    fields.remove(i);
                }
            }
        }
        return fields;
    }
}
