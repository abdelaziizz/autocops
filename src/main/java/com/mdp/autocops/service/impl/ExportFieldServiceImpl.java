package com.mdp.autocops.service.impl;

import com.mdp.autocops.dao.ExportFieldDao;
import com.mdp.autocops.model.entity.ExportField;
import com.mdp.autocops.model.entity.ServiceEntity;
import com.mdp.autocops.service.framework.ExportFieldService;
import com.mdp.autocops.service.framework.ServiceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class ExportFieldServiceImpl implements ExportFieldService {

    @Autowired
    ExportFieldDao exportFieldDao;

    @Autowired
    ServiceService serviceService;

    @Override
    public List<ExportField> getAll() {
        List<ExportField> exportFields = null;
        try {
            exportFields = exportFieldDao.findAll();
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        return exportFields;
    }

    @Override
    public ExportField getById(long id) {
        try {
            Optional<ExportField> exportField = exportFieldDao.findById(id);
            if (exportField.isPresent()) return exportField.get();
            else {
                log.error("Export Field Not Present");
                return null;
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<ExportField> getAllByService(long service_id) {
        try {
            ServiceEntity service = serviceService.getById(service_id);
            List<ExportField> fields = exportFieldDao.findAll();
            List<ExportField> serviceFields = new ArrayList<>();
            for (int i = 0 ; i < fields.size() ; i++ ) {
                if (fields.get(i).getService() == service) serviceFields.add(fields.get(i));
            }
            return serviceFields;
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public ExportField create(long service_id, String field_name) {
        ExportField exportField = new ExportField();
        try {
            ServiceEntity service = serviceService.getById(service_id);
            exportField.setService(service);
            exportField.setField_name(field_name);
            exportFieldDao.save(exportField);
            return exportField;
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public ExportField put(long id, long service_id, String field_name) {
        try{
            ExportField field = getById(id);
            ServiceEntity service = serviceService.getById(service_id);
            if (field != null) {
                if (service != null) field.setService(service);
                if (field_name != null) field.setField_name(field_name);
                exportFieldDao.save(field);
                return field;
            }
            else {
                log.error("Export Field Not Found");
                return null;
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public ExportField delete(long id) {
        try {
            Optional<ExportField> exportField = exportFieldDao.findById(id);
            if (exportField.isPresent()) {
                exportFieldDao.delete(exportField.get());
                return exportField.get();
            }
            else {
                log.error("Export Field Not Found");
                return null;
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

}
