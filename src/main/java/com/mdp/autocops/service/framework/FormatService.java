package com.mdp.autocops.service.framework;

import com.mdp.autocops.model.entity.Format;

import java.util.List;

public interface FormatService {

    Format create(String type);

    Format delete(long id);

    List<Format> getAll();

    Format put(long id, String type);

    Format getById(long id);
}
