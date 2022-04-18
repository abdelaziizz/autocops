package com.mdp.autocops.service.impl;

import com.mdp.autocops.dao.integration.FilePrefixDao;
import com.mdp.autocops.model.entity.FilePrefix;
import com.mdp.autocops.service.framework.FilePrefixService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class FilePrefixServiceImpl implements FilePrefixService {

    @Autowired
    FilePrefixDao prefixDao;

    @Override
    public List<FilePrefix> getAll() {
        try {
            return prefixDao.findAll();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public FilePrefix create(String prefix) {
        FilePrefix filePrefix = new FilePrefix();
        filePrefix.setPrefix(prefix);
        try {
            prefixDao.save(filePrefix);
            return filePrefix;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public FilePrefix update(long id, String prefix) {
        try {
            Optional<FilePrefix> filePrefix = prefixDao.findById(id);
            if (filePrefix.isPresent()) {
                filePrefix.get().setPrefix(prefix);
                prefixDao.save(filePrefix.get());
                return filePrefix.get();
            } else {
                log.error("This prefix is not available");
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public FilePrefix delete(long id) {
        try {
            Optional<FilePrefix> filePrefix = prefixDao.findById(id);
            if (filePrefix.isPresent()) {
                prefixDao.delete(filePrefix.get());
                return filePrefix.get();
            } else {
                log.error("This prefix is not available");
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
