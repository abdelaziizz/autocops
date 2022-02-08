package com.mdp.autocops.service.impl;

import com.mdp.autocops.dao.FieldTypeDao;
import com.mdp.autocops.model.entity.FieldType;
import com.mdp.autocops.service.framework.FieldTypeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class FieldTypeServiceImpl implements FieldTypeService {

    @Autowired
    FieldTypeDao fieldTypeDao;

    @Override
    public List<FieldType> getAll() {
        List<FieldType> types = new ArrayList<>();
        try {
            types = fieldTypeDao.findAll();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return types;
    }

    @Override
    public FieldType create(String type) {
        FieldType fieldType = new FieldType();
        fieldType.setField_type(type);

        try {
            fieldTypeDao.save(fieldType);
            return fieldType;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public FieldType delete(long id) {
        try {
            Optional<FieldType> fieldType = fieldTypeDao.findById(id);
            if (!fieldType.isPresent()) {
                log.error("There is no field type with id {}", id);
                return null;
            } else {
                fieldTypeDao.delete(fieldType.get());
                return fieldType.get();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public FieldType getById(long id) {
        try {
            Optional<FieldType> fieldType = fieldTypeDao.findById(id);
            if (!fieldType.isPresent()) {
                log.error("There is no field type with id {}", id);
                return null;
            } else {
                return fieldType.get();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public FieldType put(long id, String newType) {
        try {
            Optional<FieldType> fieldType = fieldTypeDao.findById(id);
            if (!fieldType.isPresent()) {
                log.error("There is no field type with id {}", id);
                return null;
            } else {
                fieldType.get().setField_type(newType);
                fieldTypeDao.save(fieldType.get());
                return fieldType.get();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
