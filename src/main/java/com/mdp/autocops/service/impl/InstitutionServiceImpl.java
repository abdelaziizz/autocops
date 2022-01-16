package com.mdp.autocops.service.impl;

import com.mdp.autocops.dao.InstitutionsDao;
import com.mdp.autocops.model.entity.Institution;
import com.mdp.autocops.service.framework.InstitutionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class InstitutionServiceImpl implements InstitutionService {

    @Autowired
    InstitutionsDao institutionsDao;

    @Override
    public List<Institution> getAll() {
        List<Institution> institutions = new ArrayList<>();
        try {
            institutions = institutionsDao.findAll();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return institutions;
    }

    @Override
    public Institution getById(long id) {
        Optional<Institution> institution = null;
        try {
            institution = institutionsDao.findById(id);

        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return institution.get();
    }

    @Override
    public Institution delete(long id) {
        Optional<Institution> institution = null;
        try {
            institution = institutionsDao.findById(id);
            if (institution.isPresent()) {
                institutionsDao.delete(institution.get());
            } else log.info("Error retreiving this institution");
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return institution.get();
    }

    @Override
    public Institution put(long id, Institution name) {
        Optional<Institution> institution = null;
        try {
            institution = institutionsDao.findById(id);
            if (institution.isPresent()) {
                institution.get().setInst_name(name.getInst_name());
                institutionsDao.save(institution.get());
            } else log.info("Error retreiving this institution");
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return institution.get();
    }

    @Override
    public Institution create(long id, String name) {
        try {
            Institution institution = new Institution();
            institution.setInst_id(id);
            institution.setInst_name(name);
            return institutionsDao.save(institution);
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }
}
