package com.mdp.autocops.service.framework;

import com.mdp.autocops.model.entity.FilePrefix;
import java.util.List;

public interface FilePrefixService {

    List<FilePrefix> getAll();

    FilePrefix create(String prefix);

    FilePrefix update(long id, String prefix);

    FilePrefix delete(long id);
}
