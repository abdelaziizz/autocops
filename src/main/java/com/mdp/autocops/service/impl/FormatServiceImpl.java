package com.mdp.autocops.service.impl;

import com.mdp.autocops.dao.FormatsDao;
import com.mdp.autocops.model.entity.Format;
import com.mdp.autocops.service.framework.FormatService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class FormatServiceImpl implements FormatService {

    @Autowired
    FormatsDao formatsDao;

    @Override
    public Format create(String type) {
        try {
            Format format = new Format();

            format.setFormat_type(type);
            formatsDao.save(format);
            return formatsDao.save(format);
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    @Override
    public Format delete(long id) {
        Optional<Format> format = null;
        try {
            format = formatsDao.findById(id);
            if (format.isPresent()) {
                formatsDao.delete(format.get());
            } else {
                log.info("Error retreiving this format");
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return format.get();
    }

    @Override
    public List<Format> getAll() {
        List<Format> formats = new ArrayList<>();
        try {
            formats = formatsDao.findAll();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return formats;
    }

    @Override
    public Format put(long id, String type) {
        Optional<Format> format = null;
        try {
            format = formatsDao.findById(id);
            if (format.isPresent()) {
                format.get().setFormat_type(type);
                formatsDao.save(format.get());
            } else {
                log.info("Error retreiving this format");
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return format.get();
    }

    @Override
    public Format getById(long id) {
        Optional<Format> format = null;
        try {
            format = formatsDao.findById(id);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return format.get();
    }
}
