package com.mdp.autocops.service.impl;

import com.mdp.autocops.dao.ServicesDao;
import com.mdp.autocops.model.entity.ServiceEntity;
import com.mdp.autocops.service.framework.ServiceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    ServicesDao servicesDao;

    @Override
    public List<ServiceEntity> getAll() {
        List<ServiceEntity> services = new ArrayList<>();
        try {
            services = servicesDao.findAll();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return services;
    }

    @Override
    public ServiceEntity create(String type, String description, boolean active) {
        ServiceEntity serviceEntity = new ServiceEntity();
        try {
            serviceEntity.setService_name(type);
            serviceEntity.setDescription(description);
            serviceEntity.setActive(active);
            servicesDao.save(serviceEntity);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return serviceEntity;
    }

    @Override
    public ServiceEntity put(long id, String type, String description, Boolean active) {
        Optional<ServiceEntity> serviceEntity = null;
        try {
            serviceEntity = servicesDao.findById(id);
            if (serviceEntity.isPresent()) {
                if (type != null) serviceEntity.get().setService_name(type);
                if (description != null) serviceEntity.get().setDescription(description);
                if (active != null) serviceEntity.get().setActive(active);
                servicesDao.save(serviceEntity.get());
            } else {
                log.info("Error retrieving service");
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return serviceEntity.get();
    }

    @Override
    public ServiceEntity delete(long id) {
        Optional<ServiceEntity> serviceEntity = null;
        try {
            serviceEntity = servicesDao.findById(id);
            if (serviceEntity.isPresent()) {
                servicesDao.delete(serviceEntity.get());
            } else {
                log.info("Error retrieving service");
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return serviceEntity.get();
    }

    @Override
    public ServiceEntity getById(long id) {
        Optional<ServiceEntity> serviceEntity = null;
        try {
            serviceEntity = servicesDao.findById(id);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return serviceEntity.get();
    }
}
