package com.mdp.autocops.controller;

import com.mdp.autocops.model.entity.DefaultValue;
import com.mdp.autocops.model.entity.TagValue;
import com.mdp.autocops.service.framework.ValueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/values")
public class ValuesController {

    private final ValueService valueService;

    @PostMapping("/tag")
    public TagValue createTag (@RequestBody TagValue tagValue) {
        return valueService.createTag(tagValue);
    }

    @PostMapping("/default/{tag_name}")
    public DefaultValue createDefault (@PathVariable String tag_name, @RequestBody DefaultValue defaultValue) {
        return valueService.createDefault(tag_name, defaultValue);
    }

    @DeleteMapping("/tag/{tag_name}")
    public TagValue deleteTag(@PathVariable String tag_name) {
        return valueService.deleteTag(tag_name);
    }

    @DeleteMapping("/default/{id}")
    public DefaultValue deleteDefault(@PathVariable Long id) {
        return valueService.deleteDefault(id);
    }


    @GetMapping("/tag")
    public List<TagValue> getAllTag(){
        return valueService.getAllTag();
    }

    @GetMapping("/default")
    public List<DefaultValue> getAllDefault(){
        return valueService.getAllDefault();
    }

    @GetMapping("/default/{tag_name}")
    public List<DefaultValue> getByTag(@PathVariable String tag_name){
        return valueService.getAllByTag(tag_name);
    }
}
