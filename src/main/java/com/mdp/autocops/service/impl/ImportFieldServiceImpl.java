package com.mdp.autocops.service.impl;

import com.mdp.autocops.dao.ImportFieldDao;
import com.mdp.autocops.model.entity.ImportField;
import com.mdp.autocops.model.entity.ServiceEntity;
import com.mdp.autocops.service.framework.ImportFieldService;
import com.mdp.autocops.service.framework.ServiceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class ImportFieldServiceImpl implements ImportFieldService {

    @Autowired
    ImportFieldDao importFieldDao;

    @Autowired
    ServiceService serviceService;

    @Override
    public List<ImportField> getAll() {
        List<ImportField> importFields = null;
        try {
            importFields = importFieldDao.findAll();
        } catch (Exception e) {
            log.error(e);
        }
        return importFields;
    }

    @Override
    public ImportField getById(long id) {
        try {
            Optional<ImportField> importField = importFieldDao.findById(id);
            if (importField.isPresent()) return importField.get();
            else {
                log.error("Import Field Not Present");
                return null;
            }
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    @Override
    public List<ImportField> getAllByService(long service_id) {
        try {
            ServiceEntity service = serviceService.getById(service_id);
            List<ImportField> fields = importFieldDao.findAll();
            List<ImportField> serviceFields = new ArrayList<>();
            for (int i = 0; i < fields.size(); i++) {
                if (fields.get(i).getService() == service) serviceFields.add(fields.get(i));
            }
            return serviceFields;
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    @Override
    public ImportField create(long service_id, String field_name) {
        ImportField importField = new ImportField();
        try {
            ServiceEntity service = serviceService.getById(service_id);
            importField.setService(service);
            importField.setField_name(field_name);
            importFieldDao.save(importField);
            return importField;
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    @Override
    public ImportField put(long id, long service_id, String field_name) {
        try {
            ImportField field = getById(id);
            ServiceEntity service = serviceService.getById(service_id);
            if (field != null) {
                if (service != null) field.setService(service);
                if (field_name != null) field.setField_name(field_name);
                importFieldDao.save(field);
                return field;
            } else {
                log.error("Import Field Not Found");
                return null;
            }
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    @Override
    public ImportField delete(long id) {
        try {
            Optional<ImportField> importField = importFieldDao.findById(id);
            if (importField.isPresent()) {
                importFieldDao.delete(importField.get());
                return importField.get();
            } else {
                log.error("Import Field Not Found");
                return null;
            }
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

}
