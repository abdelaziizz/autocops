package com.mdp.autocops.service.impl;

import com.mdp.autocops.dao.integration.DefaultValuesDao;
import com.mdp.autocops.dao.integration.TagValuesDao;
import com.mdp.autocops.model.entity.DefaultValue;
import com.mdp.autocops.model.entity.TagValue;
import com.mdp.autocops.service.framework.ValueService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class ValuesServiceImpl implements ValueService {

    @Autowired
    TagValuesDao tagValuesDao;

    @Autowired
    DefaultValuesDao defaultValuesDao;


    @Override
    public TagValue createTag(TagValue tagValue) {
        try {
            tagValuesDao.save(tagValue);
            return tagValue;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public DefaultValue createDefault(String tag_name, DefaultValue defaultValue) {
        try {
            Optional<TagValue> tagValue = tagValuesDao.findById(tag_name);
            if (tagValue.isPresent()) {
                tagValue.get().addDefaultValue(defaultValue);
                tagValuesDao.save(tagValue.get());
                return defaultValue;
            } else {
                log.error("Tag Value not present");
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public TagValue deleteTag(String tag_name) {
        try {
            Optional<TagValue> tagValue = tagValuesDao.findById(tag_name);
            if (tagValue.isPresent()) {
                tagValuesDao.delete(tagValue.get());
                return tagValue.get();
            } else {
                log.error("Tag Value not present");
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public DefaultValue deleteDefault(Long id) {
        try {
            Optional<DefaultValue> defaultValue = defaultValuesDao.findById(id);
            if (defaultValue.isPresent()) {
                defaultValuesDao.delete(defaultValue.get());
                return defaultValue.get();
            } else {
                log.error("Tag Value not present");
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<TagValue> getAllTag() {
        try {
            List<TagValue> tagValues = tagValuesDao.findAll();
            return tagValues;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<DefaultValue> getAllDefault() {
        try {
            List<DefaultValue> defaultValues = defaultValuesDao.findAll();
            return defaultValues;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<DefaultValue> getAllByTag(String tag_name) {
        try {
            Optional<TagValue> tagValue = tagValuesDao.findById(tag_name);
            if (tagValue.isPresent()) return (List<DefaultValue>) tagValue.get().getDefaultValues();
            else {
                log.error("Tag value not found");
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
