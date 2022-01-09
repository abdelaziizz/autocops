package com.mdp.autocops.service.framework;

import com.mdp.autocops.model.entity.Institution;

import java.util.List;

public interface InstitutionService {

    List<Institution> getAll();

    Institution getById(long id);

    Institution delete(long id);

    Institution put(long id, Institution institution);

    Institution create(long id, String name);
}
