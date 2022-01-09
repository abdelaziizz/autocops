package com.mdp.autocops.controller;

import com.mdp.autocops.model.entity.FieldType;
import com.mdp.autocops.service.framework.FieldTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/field-types")
public class FieldTypeController {

    @Autowired
    FieldTypeService fieldTypeService;

    @ResponseBody
    @GetMapping
    public List<FieldType> getAll () {
        return fieldTypeService.getAll();
    }

    @ResponseBody
    @PostMapping
    public FieldType create (@RequestParam String type) {
        return fieldTypeService.create(type);
    }

    @ResponseBody
    @GetMapping("/{id}")
    public FieldType getById (@PathVariable long id) {
        return fieldTypeService.getById(id);
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public FieldType delete (@PathVariable long id) {
        return  fieldTypeService.delete(id);
    }

    @ResponseBody
    @PutMapping("/{id}")
    public FieldType put (@PathVariable long id, @RequestParam String type) {
        return fieldTypeService.put(id, type);
    }
}
