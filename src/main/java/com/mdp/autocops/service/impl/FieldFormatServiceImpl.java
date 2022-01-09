package com.mdp.autocops.service.impl;

import com.mdp.autocops.dao.FieldFormatDao;
import com.mdp.autocops.dao.FieldTypeDao;
import com.mdp.autocops.model.entity.FieldFormat;
import com.mdp.autocops.model.entity.FieldType;
import com.mdp.autocops.service.framework.FieldFormatService;
import com.mdp.autocops.service.framework.FieldTypeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class FieldFormatServiceImpl implements FieldFormatService {

    @Autowired
    FieldFormatDao fieldFormatDao;

    @Autowired
    FieldTypeService fieldTypeService;

    @Override
    public List<FieldFormat> getAll() {
        List<FieldFormat> formats = new ArrayList<>();
        try {
            formats = fieldFormatDao.findAll();
            return formats;
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public FieldFormat create(long type_id, String format) {
        FieldFormat fieldFormat = new FieldFormat();
        try {
            FieldType type = fieldTypeService.getById(type_id);
            if (type!=null) {
                fieldFormat.setFieldType(type);
                fieldFormat.setFormat(format);
                fieldFormatDao.save(fieldFormat);
                return fieldFormat;
            }
            else{
                log.error("cannot find field type with this id");
                return null;
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public FieldFormat getById(long id) {
        try {
            Optional<FieldFormat> fieldFormat = fieldFormatDao.findById(id);
            if (!fieldFormat.isPresent()) {
                log.error("There is no field format with id {}",id);
                return null;
            }
            else {
                return fieldFormat.get();
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public FieldFormat delete(long id) {
        FieldFormat format = getById(id);
        try {
            fieldFormatDao.delete(format);
            return format;
        }
        catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public FieldFormat put(long id, Long type_id, String newFormat) {
        FieldFormat format = getById(id);
        try {
            FieldType type = fieldTypeService.getById(type_id);
            if (type != null) format.setFieldType(type);
            if (newFormat != null) format.setFormat(newFormat);
            fieldFormatDao.save(format);
            return format;
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<FieldFormat> getAllByType(long typeId) {
        List<FieldFormat> formats = getAll();
        List<FieldFormat> typeFormats = new ArrayList<>();
        if ( formats != null && formats.size()>0) {
            for (int i = 0; i < formats.size(); i++) {
                if (formats.get(i).getFieldType().getId() == typeId) typeFormats.add(formats.get(i));
            }
            return typeFormats;
        }
        else return null;
    }
}
