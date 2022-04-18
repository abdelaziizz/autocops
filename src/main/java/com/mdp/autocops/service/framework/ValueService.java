package com.mdp.autocops.service.framework;

import com.mdp.autocops.model.entity.DefaultValue;
import com.mdp.autocops.model.entity.TagValue;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import java.util.List;

public interface ValueService {

    TagValue createTag(TagValue tagValue);

    DefaultValue createDefault(String tag_name, DefaultValue defaultValue);

    TagValue deleteTag(String tag_name);

    DefaultValue deleteDefault(Long id);

    List<TagValue> getAllTag();

    List<DefaultValue> getAllDefault();

    List<DefaultValue> getAllByTag(String tag_name);




}
