package com.mdp.autocops.service.framework;

import com.mdp.autocops.model.entity.ServiceEntity;

import java.util.List;

public interface ServiceService {

    List<ServiceEntity> getAll();

    ServiceEntity create(String type, String description, boolean active);

    ServiceEntity put(long id, String type, String description, Boolean active);

    ServiceEntity delete(long id);

    ServiceEntity getById(long id);

}
