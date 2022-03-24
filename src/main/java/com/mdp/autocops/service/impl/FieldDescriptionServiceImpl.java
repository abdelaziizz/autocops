package com.mdp.autocops.service.impl;

import com.mdp.autocops.dao.FieldDescriptionDao;
import com.mdp.autocops.model.entity.FieldDescription;
import com.mdp.autocops.service.framework.FieldDescriptionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class FieldDescriptionServiceImpl  implements FieldDescriptionService {

    @Autowired
    FieldDescriptionDao descriptionDao;

    @Override
    public List<FieldDescription> getAll() {
       try {
           return descriptionDao.findAll();
       } catch (Exception e) {
           log.error(e.getMessage());
           return null;
       }
    }

    @Override
    public FieldDescription getById(long id) {
        try {
            Optional<FieldDescription> fDescription = descriptionDao.findById(id);
            if (fDescription.isPresent()) {
                FieldDescription fieldDescription = fDescription.get();
                return fieldDescription;
            }
            else {
                log.error("Field Description not found");
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public FieldDescription getByName(String field_name) {
       try {
           List<FieldDescription> descriptions = descriptionDao.findAll();
           List<FieldDescription> correctDescriptions = new ArrayList<>();
           for (int i = 0 ; i < descriptions.size() ; i++) {
               if (descriptions.get(i).getField_name().equals(field_name)) correctDescriptions.add(descriptions.get(i));
           }
           if (correctDescriptions.size() > 1) {
               log.error("More than one Field Description has the same Field Name");
               return null;
           }
           else {
               if (correctDescriptions.size() == 0) {
                   log.error("There are no Field Descriptions with that Field Name");
                   return null;
               } else return correctDescriptions.get(0);
           }
       } catch (Exception e) {
           log.error(e.getMessage());
           return null;
       }
    }

    @Override
    public FieldDescription create(String field_name, String description) {
        FieldDescription fieldDescription = new FieldDescription();
        fieldDescription.setField_name(field_name);
        fieldDescription.setDescription(description);
        try {
            descriptionDao.save(fieldDescription);
            return fieldDescription;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public FieldDescription put(long id, String field_name, String description) {

        try {
            Optional<FieldDescription> fDescription = descriptionDao.findById(id);
            if (fDescription.isPresent()) {
                FieldDescription fieldDescription = fDescription.get();
                if (field_name != null) fieldDescription.setField_name(field_name);
                if (description != null) fieldDescription.setDescription(description);
                descriptionDao.save(fieldDescription);
                return fieldDescription;
            }
            else {
                log.error("Field Description not present");
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public FieldDescription delete(long id) {
        try {
            Optional<FieldDescription> fDescription = descriptionDao.findById(id);
            if (fDescription.isPresent()) {
                FieldDescription fieldDescription = fDescription.get();
                descriptionDao.delete(fieldDescription);
                return fieldDescription;
            }
            else {
                log.error("Field Description not found");
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
