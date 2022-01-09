package com.mdp.autocops.service.impl;

import com.mdp.autocops.dao.FileFormatsDao;
import com.mdp.autocops.model.entity.FileFormat;
import com.mdp.autocops.service.framework.FileFormatService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class FileFormatServiceImpl implements FileFormatService {

    @Autowired
    FileFormatsDao fileFormatsDao;

    @Override
    public FileFormat create(String type) {
        try {
            FileFormat fileFormat = new FileFormat();

            fileFormat.setFormat_type(type);
            fileFormatsDao.save(fileFormat);
            return fileFormatsDao.save(fileFormat);
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    @Override
    public FileFormat delete(long id) {
        Optional<FileFormat> format = null;
        try {
            format = fileFormatsDao.findById(id);
            if (format.isPresent()) {
                fileFormatsDao.delete(format.get());
            } else {
                log.info("Error retreiving this format");
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return format.get();
    }

    @Override
    public List<FileFormat> getAll() {
        List<FileFormat> fileFormats = new ArrayList<>();
        try {
            fileFormats = fileFormatsDao.findAll();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return fileFormats;
    }

    @Override
    public FileFormat put(long id, String type) {
        Optional<FileFormat> format = null;
        try {
            format = fileFormatsDao.findById(id);
            if (format.isPresent()) {
                format.get().setFormat_type(type);
                fileFormatsDao.save(format.get());
            } else {
                log.info("Error retreiving this format");
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return format.get();
    }

    @Override
    public FileFormat getById(long id) {
        Optional<FileFormat> format = null;
        try {
            format = fileFormatsDao.findById(id);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return format.get();
    }
}
